package com.example.cadastro.repository;


import static java.util.Objects.nonNull;

import com.example.cadastro.domain.ArtigoEntity;
import com.example.cadastro.domain.PessoaEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ArtigoRepositoryInMemory {

  private PessoaEntity pessoaEntity = new PessoaEntity();
  private List<ArtigoEntity> listaDeArtigosPrincipal = new ArrayList<>();

  public boolean adicionarArtigo(ArtigoEntity novoArtigoEntity) {
    Optional<ArtigoEntity> optionalArtigo = filteArtigo(novoArtigoEntity.getTituloDoArtigo(),
        novoArtigoEntity.getAutor().getName());

   // novoArtigo.setId(ContadorUtil.contadorId());
    return listaDeArtigosPrincipal.add(novoArtigoEntity);
  }

  public List<ArtigoEntity> buscarArtigo(String nomeDoAutor, String tituloDoArtigo) {

    if (nomeDoAutor == null && tituloDoArtigo == null) {
      return listaDeArtigosPrincipal;
    }

    List<ArtigoEntity> artigosEncontrados = new ArrayList<>();

    if (nonNull(nomeDoAutor)) {
      artigosEncontrados.addAll(listaDeArtigosPrincipal.stream()
          .filter(artigoEntity -> artigoEntity.getAutor().getName().equals(nomeDoAutor))
          .collect(Collectors.toList()));
    }

    if (nonNull(tituloDoArtigo)) {
      artigosEncontrados.addAll(listaDeArtigosPrincipal.stream()
          .filter(artigoEntity -> artigoEntity.getTituloDoArtigo().equals(tituloDoArtigo))
          .collect(Collectors.toList()));
    }

    return artigosEncontrados;
  }

  public boolean atualizarArtigo(ArtigoEntity novoArtigoEntity) {

    Optional<ArtigoEntity> optionalArtigo = listaDeArtigosPrincipal.stream()
        .filter(
            artigoEntity -> artigoEntity.getAutor().getName().equals(novoArtigoEntity.getAutor().getName()))
        .filter(artigoEntity -> artigoEntity.getDataDaPublicacao().equals(novoArtigoEntity.getDataDaPublicacao()))
        .findFirst();

    if (optionalArtigo.isEmpty()) {
      return false;
    }
    ArtigoEntity artigoEntity = optionalArtigo.get();
    artigoEntity.setTituloDoArtigo(novoArtigoEntity.getTituloDoArtigo());
    artigoEntity.setCorpo(novoArtigoEntity.getCorpo());
    return true;
  }

  public boolean deletarArtigo(String tituloDoArtigo, String nomeDoAutor) {
    Optional<ArtigoEntity> optionalArtigo = filteArtigo(tituloDoArtigo, nomeDoAutor);

    return listaDeArtigosPrincipal.remove(optionalArtigo.get());
  }

  private Optional<ArtigoEntity> filteArtigo(String tituloDoArtigo, String nomeDoAutor) {
    return listaDeArtigosPrincipal.stream()
        .filter(artigoEntity -> artigoEntity.getTituloDoArtigo().equals(tituloDoArtigo))
        .filter(artigoEntity -> artigoEntity.getAutor().getName().equals(nomeDoAutor))
        .findFirst();
  }

}
