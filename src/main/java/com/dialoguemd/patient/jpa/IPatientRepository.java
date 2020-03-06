package com.dialoguemd.patient.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPatientRepository extends PagingAndSortingRepository<PatientEntity, Long> {
	
}
