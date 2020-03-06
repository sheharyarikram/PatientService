package com.dialoguemd.patient.jdbc;

import java.util.List;

public interface IPatientDAO {
	public List<PatientVO> getAllPatients();
	public boolean savePatient(PatientVO patient);
	public PatientVO getPatientById(int id);
	public boolean insertPatient(PatientVO patient);
	public boolean doesPatientExist(String email);
	public int deletePatient(int patientId);
	public int getNextAvailableId();
}
