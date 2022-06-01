package com.example.cadastro.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Entity(name = "Artigo")
public class Artigo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "titulo_do_artigo")
  @NotBlank(message = "{tituloDoArtigo.not.blank}")
  private String tituloDoArtigo;

  @ManyToOne
  @JoinColumn
  @NotNull(message = ("{autor.not.null}"))
  private Pessoa autor;

  @Column(name = "data_da_publicacao")
  @DateTimeFormat(iso = ISO.DATE)
  @JsonFormat(pattern = "dd/MM/yyyy")
  private String dataDaPublicacao;

  @NotBlank(message = "{corpo.not.blank}")
  private String corpo;

  @Column(nullable = true)
  private Long views;
}







