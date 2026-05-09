package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID>
// Here we pass:
// 1. Patient -> the Entity class that JPA will manage
// 2. UUID -> the datatype of the @Id field in the Patient entity i.e., primary key type of our entity class 'Patient'
// By extending JpaRepository, Spring Data JPA automatically provides
// CRUD operations and database interaction methods for the Patient entity,
// So JpaRepository<Patient, UUID> means:
//=>"Create repository operations for Patient entity whose primary key type is UUID."
{

}
