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
import org.springframework.web.bind.annotation.PathVariable;
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

  private PessoaRepositoryDB pessoaRepositoryDB;

  public PersonController(PessoaRepositoryDB repository) {
    this.pessoaRepositoryDB = repository;
  }

  @GetMapping()
  public ResponseEntity<List<Pessoa>> buscarPessoas(@RequestParam(value = "name", required = true)
  String nomeDaPessoa, @RequestParam(value = "lastname", required = true) String lastName) {

    Optional<List<Pessoa>> pessoasOptional = pessoaRepositoryDB.findByNameAndLastName(nomeDaPessoa,
        lastName);

    if (pessoasOptional.isPresent()) {

      return new ResponseEntity<>(pessoasOptional.get(), HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pessoa não encontrada. ");
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pessoa> quantidadeDeViews(@PathVariable(value = "id")  Long id) {

    Optional<Pessoa> pessoasOptional = pessoaRepositoryDB.findById(id);

    if (pessoasOptional.isPresent()) {
      Pessoa pessoa = buscarPessoaPorId(pessoasOptional);
      return new ResponseEntity<>(pessoasOptional.get(), HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pessoa não encontrada. ");
  }


  @GetMapping("/list")
  public ResponseEntity<List<Pessoa>> buscarTodasPessoas() {

    List<Pessoa> pessoas = pessoaRepositoryDB.findAll();

    if (pessoas.size() > 0) {

      return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pessoa não encontrada. ");
  }

  @PostMapping
  public ResponseEntity<Void> addPerson(@RequestBody @Valid Pessoa pessoa) {

    Pessoa pessoaSalva = pessoaRepositoryDB.save(pessoa);

    if (isNull(pessoaSalva)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "The person exist. " + pessoa);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<Void> putPerson(@RequestBody Pessoa pessoa) {

    Optional<Pessoa> pessoaExistenteOptional = pessoaRepositoryDB.findById(pessoa.getId());

    if (pessoaExistenteOptional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Não foi possível atualizar. " + pessoaExistenteOptional.get());
    }
    pessoaRepositoryDB.save(pessoa);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @DeleteMapping
  public ResponseEntity<Void> deletePerson(@RequestParam(value = "id") long id) {

    pessoaRepositoryDB.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  private Pessoa buscarPessoaPorId(Optional<Pessoa> pessoasOptional) {
    Pessoa pessoa = pessoasOptional.get();
    Long viewsPessoa = Optional.ofNullable(pessoa.getViewsPessoa()).orElse(0L);
    viewsPessoa++;
    pessoa.setViewsPessoa(viewsPessoa);
    pessoaRepositoryDB.save(pessoa);
    return pessoa;
  }

}