package com.codefish.keeilzhanstd.ecommerce.record;

import com.codefish.keeilzhanstd.ecommerce.appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @ToString
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(min=2, message = "Name should have at least 2 characters")
    private String name;
    private String gender;

    @Past(message = "Birth date should be in the past")
    private LocalDate birthDate;
    private String address;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String email;
    private String postcode;
    private Double height;
    private Double weight;
    private Double bmi;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="record")
    @JsonIgnore
    private List<Appointment> appointments;

    public Record() {}
}
