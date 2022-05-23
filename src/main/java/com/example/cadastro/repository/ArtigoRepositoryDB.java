package com.example.cadastro.repository;

import com.example.cadastro.domain.Artigo;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface ArtigoRepositoryDB extends CrudRepository<Artigo, Long> {


}
