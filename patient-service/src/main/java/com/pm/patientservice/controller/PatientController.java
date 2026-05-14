package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<PatientResponseDTO> createPatient
            (@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO
     //@Valid validates the fields of patientRequestDTO to make sure all the properties matches the annotations that we used in the PatientRequestDTO object in dto package.
     //@RequestBody : This annotation coverts the json request to our PatientRequestDTO for us.
    ) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
        //NOTE: The reason why we returned patient response DTO back in POST request is bcz it's going to have the latest updated info. as it comes back from the db.
    }

    @PutMapping("/{id}") //NOTE: Anytime we are updating an Entity we use put request and any time we use a PUT request
    // we use it in association with an 'id' so that we know the id of the entity that we are going to update.
    //The UUID received is converted to an 'id' variable which is then passed to the update Patient method which handles the request.
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,  //This @PathVariable annotation tells spring that this 'id' is the variable that we want to map to the path variable that we received in PUT request.
                                                            @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) //This lines means that the updated patient data that we receive in the body of the request as JSON is going to get assigned to patient request DTO.
    //NOTE: @Validated({Default.class})--> Tells spring to validate the request using all the defaults that we specify in the DTO
    {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);

    }


    //CREATING A DELETE END POINT
    @DeleteMapping("/{id}") //Note: below in <> we have Void since this method does not return anything
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id)
    {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
    }
//So above method calls the Patient service and calls the delete patient method
//which then deletes the Patient from the repository and then above method returns
// a response entity with no content. And that will return a staus code : 204 ,
// 204 : means no content to display,
// and finally the request will be completed.

}
