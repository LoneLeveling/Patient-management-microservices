package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
//Service Layer: Where all the business logic + DTO conversion happens for a given request.
@Service
public class PatientService {
private PatientRepository patientRepository;
// Spring injects PatientRepository dependency through constructor Injection
public PatientService(PatientRepository patientRepository)
{

    this.patientRepository=patientRepository;
}

//NOTE: Service layer converts our domain entity model into Response DTO
    //And the patient response DTO has the properties that we want the client to see.
public List<PatientResponseDTO> getPatients()
{

    List<Patient> patients=patientRepository.findAll();
    List<PatientResponseDTO> patientResponseDTOS=
            patients.stream().map(patient -> PatientMapper.toDTO(patient))
                    .toList();

    return patientResponseDTOS;
}
}
