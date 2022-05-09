package com.example.cadastro.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class Pessoa {

    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    private String birthDate;


}
