package com.codefish.keeilzhanstd.ecommerce.appointment;
import jakarta.persistence.*;

import com.codefish.keeilzhanstd.ecommerce.record.Record;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter @Setter @ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "rid", nullable = false)
    private Record record;

    private LocalDate date;
    private boolean status;

    public Appointment() { }

}
