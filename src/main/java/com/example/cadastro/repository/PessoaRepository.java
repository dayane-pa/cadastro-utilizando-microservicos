package com.example.cadastro.repository;

import static java.util.Objects.isNull;

import com.example.cadastro.domain.Pessoa;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class PessoaRepository {
  private int contador = 0;
  private final List<Pessoa> pessoaListDataBase = new ArrayList<>();
  public void addNewPerson(Pessoa newPessoa) {
    Optional<Pessoa> optionalPessoa = pessoaListDataBase.stream()
        .filter(pessoa -> pessoa.getName().equals(newPessoa.getName()))
        .filter(pessoa -> pessoa.getLastName().equals(newPessoa.getLastName()))
        .filter(pessoa -> pessoa.getBirthDate().equals(newPessoa.getBirthDate()))
        .findFirst();

    if (optionalPessoa.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The person exist. " + optionalPessoa.get());
    }

    newPessoa.setId(getProximoIdPessoa());
    pessoaListDataBase.add(newPessoa);
  }
  public List<Pessoa> buscarPessoas(String nomeDaPessoa) {

    if (isNull(nomeDaPessoa)) {
      return pessoaListDataBase;
    }

    return pessoaListDataBase.stream()
        .filter(pessoa -> pessoa.getName().equals(nomeDaPessoa))
        .collect(Collectors.toList());

  }
  public void atualizarPessoa(Pessoa novaPessoa) {

    Optional<Pessoa> optionalPessoa = pessoaListDataBase.stream()
        .filter(pessoa -> pessoa.getId() == novaPessoa.getId())
        .findFirst();

    if (optionalPessoa.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Não foi possível atualizar. " + novaPessoa);
    }

    Pessoa pessoa = optionalPessoa.get();
    pessoa.setName(novaPessoa.getName());
    pessoa.setLastName(novaPessoa.getLastName());
    pessoa.setBirthDate(novaPessoa.getBirthDate());

  }
  public void delete(String name, String lastName) {
    Optional<Pessoa> optionalPessoa = pessoaListDataBase.stream()
        .filter(pessoa -> pessoa.getName().equals(name))
        .filter(pessoa -> pessoa.getLastName().equals(lastName))
        .findFirst();

    if (optionalPessoa.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Pessoa não encontrada. " + name + " " + lastName);
    }

    pessoaListDataBase.remove(optionalPessoa.get());
  }

  private int getProximoIdPessoa() {
    contador++;
    return contador;

  }

}
