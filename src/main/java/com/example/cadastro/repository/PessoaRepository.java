package com.example.cadastro.repository;

import static java.util.Objects.isNull;

import com.example.cadastro.domain.Pessoa;
import com.example.cadastro.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaRepository {

  private final List<Pessoa> pessoaListDataBase = new ArrayList<>();

  public boolean addNewPerson(Pessoa newPessoa) {
    Optional<Pessoa> optionalPessoa = pessoaListDataBase.stream()
        .filter(pessoa -> pessoa.getName().equals(newPessoa.getName()))
        .filter(pessoa -> pessoa.getLastName().equals(newPessoa.getLastName()))
        .filter(pessoa -> pessoa.getBirthDate().equals(newPessoa.getBirthDate()))
        .findFirst();

    newPessoa.setId(ContadorUtil.contadorId());
   return pessoaListDataBase.add(newPessoa);
  }

  public List<Pessoa> buscarPessoas(String nomeDaPessoa) {

    if (isNull(nomeDaPessoa)) {
      return pessoaListDataBase;
    }

    return pessoaListDataBase.stream()
        .filter(pessoa -> pessoa.getName().equals(nomeDaPessoa))
        .collect(Collectors.toList());

  }

  public boolean atualizarPessoa(Pessoa novaPessoa) {

    Optional<Pessoa> optionalPessoa = pessoaListDataBase.stream()
        .filter(pessoa -> pessoa.getId() == novaPessoa.getId())
        .findFirst();

    if (optionalPessoa.isEmpty()) {
      return false;
    }

    Pessoa pessoa = optionalPessoa.get();
    pessoa.setName(novaPessoa.getName());
    pessoa.setLastName(novaPessoa.getLastName());
    pessoa.setBirthDate(novaPessoa.getBirthDate());
    return true;
  }

  public boolean delete(String name, String lastName) {
    Optional<Pessoa> optionalPessoa = pessoaListDataBase.stream()
        .filter(pessoa -> pessoa.getName().equals(name))
        .filter(pessoa -> pessoa.getLastName().equals(lastName))
        .findFirst();

   return pessoaListDataBase.remove(optionalPessoa.get());
  }

}
