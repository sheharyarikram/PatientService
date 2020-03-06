package com.dialoguemd.patient.rest;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dialoguemd.patient.jdbc.PatientVO;


@RestController
@RequestMapping("/v1")
public class PatientController {
	
	//TODO - UUID generation can be performed in a request mapper so won't need to call it explicitly in each request method...
	
	@Autowired
	private PatientRestService patientRestService;
	
	private Logger logger = LoggerFactory.getLogger(PatientController.class);
	
	//URL: http://localhost:8080/
	@RequestMapping("/")
	public String healthCheck() {
		return "Hello!";
	}

	//URL: http://localhost:8080/v1/patients
	@RequestMapping(value = "/patients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<PatientListResponseModel> getAllPatients(@RequestParam(defaultValue = "0") Integer page, 
																	@RequestParam(defaultValue = "10") Integer size,
																	Pageable pageable) {
		String requestId = patientRestService.generateNewId();
		logger.info("Received GET request to get pagenated list of patients: page numer = {}, page size = {} - Request ID: {}", page, size, requestId);
  		@SuppressWarnings("deprecation")
		String baseUrl = linkTo(PatientController.class).slash("/patients").toString();
    	return patientRestService.getAllPatientsPaginated(page, size, baseUrl);
	}
	
	//URL: http://localhost:8080/v1/patients
//    @RequestMapping(value = "/patients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    private List<PatientVO> getAllPatients(HttpServletRequest request) {
//    	String requestId = patientRestService.generateNewId();
//    	logger.info("server name = {}", request.getServerName());
//    	logger.info("Received GET request to get list of all patients - Request ID: {}", requestId);
//        return patientRestService.getAllPatients();
//    }

    @RequestMapping(value = "/patients", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> savePatient(@RequestBody PatientVO patient) {
    	String requestId = patientRestService.generateNewId();
    	logger.info("Received POST request to save patient with email: {} - Request ID: {}", patient.getEmail(), requestId);
    	return patientRestService.savePatient(patient, requestId);
    }
    
	//URL: http://localhost:8080/v1/patients/1
    @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> getPatient(@PathVariable(required = true) int id) {
    	String requestId = patientRestService.generateNewId();
    	logger.info("Received GET request to retrieve patient with id: {} - Request ID: {}", id, requestId);
    	return patientRestService.getPatient(id, requestId);
    }
    
    @RequestMapping(value = "/patients/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> deletePatient(@PathVariable("id") int id) {
    	String requestId = patientRestService.generateNewId();
    	logger.info("Received DELETE request to delete patient with id: {} - Request ID: {}", id + requestId);
    	return patientRestService.deletePatient(id, requestId);
    }

}
