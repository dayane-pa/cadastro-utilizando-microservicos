package com.example.cadastro.repository;


import static java.util.Objects.nonNull;

import com.example.cadastro.domain.Artigo;
import com.example.cadastro.domain.Pessoa;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ArtigoRepositoryInMemory {

  private Pessoa pessoa = new Pessoa();
  private List<Artigo> listaDeArtigosPrincipal = new ArrayList<>();

  public boolean adicionarArtigo(Artigo novoArtigo) {
    Optional<Artigo> optionalArtigo = filteArtigo(novoArtigo.getTituloDoArtigo(),
        novoArtigo.getAutor().getName());

   // novoArtigo.setId(ContadorUtil.contadorId());
    return listaDeArtigosPrincipal.add(novoArtigo);
  }

  public List<Artigo> buscarArtigo(String nomeDoAutor, String tituloDoArtigo) {

    if (nomeDoAutor == null && tituloDoArtigo == null) {
      return listaDeArtigosPrincipal;
    }

    List<Artigo> artigosEncontrados = new ArrayList<>();

    if (nonNull(nomeDoAutor)) {
      artigosEncontrados.addAll(listaDeArtigosPrincipal.stream()
          .filter(artigo -> artigo.getAutor().getName().equals(nomeDoAutor))
          .collect(Collectors.toList()));
    }

    if (nonNull(tituloDoArtigo)) {
      artigosEncontrados.addAll(listaDeArtigosPrincipal.stream()
          .filter(artigo -> artigo.getTituloDoArtigo().equals(tituloDoArtigo))
          .collect(Collectors.toList()));
    }

    return artigosEncontrados;
  }

  public boolean atualizarArtigo(Artigo novoArtigo) {

    Optional<Artigo> optionalArtigo = listaDeArtigosPrincipal.stream()
        .filter(artigo -> artigo.getAutor().getName().equals(novoArtigo.getAutor().getName()))
        .filter(artigo -> artigo.getDataDaPublicacao().equals(novoArtigo.getDataDaPublicacao()))
        .findFirst();

    if (optionalArtigo.isEmpty()) {
      return false;
    }
    Artigo artigo = optionalArtigo.get();
    artigo.setTituloDoArtigo(novoArtigo.getTituloDoArtigo());
    artigo.setCorpo(novoArtigo.getCorpo());
    return true;
  }

  public boolean deletarArtigo(String tituloDoArtigo, String nomeDoAutor) {
    Optional<Artigo> optionalArtigo = filteArtigo(tituloDoArtigo, nomeDoAutor);

    return listaDeArtigosPrincipal.remove(optionalArtigo.get());
  }

  private Optional<Artigo> filteArtigo(String tituloDoArtigo, String nomeDoAutor) {
    return listaDeArtigosPrincipal.stream()
        .filter(artigo -> artigo.getTituloDoArtigo().equals(tituloDoArtigo))
        .filter(artigo -> artigo.getAutor().getName().equals(nomeDoAutor))
        .findFirst();
  }

}
