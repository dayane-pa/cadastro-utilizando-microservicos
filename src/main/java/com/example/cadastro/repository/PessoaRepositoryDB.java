package com.example.cadastro.repository;

import com.example.cadastro.domain.PessoaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepositoryDB extends CrudRepository<PessoaEntity, Long> {

  Optional<List<PessoaEntity>> findByNameAndLastName(String name, String lastName);

  List<PessoaEntity> findAll ();

  Optional<PessoaEntity> findById(long Id);
}
