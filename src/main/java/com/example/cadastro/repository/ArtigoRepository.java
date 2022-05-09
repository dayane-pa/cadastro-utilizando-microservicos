package com.example.cadastro.repository;


import com.example.cadastro.domain.Artigo;
import com.example.cadastro.domain.Pessoa;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ArtigoRepository {

  private Pessoa pessoa = new Pessoa();

  private List<Artigo> listaDeArtigosPrincipal = new ArrayList<>();


  public void adicionarArtigo(Artigo novoArtigo) {

    listaDeArtigosPrincipal.forEach(artigo -> {

      if (artigo.getTituloDoArtigo().equals(novoArtigo.getTituloDoArtigo()) && artigo.getAutor()
          .getName().equals(novoArtigo.getAutor().getName())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Artigo já existente. " + novoArtigo);
      }
    });

    listaDeArtigosPrincipal.add(novoArtigo);

  }

  public List<Artigo> buscarArtigo(String nomeDoAutor, String tituloDoArtigo) {

    if (nomeDoAutor == null && tituloDoArtigo == null) {
      return listaDeArtigosPrincipal;
    }

    List<Artigo> artigosEncontrados = new ArrayList<>();

    if (Objects.nonNull(nomeDoAutor)) {
      artigosEncontrados.addAll(listaDeArtigosPrincipal.stream()
          .filter(artigo -> artigo.getAutor().getName().equals(nomeDoAutor))
          .collect(Collectors.toList()));

      Long quantidade = listaDeArtigosPrincipal.stream()
          .filter(artigo -> artigo.getAutor().getName().equals(nomeDoAutor))
          .collect(Collectors.counting());
    }

    if (Objects.nonNull(tituloDoArtigo)) {
      artigosEncontrados.addAll(listaDeArtigosPrincipal.stream()
          .filter(artigo -> artigo.getTituloDoArtigo().equals(tituloDoArtigo))
          .collect(Collectors.toList()));
    }

//        listaDeArtigosPrincipal.forEach(artigo -> {
//
//            if (Objects.nonNull(nomeDoAutor)) {
//                if (artigo.getAutor().getName().contains(nomeDoAutor)) {
//                    artigosEncontrados.add(artigo);
//                }
//            }
//
//            if (Objects.nonNull(tituloDoArtigo)) {
//                if (artigo.getTituloDoArtigo().contains(tituloDoArtigo)) {
//                    artigosEncontrados.add(artigo);
//                }
//            }
//        });

    if (artigosEncontrados.size() == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Não encontrado, dados incorretos. " + (Objects.nonNull(nomeDoAutor) ? nomeDoAutor : "")
              + " " + (Objects.nonNull(tituloDoArtigo) ? tituloDoArtigo : ""));
    }

    return artigosEncontrados;
  }


  public void atualizarArtigo(Artigo novoArtigo) {

    listaDeArtigosPrincipal.forEach(artigo -> {

      if (artigo.getAutor().getName().equals(novoArtigo.getAutor().getName())
          && artigo.getDataDaPublicacao().equals(novoArtigo.getDataDaPublicacao())) {

        artigo.setTituloDoArtigo(novoArtigo.getTituloDoArtigo());
        artigo.setCorpo(novoArtigo.getCorpo());
        return;
      }
    });

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Artigo não pode ser alterado, verifique o nome do " + "autor e data " + novoArtigo);

  }

  public void deletarArtigo(String tituloDoArtigo, String nomeDoAutor) {

    for (Artigo artigo : listaDeArtigosPrincipal) {
      if (artigo.getTituloDoArtigo().equals(tituloDoArtigo) && artigo.getAutor().getName()
          .equals(nomeDoAutor)) {
        listaDeArtigosPrincipal.remove(artigo);
        return;
      }
    }

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artigo não pode ser deletado");
  }
}

// TODO: 04/05/22 estudar stream de listas

