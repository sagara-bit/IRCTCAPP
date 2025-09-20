package com.example.irctc.irctc_backend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="stations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private  String code;
    private  String name;
    private  String city;
    private  String state;

}
