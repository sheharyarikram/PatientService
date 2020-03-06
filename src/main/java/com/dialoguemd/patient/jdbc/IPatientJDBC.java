package com.dialoguemd.patient.jdbc;

import java.util.List;

public interface IPatientJDBC {
	public List<PatientVO> findAll();
	public int save(PatientVO patient);
	public PatientVO getPatientByEmail(String email);
	public int update(PatientVO patient);
	public PatientVO getPatientById(int id);    	
	public int deleteById(int patientId);
	public Integer getMaxId();


}