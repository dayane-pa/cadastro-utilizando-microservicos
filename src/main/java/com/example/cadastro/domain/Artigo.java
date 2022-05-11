package com.example.cadastro.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.DatabindException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Artigo {

  private int id;

  @NotBlank(message = "{tituloDoArtigo.not.blank}")
  private String tituloDoArtigo;

  @NotNull(message = ("{autor.not.null}"))
  private Pessoa autor;

  @DateTimeFormat(iso = ISO.DATE)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private String dataDaPublicacao;

  @NotBlank(message = "{corpo.not.blank}")
  private String corpo;


}







