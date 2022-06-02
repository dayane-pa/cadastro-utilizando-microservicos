package com.example.cadastro.repository;

import static java.util.Objects.isNull;

import com.example.cadastro.domain.PessoaEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaRepositoryInMemory {

  private final List<PessoaEntity> pessoaEntityListDataBase = new ArrayList<>();

  public boolean addNewPerson(PessoaEntity newPessoaEntity) {
    Optional<PessoaEntity> optionalPessoa = pessoaEntityListDataBase.stream()
        .filter(pessoa -> pessoa.getName().equals(newPessoaEntity.getName()))
        .filter(pessoa -> pessoa.getLastName().equals(newPessoaEntity.getLastName()))
        .filter(pessoa -> pessoa.getBirthDate().equals(newPessoaEntity.getBirthDate()))
        .findFirst();

  //  newPessoa.setId(ContadorUtil.contadorId());
   return pessoaEntityListDataBase.add(newPessoaEntity);
  }

  public List<PessoaEntity> buscarPessoas(String nomeDaPessoa) {

    if (isNull(nomeDaPessoa)) {
      return pessoaEntityListDataBase;
    }

    return pessoaEntityListDataBase.stream()
        .filter(pessoa -> pessoa.getName().equals(nomeDaPessoa))
        .collect(Collectors.toList());

  }

  public boolean atualizarPessoa(PessoaEntity novaPessoaEntity) {

    Optional<PessoaEntity> optionalPessoa = pessoaEntityListDataBase.stream()
        .filter(pessoa -> pessoa.getId() == novaPessoaEntity.getId())
        .findFirst();

    if (optionalPessoa.isEmpty()) {
      return false;
    }

    PessoaEntity pessoaEntity = optionalPessoa.get();
    pessoaEntity.setName(novaPessoaEntity.getName());
    pessoaEntity.setLastName(novaPessoaEntity.getLastName());
    pessoaEntity.setBirthDate(novaPessoaEntity.getBirthDate());
    return true;
  }

  public boolean delete(String name, String lastName) {
    Optional<PessoaEntity> optionalPessoa = pessoaEntityListDataBase.stream()
        .filter(pessoa -> pessoa.getName().equals(name))
        .filter(pessoa -> pessoa.getLastName().equals(lastName))
        .findFirst();

   return pessoaEntityListDataBase.remove(optionalPessoa.get());
  }

}
