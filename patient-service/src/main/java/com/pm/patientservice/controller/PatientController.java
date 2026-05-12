package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients") //http://localhost:4000/patients
public class PatientController {
    //Again we use below the DI concept since the controller class is dependent on the Service class,
    //just how the Service class is dependent on the Patient repository class.
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
//Now we need to create method that will handle various CRUD requests that we are going to receive


    @GetMapping//This means this method handles all the GET requests
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {//Below we need to call the Patient Service layer to get all the patients
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
        //Above we are returning a http response to client with status code 200 i.e.
        // response is ok via ok(),
        // to inform the client that all went well!!
        // And to the body of the http response we add the 'patients' which is a list of
        // patient response DTOs.
    }

    @PostMapping //We use post request anytime we are creating any stuff.
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO
                                                            //@Valid validates the fields of patientRequestDTO to make sure all the properties matches the annotations that we used in the PatientRequestDTO object in dto package.
                                                            //@RequestBody : This annotation coverts the json request to our PatientRequestDTO for us.
    ) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
        //NOTE: The reason why we returned patient response DTO back in POST request is bcz it's going to have the latest updated info. as it comes back from the db.
    }
}
