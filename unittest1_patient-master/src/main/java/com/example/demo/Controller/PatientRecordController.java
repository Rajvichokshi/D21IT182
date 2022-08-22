package com.example.demo.Controller;

import com.example.demo.Repositories.PatientRecordRepository;
import com.example.demo.errors.InvalidRequestException;
import com.example.demo.model.PatientRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class PatientRecordController {

    @Autowired
    PatientRecordRepository patientRecordRepository;

    @GetMapping
    public List<Patient> getAllRecords() {
        return patientRecordRepository.findAll();
    }

    @GetMapping(value = "{patientId}")
    public Patient getPatientById(@PathVariable(value="patientId") Integer patientId) {
        return patientRecordRepository.findById(patientId).get();
    }

    @PostMapping
    public PatientRecord createRecord(@RequestBody PatientRecord patientRecord) {
        return patientRecordRepository.save(patientRecord);
    }

    @PutMapping
    public PatientRecord updatePatientRecord(@RequestBody PatientRecord patientRecord) throws ChangeSetPersister.NotFoundException {
        if (patientRecord == null || patientRecord.getPatientId() == null) {
            throw new InvalidRequestException("PatientRecord or ID must not be null!");
        }
        Optional<PatientRecord> optionalRecord = patientRecordRepository.findById(patientRecord.getPatientId());
        if (optionalRecord.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException("Patient with ID " + patientRecord.getPatientId() + " does not exist.");
        }
        PatientRecord existingPatientRecord = patientRecordRepository.findById(id).get();
        existingPatientRecord.setName(patientRecord.getName());
        existingPatientRecord.setAddress(patientRecord.getAddress());
        patientRecordRepository.save(existingPatientRecord);
        return patientRecordRepository.save(existingPatientRecord);
    }

    @DeleteMapping(value = "{patientId}")
    public void deletePatientById(@PathVariable(value = "patientId") Integer patientId) throws ChangeSetPersister.NotFoundException {
        if (patientRecordRepository.findById(patientId).isEmpty()) {
            throw new ChangeSetPersister.NotFoundException("Patient with ID " + patientId + " does not exist.");
        }
        patientRecordRepository.deleteById(patientId);
    }
}
