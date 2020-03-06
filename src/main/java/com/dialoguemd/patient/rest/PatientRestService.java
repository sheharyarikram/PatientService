package com.dialoguemd.patient.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dialoguemd.errors.ErrorCodesEnum;
import com.dialoguemd.errors.HttpError;
import com.dialoguemd.patient.jdbc.PatientDAOImpl;
import com.dialoguemd.patient.jdbc.PatientVO;
import com.dialoguemd.patient.jpa.PatientEntity;
import com.dialoguemd.patient.jpa.PatientPaginationService;


/*
 * REST service class to dispatch calls to data layer
 */
@Component
public class PatientRestService {
	
	@Autowired
	private PatientDAOImpl patientDAO;
	
	@Autowired
	private PatientPaginationService patientPaginationService;
	
	private Logger logger = LoggerFactory.getLogger(PatientRestService.class);
	
	public List<PatientVO> getAllPatients() {
		//TODO - error handling
		return patientDAO.getAllPatients();
	}
	
	public ResponseEntity<?> getPatient(int patientId, String requestId) {
		PatientVO patient = patientDAO.getPatientById(patientId);
		if (patient == null) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.PATIENT_NOT_FOUND, "Patient with ID " + patientId + " not found!");
		} else {
			logger.info("Patient with email {} found", patient.getEmail());
			return new ResponseEntity<PatientVO>(patient, HttpStatus.OK);
		}
	}
	
	public ResponseEntity<?> savePatient(PatientVO patient, String requestId) {	
		//Check if all fields are provided
		if (patient == null || patient.getEmail() == null || patient.getEmail().isEmpty() ||
			patient.getBirthDate() == null || patient.getBirthDate().toString().isEmpty() ||
			patient.getFirstName() == null || patient.getFirstName().isEmpty() ||
			patient.getLastName() == null || patient.getLastName().isEmpty() ||
			patient.getSex() == null || patient.getSex().isEmpty()) {
			
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.FIELD_MISSING, "A required field missing or empty");
		}
			
		//Check if email has exactly 1 '@' sign and ends with '.com'
		if ((StringUtils.countOccurrencesOf(patient.getEmail(), "@") != 1) || !(patient.getEmail().endsWith(".com"))) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BAD_FORMAT, "Email " + patient.getEmail() + " has incorrect format");
		}
		
		//Check if first and last name is letters only
		if (!(patient.getFirstName().chars().allMatch(Character::isLetter)) || !(patient.getLastName().chars().allMatch(Character::isLetter))) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BAD_FORMAT, "Patient name is incorrect format: " + patient.getFirstName() + " " + patient.getLastName());
		}
		
		//Check if sex is one of 'male/female/unspecified'
		if (!patient.getSex().equals("male") && !patient.getSex().equals("female") && !patient.getSex().equals("unspecified")) {
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BAD_FORMAT, "Patient gender " + patient.getSex() + " is incorrect. Allowed values are male/female/unspecified");
		}
		
		//Check if birthdate is in format 'yyyy-mm-dd'
		Date formattedDate = new Date();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = format.parse(format.format(patient.getBirthDate()));
		} catch (ParseException e) {
			logger.info("Patient birth date format is incorrect - {}", patient.getBirthDate());
			return generateErrorResponseEntity(requestId, HttpStatus.BAD_REQUEST, ErrorCodesEnum.BAD_FORMAT, "Patient birth date " + patient.getBirthDate() + " is incorrect. Allowed format is yyyy-mm-dd");
		}
		
		logger.info("Attempting to save patient with email = {}, date = {}, firstName = {}, lastName = {}, gender = {}", 
				patient.getEmail(),
				formattedDate,
				patient.getFirstName(),
				patient.getLastName(),
				patient.getSex()
			);

		if (patientDAO.doesPatientExist(patient.getEmail())) {
			logger.info("Patient with email {} alread exists", patient.getEmail());
			return generateErrorResponseEntity(requestId, HttpStatus.CONFLICT, ErrorCodesEnum.DUPLICATE_EMAIL, "Email is duplicate");
		}
		
		//Set the Id of the patient before
		patient.setId(patientDAO.getNextAvailableId());
		//Set the correct birth date format
		patient.setBirthDate(formattedDate);

		//Save the patient - 
		if (patientDAO.insertPatient(patient)) {
			return new ResponseEntity<PatientVO>(patient, HttpStatus.CREATED);
		} else {
			return generateErrorResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodesEnum.SERVER_ERROR, "Failed to save patient - something went wrong!");
		}
	}
	
	public ResponseEntity<PatientListResponseModel> getAllPatientsPaginated(Integer page, Integer size, String baseUrl) {
        List<PatientEntity> patientList = patientPaginationService.getAllPatients(page, size);
        int currentPage = patientPaginationService.getCurrentPageNumber(page, size);
        int totalPages = patientPaginationService.getTotalPages(page, size);

  		StringBuffer sb = new StringBuffer();
  		sb.append(baseUrl);
  		
      	List<Link> listOfLinks = new ArrayList<>();
  		Link selfLink = new Link((sb.toString() + "?page=" + page + "&size=" + size), IanaLinkRelations.SELF);
      	listOfLinks.add(selfLink);
      	
      	if (currentPage < totalPages - 1) {
      		Link nextLink = new Link((sb.toString() + "?page=" + (page + 1) + "&size=" + size), IanaLinkRelations.NEXT);
          	listOfLinks.add(nextLink);
      	} else {
      		Link nextLink = new Link((sb.toString() + "?page=" + (page) + "&size=" + size), IanaLinkRelations.NEXT);
          	listOfLinks.add(nextLink);
      	}
        
        PatientListResponseModel ps = new PatientListResponseModel(patientList, listOfLinks);
		return new ResponseEntity<PatientListResponseModel>(ps, HttpStatus.OK);
	}
	
	public ResponseEntity<?> deletePatient(int patientId, String requestId) {
		logger.info("Attempting to delete patient with id {}", patientId);

		if (patientDAO.deletePatient(patientId) < 1) {
			return generateErrorResponseEntity(requestId, HttpStatus.NOT_FOUND, ErrorCodesEnum.PATIENT_NOT_FOUND, "Patient does not exist");
		} else {
			return new ResponseEntity<>("Deleted patient successfully", HttpStatus.OK);
		}
	}
	
	public String generateNewId() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}
	
	public ResponseEntity<?> generateErrorResponseEntity(String requestId, HttpStatus httpStatus, ErrorCodesEnum internalCode, String description) {
		return new ResponseEntity<HttpError>(
			new HttpError(
				requestId, 
				httpStatus.value(), 
				httpStatus.getReasonPhrase(), 
				description, 
				internalCode.code
			),
			httpStatus
		);
	}

}
