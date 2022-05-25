package com.example.cadastro.repository;

import com.example.cadastro.domain.Artigo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtigoRepositoryDB extends CrudRepository<Artigo, Long> {


  Optional <List<Artigo>> findByTituloDoArtigo(String tituloDoArtigo);

  List<Artigo> findAll();

}
