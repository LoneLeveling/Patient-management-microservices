package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients") //http://localhost:4000/patients
public class PatientController {
    //Again we use below the DI concept since the controller class is dependent on the Service class,
    //just how the Service class is dependent on the Patient repository class.
    private PatientService patientService;
    public PatientController(PatientService patientService)
    {
        this.patientService=patientService;
    }
//Now we need to create method that will handle various CRUD requests that we are going to receive


    @GetMapping//This means this method handles all the GET requests
    public ResponseEntity<List<PatientResponseDTO>> getPatients()
    {//Below we need to call the Patient Service layer to get all the patients
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
        //Above we are returning a http response to client with status code 200 i.e.
        // response is ok via ok(),
        // to inform the client that all went well!!
        // And to the body of the http response we add the 'patients' which is a list of
        // patient response DTOs.
    }
 }
