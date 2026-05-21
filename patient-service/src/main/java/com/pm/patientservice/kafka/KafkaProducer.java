package com.pm.patientservice.kafka;

import com.pm.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, byte[]> KafkaTemplate;
    //Autowriring the above dependency in the constructor of our Kafka producer class 'KafkaProducer'
    public KafkaProducer(KafkaTemplate<String, byte[]> KafkaTemplate)
    {
        this.KafkaTemplate=KafkaTemplate;
    }

    public void sendEvent(Patient patient)
    {
    //Creating an event that has all the properties in it:
        PatientEvent event=PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        try{
            KafkaTemplate.send("patient",event.toByteArray());
        }
        catch (Exception e)
        {
            log.error("Error sending PatientCreated event:{}", event);
        }

    }
}
