package com.example.cadastro.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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







