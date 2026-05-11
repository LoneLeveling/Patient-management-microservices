package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import java.util.List;
//Service Layer: Where all the business logic + DTO conversion happens for a given request.
@Service
public class PatientService
{
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
    List<PatientResponseDTO> patientResponseDTOS //this DTO object contains all the properties that we want to sent to the frontend client.
            =
            patients.stream().map(patient -> PatientMapper.toDTO(patient))
                    .toList();
    //NOTE:  patients.stream().map() function is similar to a for loop which iterated over every item in a given list , patients list in our case
    // For each patient the static toDTO method is called on the Mapper class and the result is added to the patientResponseDTO variable as a list.
     //So at the end if we have 15 patients being returned from the repository ,
     //then patientResponseDTOS variable will have 15 items in it as well.
    return patientResponseDTOS;
 }
}
