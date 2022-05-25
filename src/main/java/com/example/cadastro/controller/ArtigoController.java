package com.example.cadastro.controller;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.example.cadastro.domain.Artigo;
import com.example.cadastro.repository.ArtigoRepositoryDB;
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
@RequestMapping("/artigo")
public class ArtigoController {

  private ArtigoRepositoryDB artigoRepositoryDB;

  public ArtigoController(ArtigoRepositoryDB artigoRepositoryDB) {
    this.artigoRepositoryDB = artigoRepositoryDB;
  }

  @PostMapping()
  public ResponseEntity<Void> adicionarArtigo(@RequestBody @Valid Artigo artigo) {
    Artigo artigoAdicionado = artigoRepositoryDB.save(artigo);

    if (isNull(artigoAdicionado)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Artigo já existente. " + artigo);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<List<Artigo>> pegarArtigos(
      @RequestParam(value = "titulodoartigo'", required = true) String tituloDoArtigo) {

    Optional<List<Artigo>> optionalArtigo = artigoRepositoryDB.findByTituloDoArtigo(tituloDoArtigo);


    if (optionalArtigo.isPresent()) {
      return new ResponseEntity<>(optionalArtigo.get(), HttpStatus.OK);

    }

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Não encontrado, dados incorretos. " + " " +
            (nonNull(tituloDoArtigo) ? tituloDoArtigo : ""));
  }

  @GetMapping("/list")
  public ResponseEntity<List<Artigo>> pegarTodosArtigos() {

    List<Artigo> listaDeArtigo = artigoRepositoryDB.findAll();


    if (listaDeArtigo.size() > 0) {
      return new ResponseEntity<>(listaDeArtigo, HttpStatus.OK);

    }

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Não encontrado, dados incorretos. " );
  }

  @PutMapping
  public ResponseEntity<Void> atualizaArtigo(@RequestBody Artigo artigo) {

    Artigo pessoaAtualizada = artigoRepositoryDB.save(artigo);

    if (isNull(pessoaAtualizada)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Artigo não pode ser alterado, verifique o nome do " + "autor e data " + artigo);
    }

    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @DeleteMapping
  public ResponseEntity<Void> deletarArtigo(@RequestParam(value = "id") Long id) {

    artigoRepositoryDB.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);

  }
}

