package com.example.cadastro.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Artigo {

    private String tituloDoArtigo;
    private String nomeDoAutor;
    private String dataDaPublicacao;
    private String corpo;

}
