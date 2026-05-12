package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

//Service Layer: Where all the business logic + DTO conversion happens for a given request.
@Service
public class PatientService {
    private final PatientRepository patientRepository;

    // Spring injects PatientRepository dependency through constructor Injection
    public PatientService(PatientRepository patientRepository) {

        this.patientRepository = patientRepository;
    }

    //NOTE: Service layer converts our domain entity model into Response DTO
    //And the patient response DTO has the properties that we want the client to see.
    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS //this DTO object contains all the properties that we want to sent to the frontend client.
                = patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();
        //NOTE:  patients.stream().map() function is similar to a for loop which
        // iterated over every item in a given list , patients list in our case
        // For each patient the static toDTO method is called on the Mapper class
        // and the result is added to the patientResponseDTO variable as a list.
        // So at the end if we have 15 patients being returned from the repository ,
        //then patientResponseDTOS variable will have 15 items in it as well.
        return patientResponseDTOS;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email " + "already exists " + patientRequestDTO.getEmail());
        }
//IMP NOTE: The below code with never get called i.e., patient will never be saved into
// the db table in case above throw runs, since we cannot have more than 1 patient with the same
//email address.

        //Converting patient request DTO into Patient Entity model object

        Patient newPatinet = patientRepository.save //.save(called Mapper)
                (PatientMapper.toModel(patientRequestDTO));
        //Mapper.toModel() return a Patient object which is being passed into the
        //save() function of the repository and then that gets persisted to the
        // database, behind the scenes and we get a new Patient record created in the db.

        //Once all this is done we can convert the newPatient variable to DTO
        //and return it back to controller.

        return PatientMapper.toDTO(newPatinet);
    }


    //Below method handles the business logic for updating a patient which is already existing in the db
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO)//fetching patient by the ID , this ID comes from the users request for the patient which he wants to make's changes for
    {
        //Below the patient repository is trying to find an Entity by its ID
        // and if not found it's going to throw the new PatientNotFoundException
        //& we are going to create the below custom exception.
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email " + "already exists " + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
    //NOTE WE DID NOT PUT LOGIC FOR REGISTERED DATE, Because we do not allow to alter it once its created.
        //This is why we update each property individually because it gives us control on what property we want
        // to update during the request to update the patient.

        //Now that we have updated the object we save it back to repository as below:
        Patient updatedPatient=patientRepository.save(patient);

        //Now we need to return the updated DTO object as a json back to the controller, so that it can complete the request:
        return PatientMapper.toDTO(patient);

    }
}
