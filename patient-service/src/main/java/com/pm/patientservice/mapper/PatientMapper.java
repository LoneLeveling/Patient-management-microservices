package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

//We are using this separate 'Mapper' class which is basically a Helper class,
// used to convert from Entity to DTO,
//to keep things more modular & it keeps all the logic out of service layer
public class PatientMapper
{

    //PatientResponseDTO :: Entity--> ResponseDTO
    public static PatientResponseDTO toDTO(Patient patient)
            //In above argument we have patient entity class which we want
    // to convert to Patient DTO response.
    {
        PatientResponseDTO dto=new PatientResponseDTO();
        dto.setId(patient.getId().toString());//here we are fetching Id from
        // patient entity and have set it as being the ID on our Patient DTO.
        dto.setName(patient.getName());
        dto.setAddress(patient.getAddress());
        dto.setEmail(patient.getEmail());
        dto.setDateOfBirth(patient.getDateOfBirth().toString());
        return dto;
    }


    //Creating a Patient object from DTO object (to be saved in db)
    public static Patient toModel(PatientRequestDTO patientRequestDTO)
    {
        Patient patient=new Patient();
        patient.setName(patientRequestDTO.getName());
        //Here above we are taking (.getName()) the name from the patientRequestDTO
        //and setting it as a name on the Patient object.
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        //In above if you remove whats inside the argument and then hover over the argument
        //You will see its expecting a LocalDate datatype for dateOfBirth , since this data needs to be stored
        //In the db and the data type of this column is LocalDate, but the data that we received from the
        // PatientRequestDTO is a String type, so we parse here above using the java builtin package.
//Same logic for below:
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
    return patient;
    }
}
