package com.dialoguemd.patient.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.dialoguemd.patient.rest.PatientController;

/*
 * Patient Data Access Object (REST -> DAO -> JDBC -> H2)
 */
@Component
public class PatientDAOImpl implements IPatientDAO {
	
	//TODO - error handling
	
	@Autowired
	private PatientJDBCImpl patientJDBC;
	
	private Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Override
	public List<PatientVO> getAllPatients() {
		List<PatientVO> allPatients = new ArrayList<>();
		allPatients = patientJDBC.findAll();
		return allPatients;

	}
	
	public PatientVO getPatientById(int id) {
		return patientJDBC.getPatientById(id);
	}
	
	public boolean insertPatient(PatientVO patient) {
		if (patientJDBC.save(patient) < 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean doesPatientExist(String email) {
		return (patientJDBC.getPatientIdByEmail(email) == -1 ? false : true);
	}

	//Handles both insert and update cases
	@Override
	public boolean savePatient(PatientVO patient) {
		try { 
			//Check if patient already exists
			int patientId = patientJDBC.getPatientIdByEmail(patient.getEmail());
			if (patientId != -1) {
				//update case
				patient.setId(patientId);
				patientJDBC.update(patient);
			} else {
				//insert case
				patientJDBC.save(patient);
			}
		} catch (DataAccessException e) {
			logger.error("Could not save patient as ran into exception: {}" + e);
			return false;
		}
		
		return true;
	}
	
	public int deletePatient(int patientId) {
		return patientJDBC.deleteById(patientId);
	}
	
	public int getNextAvailableId() {
		return (patientJDBC.getMaxId().intValue() + 1);
	}

}
