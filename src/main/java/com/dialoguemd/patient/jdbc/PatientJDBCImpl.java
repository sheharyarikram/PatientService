package com.dialoguemd.patient.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/*
 * JDBC functionality for Patient
 */
@Component
public class PatientJDBCImpl implements IPatientJDBC {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Logger logger = LoggerFactory.getLogger(PatientJDBCImpl.class);


	//Get list of all patients
	//TODO - what if no patients in DB - need to test 
    public List<PatientVO> findAll() {
        return jdbcTemplate.query("select * from patient order by id asc", 
        	(rs, rowNum) -> new PatientVO(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("birth_date"),
                rs.getString("sex")));
    }    
        
    //Save patient object
    public int save(PatientVO patient) {
        return jdbcTemplate.update("insert into patient (email, first_name, last_name, birth_date, sex) values(?, ?, ?, ?, ?)",
            patient.getEmail(),
            patient.getFirstName(),
            patient.getLastName(), 
            patient.getBirthDate(), 
            patient.getSex());
    }
    
    //Update patient object
    public int update(PatientVO patient) {
        return jdbcTemplate.update("update patient set email = ?, first_name = ?, last_name = ?, birth_date = ?, sex = ? where id = ?",
        	patient.getEmail(), 
            patient.getFirstName(), 
            patient.getLastName(), 
            patient.getBirthDate(), 
            patient.getSex(), 
            patient.getId()); //lookup key
    }
    	
    public PatientVO getPatientByEmail(String email) { 
    	return jdbcTemplate.queryForObject("select * from patient where email = ?", 
    			new String[] {email}, 
    			(rs, rowNum) -> new PatientVO(
    					rs.getInt("id"),
    					rs.getString("email"),
    					rs.getString("first_name"),
    					rs.getString("last_name"),
    					rs.getDate("birth_date"),
    					rs.getString("sex")));    	
    }
    
    //Get patient that matches the given unique Id
    public PatientVO getPatientById(int id) {     	
    	try {
        return jdbcTemplate.queryForObject("select * from patient where id = ?", 
        		new Integer[] {id}, 
        		(rs, rowNum) -> new PatientVO(
        			rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("birth_date"),
                    rs.getString("sex")));
    	} catch (EmptyResultDataAccessException ex) {
        	logger.info("Patient with patient id {} does not exist!", id);
    		return null;
    	}
    }
    
    //Get patient id from email
    public Integer getPatientIdByEmail(String email) {
    	int id = -1;
    	try {
    		id = jdbcTemplate.queryForObject("select id from patient where email = \'" + email.trim() + "\'", Integer.class);
    	} catch (EmptyResultDataAccessException ex) {
    		logger.info("No existing patient with email: {}", email);
    	}
    	return id;
    }
    
    //delete patient
    public int deleteById(int patientId) {
       return jdbcTemplate.update("delete from patient where id = ?", patientId);
    }
    
    public Integer getMaxId() {
        return jdbcTemplate.queryForObject("select max(id) from patient", Integer.class);
    }
    
}
