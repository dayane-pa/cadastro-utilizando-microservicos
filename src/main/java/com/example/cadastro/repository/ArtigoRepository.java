package com.example.cadastro.repository;


import static java.util.Objects.nonNull;

import com.example.cadastro.domain.Artigo;
import com.example.cadastro.domain.Pessoa;
import com.example.cadastro.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class ArtigoRepository {

  private Pessoa pessoa = new Pessoa();
  private List<Artigo> listaDeArtigosPrincipal = new ArrayList<>();

  public void adicionarArtigo(Artigo novoArtigo) {
    Optional<Artigo> optionalArtigo = filteArtigo(novoArtigo.getTituloDoArtigo(),
        novoArtigo.getAutor().getName());

    if (optionalArtigo.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Artigo já existente. " + optionalArtigo.get());
    }

    novoArtigo.setId(ContadorUtil.contadorId());
    listaDeArtigosPrincipal.add(novoArtigo);
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

    if (artigosEncontrados.size() == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Não encontrado, dados incorretos. " + (nonNull(nomeDoAutor) ? nomeDoAutor : "") + " " +
              (nonNull(tituloDoArtigo) ? tituloDoArtigo : ""));
    }

    return artigosEncontrados;
  }
  public void atualizarArtigo(Artigo novoArtigo) {

    Optional<Artigo> optionalArtigo = listaDeArtigosPrincipal.stream()
        .filter(artigo -> artigo.getAutor().getName().equals(novoArtigo.getAutor().getName()))
        .filter(artigo -> artigo.getDataDaPublicacao().equals(novoArtigo.getDataDaPublicacao()))
        .findFirst();

    if (optionalArtigo.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Artigo não pode ser alterado, verifique o nome do " + "autor e data " + novoArtigo);
    }

    Artigo artigo = optionalArtigo.get();
    artigo.setTituloDoArtigo(novoArtigo.getTituloDoArtigo());
    artigo.setCorpo(novoArtigo.getCorpo());

  }

  public void deletarArtigo(String tituloDoArtigo, String nomeDoAutor) {
    Optional<Artigo> optionalArtigo = filteArtigo(tituloDoArtigo, nomeDoAutor);

    if (optionalArtigo.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artigo não pode ser deletado");
    }
    listaDeArtigosPrincipal.remove(optionalArtigo.get());
  }

  private Optional<Artigo> filteArtigo(String tituloDoArtigo, String nomeDoAutor) {
    return listaDeArtigosPrincipal.stream()
        .filter(artigo -> artigo.getTituloDoArtigo().equals(tituloDoArtigo))
        .filter(artigo -> artigo.getAutor().getName().equals(nomeDoAutor))
        .findFirst();
  }

}
