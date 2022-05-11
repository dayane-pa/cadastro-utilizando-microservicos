package com.example.cadastro.domain;

import javax.validation.constraints.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class Pessoa {

  @NotNull(message = "{id.not.null}")
  private int id;
  @NotBlank(message = "{name.not.blank}")
  private String name;
  @NotBlank(message = "{lastName.not.blank}")
  private String lastName;
  private String birthDate;


}
