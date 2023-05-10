package com.codefish.keeilzhanstd.ecommerce.record;

import com.codefish.keeilzhanstd.ecommerce.appointment.Appointment;
import com.codefish.keeilzhanstd.ecommerce.appointment.AppointmentRepository;
import com.codefish.keeilzhanstd.ecommerce.exception.RecordNotFoundException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mrs")
public class RecordController {
    @Autowired
    private RecordRepository repository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping(path="/records")
    public List<Record> retrieveAllRecords(){
        return repository.findAll();
    }

    @GetMapping(path="/records/count")
    public int countAllRecords(){
        return repository.findAll().size();
    }

    @GetMapping(path="/records/{id}")
    public Optional<Record> retrieveRecord(@PathVariable Long id){
        Optional<Record> record = repository.findById(id);
        if(record.isEmpty())
            throw new RecordNotFoundException("id:"+id);
        return record;
    }

    @DeleteMapping(path="/records/{id}")
    public void deleteRecord(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PutMapping(path="/records/{id}")
    public Record updateRecord(@Valid @RequestBody Record record) {
        repository.save(record);
        return record;
    }

    @PostMapping(path="/records")
    public Record addNewRecord(@Valid @RequestBody Record record) {
        record.setId(null);
        repository.save(record);
        return record;
    }

    @GetMapping(path="/records/{id}/appointments")
    public List<Appointment> retrieveAppointmentForRecord(@PathVariable Long id){
        Optional<Record> record = repository.findById(id);
        if(record.isEmpty())
            throw new RecordNotFoundException("id:"+id);

        return record.get().getAppointments();
    }

    @GetMapping(path="/records/{id}/appointments/{aid}")
    public Appointment retrieveAppointmentForRecord(@PathVariable Long id, @PathVariable Long aid){
        Optional<Record> record = repository.findById(id);
        if(record.isEmpty())
            throw new RecordNotFoundException("record id:"+id);

        Optional<Appointment> appt = appointmentRepository.findById(aid);
        if(appt.isEmpty())
            throw new RecordNotFoundException("appt id:"+id);

        return appt.get();
    }

    @PutMapping(path="/records/{id}/appointments/{aid}")
    public Appointment updateAppointmentForRecord(@PathVariable Long id, @PathVariable Long aid, @Valid @RequestBody Appointment appointment){
        Optional<Record> record = repository.findById(id);
        if(record.isEmpty())
            throw new RecordNotFoundException("record id:"+id);

        Optional<Appointment> appt = appointmentRepository.findById(aid);
        if(appt.isEmpty())
            throw new RecordNotFoundException("appt id:"+aid);

        //Delete previous appointment
        appointmentRepository.deleteById(aid);

        //Add new appointment to the user
        appointment.setRecord(record.get());
        return appointmentRepository.save(appointment);
    }

    @PostMapping(path="/records/{id}/appointments")
    public Appointment createAppointmentForRecord(@PathVariable Long id, @Valid @RequestBody Appointment appointment){
        Optional<Record> record = repository.findById(id);
        if(record.isEmpty())
            throw new RecordNotFoundException("id:"+id);
        appointment.setRecord(record.get());
        return appointmentRepository.save(appointment);

    }
}
