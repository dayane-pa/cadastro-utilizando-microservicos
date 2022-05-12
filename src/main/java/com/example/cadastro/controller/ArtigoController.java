package com.example.cadastro.controller;

import com.example.cadastro.domain.Artigo;
import com.example.cadastro.repository.ArtigoRepository;
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


@RestController
@RequestMapping("/artigo")
public class ArtigoController {

  private ArtigoRepository artigoRepository;

  public ArtigoController(ArtigoRepository artigoRepository) {
    this.artigoRepository = artigoRepository;
  }
  @PostMapping()
  public ResponseEntity<Void> adicionarArtigo(@RequestBody @Valid Artigo artigo) {
    artigoRepository.adicionarArtigo(artigo);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
  @GetMapping()
  public ResponseEntity<List<Artigo>> pegarArtigo(
      @RequestParam(value = "nomedoautor", required = false) String nomeDoAutor,
      @RequestParam(value = "nomedotitulo", required = false) String nomeDoTitulo) {

    List<Artigo> listaDeArtigo = artigoRepository.buscarArtigo(nomeDoAutor, nomeDoTitulo);

    return new ResponseEntity<>(listaDeArtigo, HttpStatus.OK);
  }


  @PutMapping
  public ResponseEntity<Void> atualizaArtigo(@RequestBody Artigo artigo) {

    artigoRepository.atualizarArtigo(artigo);

    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }


  @DeleteMapping
  public ResponseEntity<Void> deletarArtigo(
      @RequestParam(value = "titulodoartigo") String tituloDoArtigo,
      @RequestParam(value = "nomedoautor") String nomeDoAutor) {

    artigoRepository.deletarArtigo(tituloDoArtigo, nomeDoAutor);

    return new ResponseEntity<>(HttpStatus.OK);
  }


}
