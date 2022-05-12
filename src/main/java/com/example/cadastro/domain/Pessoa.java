package com.example.cadastro.domain;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Pessoa {


  private int id;

  @NotBlank(message = "{name.not.blank}")
  private String name;

  @NotBlank(message = "{lastName.not.blank}")
  private String lastName;

  private String birthDate;



}
