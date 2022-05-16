package com.example.cadastro.controller;

import static java.util.Objects.nonNull;

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
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/artigo")
public class ArtigoController {

  private ArtigoRepository artigoRepository;

  public ArtigoController(ArtigoRepository artigoRepository) {
    this.artigoRepository = artigoRepository;
  }

  @PostMapping()
  public ResponseEntity<Void> adicionarArtigo(@RequestBody @Valid Artigo artigo) {
    boolean adicionado = artigoRepository.adicionarArtigo(artigo);

    if (!adicionado) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Artigo já existente. " + artigo);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping()
  public ResponseEntity<List<Artigo>> pegarArtigo(
      @RequestParam(value = "nomedoautor", required = false) String nomeDoAutor,
      @RequestParam(value = "nomedotitulo", required = false) String tituloDoArtigo) {

    List<Artigo> listaDeArtigo = artigoRepository.buscarArtigo(nomeDoAutor, tituloDoArtigo);

    if (listaDeArtigo.size() == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Não encontrado, dados incorretos. " + (nonNull(nomeDoAutor) ? nomeDoAutor : "") + " " +
              (nonNull(tituloDoArtigo) ? tituloDoArtigo : ""));
    }

    return new ResponseEntity<>(listaDeArtigo, HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<Void> atualizaArtigo(@RequestBody Artigo artigo) {

    boolean atualizou = artigoRepository.atualizarArtigo(artigo);

    if (!atualizou) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Artigo não pode ser alterado, verifique o nome do " + "autor e data " + artigo);
    }

    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @DeleteMapping
  public ResponseEntity<Void> deletarArtigo(
      @RequestParam(value = "titulodoartigo") String tituloDoArtigo,
      @RequestParam(value = "nomedoautor") String nomeDoAutor) {

    boolean deletou = artigoRepository.deletarArtigo(tituloDoArtigo, nomeDoAutor);

    if (!deletou) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artigo não pode ser deletado");
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
