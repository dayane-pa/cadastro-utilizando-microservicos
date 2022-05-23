package com.example.cadastro.repository;

import com.example.cadastro.domain.Pessoa;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface PessoaRepositoryDB extends CrudRepository<Pessoa, Long> {

}
