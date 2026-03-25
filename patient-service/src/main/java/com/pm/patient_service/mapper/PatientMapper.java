package com.pm.patient_service.mapper;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    //Below method converts a Patient entity object to a Patient response DTO.
    public static PatientResponseDTO toDTO(Patient patient)
    {
        PatientResponseDTO patientDTO=new PatientResponseDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientDTO;
    }

    //Below method converts a Patient response DTO to Patient entity object.
    public static Patient toModel(PatientRequestDTO patientRequestDTO)
    {
        //Below we are creating a Patient entity object from the DTO object --> patientRequestDTO
        Patient patient= new Patient();
        patient.setName(patientRequestDTO.getName());//note: all this getName(), getAddress() etc are coming in from the patientRequestDTO and we passing it into the entity model
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        return patient;
    }
}
