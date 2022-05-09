package com.example.cadastro.domain;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Data
public class Artigo {

  @NotBlank(message = "{tituloDoArtigo.not.blank}")
  private String tituloDoArtigo;
  private Pessoa autor;
  @NotBlank(message = "{data.not.blank}")
  private String dataDaPublicacao;
  @NotBlank(message = "{corpo.not.blank}")
  private String corpo;

}
