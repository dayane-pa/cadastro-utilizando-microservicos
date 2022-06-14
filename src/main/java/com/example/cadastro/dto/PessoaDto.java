package com.example.cadastro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(setterPrefix = "with")
public class PessoaDto {

  private long id;
  private String name;
  private String lastName;
  private String birthDate;
  private String email;

}
