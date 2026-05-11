package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;
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
}
