package com.example.cadastro.dto;

import com.example.cadastro.domain.PessoaEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
public class ArtigoDto {

  private long id;
  private String tituloDoArtigo;
  private PessoaEntity autor;
  private String dataDaPublicacao;
  private String corpo;
  private Long views;
}
