package com.example.cadastro.controller;

import com.example.cadastro.domain.Pessoa;
import com.example.cadastro.repository.PessoaRepository;
import java.util.List;
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

    private PessoaRepository pessoaRepository;

    public PersonController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Pessoa>> buscarPessoas(@RequestParam(value = "name", required = false)
                                                      String nomeDaPessoa) {

        List<Pessoa> listaDePessoas = pessoaRepository.buscarPessoas(nomeDaPessoa);

        if (listaDePessoas.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pessoa não encontrada. ");
        }

        return new ResponseEntity<>(listaDePessoas, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Void> addPerson(@RequestBody @Valid Pessoa pessoa) {
        pessoaRepository.addNewPerson(pessoa);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> putPerson(@RequestBody Pessoa pessoa) {
        pessoaRepository.atualizarPessoa(pessoa);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePerson(@RequestParam("name") String name, @RequestParam("lastName")
    String lastName) {
        pessoaRepository.delete(name, lastName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}