package com.example.cadastro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(name = "pessoa")
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(length = 50)
  @NotBlank(message = "{name.not.blank}")
  private String name;

  @Column(name = "last_name", length = 50)
  @NotBlank(message = "{lastName.not.blank}")
  private String lastName;

  @Column(name = "birth_date", length = 10)
  private String birthDate;

  @Email(message = "{email.valid}")
  @Column(unique = true)
  @NotBlank(message = "{email.not.blank}")
  private String email;

  @Column(nullable = true)
  private Long viewsPessoa;

}
