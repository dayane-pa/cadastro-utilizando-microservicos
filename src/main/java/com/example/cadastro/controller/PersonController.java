package com.example.cadastro.controller;

import static java.util.Objects.isNull;

import com.example.cadastro.domain.Pessoa;
import com.example.cadastro.repository.PessoaRepositoryDB;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/person")
public class PersonController {

  private PessoaRepositoryDB repository;

  public PersonController(PessoaRepositoryDB repository) {
    this.repository = repository;
  }

  @GetMapping()
  public ResponseEntity<List<Pessoa>> buscarPessoas(@RequestParam(value = "name", required = true)
  String nomeDaPessoa, @RequestParam(value = "lastname", required = true) String lastName) {

    Optional<List<Pessoa>> pessoasOptional = repository.findByNameAndLastName(nomeDaPessoa,
        lastName);

    if (pessoasOptional.isPresent()) {

      return new ResponseEntity<>(pessoasOptional.get(), HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pessoa não encontrada. ");
  }

  @GetMapping("/list")
  public ResponseEntity<List<Pessoa>> buscarTodasPessoas() {

    List<Pessoa> pessoas = repository.findAll();

    if (pessoas.size() > 0) {

      return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pessoa não encontrada. ");
  }

  @PostMapping
  public ResponseEntity<Void> addPerson(@RequestBody @Valid Pessoa pessoa) {

    Pessoa pessoaSalva = repository.save(pessoa);

    if (isNull(pessoaSalva)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "The person exist. " + pessoa);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<Void> putPerson(@RequestBody Pessoa pessoa) {

    Pessoa pessoaAtualizada = repository.save(pessoa);

    if (isNull(pessoaAtualizada)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Não foi possível atualizar. " + pessoa);
    }
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @DeleteMapping
  public ResponseEntity<Void> deletePerson(@RequestParam(value = "id") long id) {

    repository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}