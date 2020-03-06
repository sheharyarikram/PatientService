package com.dialoguemd.patient.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/*
 * Service used to retrieve paginated list of all patients leveraging JPA
 */
@Component
public class PatientPaginationService {
	
    @Autowired
    IPatientRepository patientRepo;
     
    public List<PatientEntity> getAllPatients(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id"));
        Page<PatientEntity> pagedResult = patientRepo.findAll(paging);
         
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<PatientEntity>();
        }
    }

    public int getTotalPages(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id"));
        Page<PatientEntity> pagedResult = patientRepo.findAll(paging);
        
        if (pagedResult.hasContent()) {
            return pagedResult.getTotalPages();
        } else {
            return 0;
        }
    }
    
    public int getCurrentPageNumber(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id"));
        Page<PatientEntity> pagedResult = patientRepo.findAll(paging);
        
        if (pagedResult.hasContent()) {
            return pagedResult.getNumber();
        } else {
            return Integer.MAX_VALUE;
        }
    }

}

