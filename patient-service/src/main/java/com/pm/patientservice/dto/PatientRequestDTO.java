package com.pm.patientservice.dto;

//In this class we add all the properties that we expect to receive in the body
// of the request from client whenever we create a new Patient.

import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PatientRequestDTO {

    @NotBlank(message = "Name is required")
    @NotBlank
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Mail Id is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Date of Birth is required")
    private String dateOfBirth;

    @NotBlank(groups = CreatePatientValidationGroup.class, message = "Registered date is required")
    private String registeredDate;
    //Even though we do not send registered date back in patientResponse DTO
    //we still need a way for admins of our Patient management to enter in a registered date
    //So on the frontend when we build the form the admin will be able to add a
    //Patient along with the registered date but we do not return patient registered date in response dto, we keep tit h idden,
    //this is the reason why we keep patient request and response DTO different in case
    //we need to change the data that gets input Vs the data that gets returned.


    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(
          String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
