package com.example.cadastro.controller;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.example.cadastro.domain.Artigo;
import com.example.cadastro.repository.ArtigoRepositoryDB;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@RequestMapping("/artigo")
@JsonInclude(Include.NON_NULL)
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
  public ResponseEntity<List<Artigo>> pegarArtigo(
      @RequestParam(value = "titulodoartigo", required = true) String tituloDoArtigo) {

    Optional<List<Artigo>> optionalArtigo = artigoRepositoryDB.findBytituloDoArtigo(tituloDoArtigo);

    if (optionalArtigo.isPresent()) {

      artigoRepositoryDB.findBytituloDoArtigo(tituloDoArtigo);
      return new ResponseEntity<>(optionalArtigo.get(), HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Não encontrado, dados incorretos. " + " " +
            (nonNull(tituloDoArtigo) ? tituloDoArtigo : ""));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Artigo> pegarArtigo(
      @PathVariable(value = "id") long id) {

    Optional<Artigo> optionalArtigo = artigoRepositoryDB.findById(id);

    if (optionalArtigo.isPresent()) {
      Artigo artigo = atualizarViewls(optionalArtigo);
      return new ResponseEntity<>(artigo, HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Não encontrado, dados incorretos. " + " " +
            (nonNull(optionalArtigo) ? optionalArtigo : ""));
  }

  @GetMapping("/list")
  public ResponseEntity<List<Artigo>> pegarTodosArtigos() {

    List<Artigo> listaDeArtigo = artigoRepositoryDB.findAll();

    if (listaDeArtigo.size() > 0) {
      return new ResponseEntity<>(listaDeArtigo, HttpStatus.OK);

    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Não encontrado, dados incorretos. ");
  }

  @PutMapping
  public ResponseEntity<Void> atualizaArtigo(@RequestBody Artigo artigoNovo) {

    Optional<Artigo> artigoEncontrado = artigoRepositoryDB.findById(artigoNovo.getId());

    if (artigoEncontrado.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Artigo não pode ser alterado, verifique o nome do " + "autor e data " + artigoNovo);
    }

    Artigo artigoNoBD = artigoEncontrado.get();
    artigoNoBD.setTituloDoArtigo(artigoNovo.getTituloDoArtigo());
    artigoNoBD.setAutor(artigoNovo.getAutor());
    artigoNoBD.setCorpo(artigoNovo.getCorpo());
    artigoNoBD.setDataDaPublicacao(artigoNovo.getDataDaPublicacao());

    artigoRepositoryDB.save(artigoNoBD);

    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @DeleteMapping
  public ResponseEntity<Void> deletarArtigo(@RequestParam(value = "id") Long id) {

    artigoRepositoryDB.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);

  }

  private Artigo atualizarViewls(Optional<Artigo> optionalArtigo) {
    Artigo artigo = optionalArtigo.get();
    Long views = Optional.ofNullable(artigo.getViews()).orElse(0L);
    views++;
    artigo.setViews(views);
    return artigoRepositoryDB.save(artigo);
  }

}

