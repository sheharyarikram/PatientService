package com.dialoguemd.patient.rest;

import java.util.List;

import org.springframework.hateoas.Link;

import com.dialoguemd.patient.jpa.PatientEntity;

public class PatientListResponseModel {

	public List<PatientEntity> data;
	public List<Link> links;
	
	public PatientListResponseModel(List<PatientEntity> data, List<Link> links) {
		super();
		this.data = data;
		this.links = links;
	}
	
}
