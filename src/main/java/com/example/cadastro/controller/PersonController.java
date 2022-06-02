package com.example.cadastro.controller;

import static java.util.Objects.isNull;

import com.example.cadastro.domain.PessoaEntity;
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
  public ResponseEntity<List<PessoaEntity>> buscarPessoas(@RequestParam(value = "name", required = true)
  String nomeDaPessoa, @RequestParam(value = "lastname", required = true) String lastName) {

    Optional<List<PessoaEntity>> pessoasOptional = pessoaRepositoryDB.findByNameAndLastName(nomeDaPessoa,
        lastName);

    if (pessoasOptional.isPresent()) {

      return new ResponseEntity<>(pessoasOptional.get(), HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pessoa não encontrada. ");
  }

  @GetMapping("/{id}")
  public ResponseEntity<PessoaEntity> quantidadeDeViews(@PathVariable(value = "id")  Long id) {

    Optional<PessoaEntity> pessoasOptional = pessoaRepositoryDB.findById(id);

    if (pessoasOptional.isPresent()) {
      return new ResponseEntity<>(pessoasOptional.get(), HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pessoa não encontrada. ");
  }


  @GetMapping("/list")
  public ResponseEntity<List<PessoaEntity>> buscarTodasPessoas() {

    List<PessoaEntity> pessoaEntities = pessoaRepositoryDB.findAll();

    if (pessoaEntities.size() > 0) {

      return new ResponseEntity<>(pessoaEntities, HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pessoa não encontrada. ");
  }

  @PostMapping
  public ResponseEntity<Void> addPerson(@RequestBody @Valid PessoaEntity pessoaEntity) {

    PessoaEntity pessoaEntitySalva = pessoaRepositoryDB.save(pessoaEntity);

    if (isNull(pessoaEntitySalva)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "The person exist. " + pessoaEntity);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<Void> putPerson(@RequestBody PessoaEntity pessoaEntity) {

    Optional<PessoaEntity> pessoaExistenteOptional = pessoaRepositoryDB.findById(pessoaEntity.getId());

    if (pessoaExistenteOptional.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Não foi possível atualizar. " + pessoaExistenteOptional.get());
    }
    pessoaRepositoryDB.save(pessoaEntity);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @DeleteMapping
  public ResponseEntity<Void> deletePerson(@RequestParam(value = "id") long id) {

    pessoaRepositoryDB.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }


}