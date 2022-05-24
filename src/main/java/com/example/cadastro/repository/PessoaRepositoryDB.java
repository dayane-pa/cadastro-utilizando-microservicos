package com.example.cadastro.repository;

import com.example.cadastro.domain.Pessoa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepositoryDB extends CrudRepository<Pessoa, Long> {

  Optional<List<Pessoa>> findByNameAndLastName(String name, String lastName);

  List<Pessoa> findAll ();
}
