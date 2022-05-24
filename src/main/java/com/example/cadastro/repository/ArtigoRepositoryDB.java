package com.example.cadastro.repository;

import com.example.cadastro.domain.Artigo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtigoRepositoryDB extends CrudRepository<Artigo, Long> {


}
