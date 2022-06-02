package com.example.cadastro.repository;

import com.example.cadastro.domain.ArtigoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtigoRepositoryDB extends CrudRepository<ArtigoEntity, Long> {

  Optional<List<ArtigoEntity>> findBytituloDoArtigo(String tituloDoArtigo);

  Optional<ArtigoEntity> findById(long id);

  List<ArtigoEntity> findAll();
}
