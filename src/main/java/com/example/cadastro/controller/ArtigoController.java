package com.example.cadastro.controller;

import static java.util.Objects.isNull;

import com.example.cadastro.converters.ArtigoConverter;
import com.example.cadastro.domain.ArtigoEntity;
import com.example.cadastro.dto.ArtigoDto;
import com.example.cadastro.repository.ArtigoRepositoryDB;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
  private ArtigoConverter artigoConverter;

  public ArtigoController(ArtigoRepositoryDB artigoRepositoryDB, ArtigoConverter artigoConverter) {
    this.artigoRepositoryDB = artigoRepositoryDB;
    this.artigoConverter = artigoConverter;
  }

  @PostMapping()
  public ResponseEntity<Void> adicionarArtigo(@RequestBody @Valid ArtigoDto artigoDto) {

    ArtigoEntity artigoEntity = artigoConverter.converterArtigoDtoEmEntidade(artigoDto);

    ArtigoEntity artigoEntityAdicionado = artigoRepositoryDB.save(artigoEntity);


    if (isNull(artigoEntityAdicionado)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Artigo já existente. " + artigoEntity);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ArtigoDto> pegarArtigo(@PathVariable(value = "id") long id) {

    Optional<ArtigoEntity> optionalArtigo = artigoRepositoryDB.findById(id);

    if (optionalArtigo.isPresent()) {
      ArtigoEntity artigoEntity = atualizarViewls(optionalArtigo);
      artigoEntity = optionalArtigo.get();
      ArtigoDto artigoDto = artigoConverter.converterEntidadeDeArtigoParaDto(artigoEntity);

      return new ResponseEntity<>(artigoDto, HttpStatus.OK);
    }

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Não encontrado, dados incorretos. " + id);
  }

  @GetMapping("/list")
  public ResponseEntity<List<ArtigoDto>> pegarTodosArtigos() {

    List<ArtigoEntity> listaDeArtigoEntities = artigoRepositoryDB.findAll();

    if (!listaDeArtigoEntities.isEmpty()) {

      List<ArtigoDto> artigoDtos = listaDeArtigoEntities.stream()
          .map(artigoEntity -> artigoConverter.converterEntidadeDeArtigoParaDto(artigoEntity))
          .collect(Collectors.toList());

      return new ResponseEntity<>(artigoDtos, HttpStatus.OK);

    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não encontrado, dados incorretos. ");
  }

  @PutMapping
  public ResponseEntity<Void> atualizaArtigo(@RequestBody ArtigoEntity artigoEntityNovo) {

    Optional<ArtigoEntity> artigoEncontrado = artigoRepositoryDB.findById(artigoEntityNovo.getId());

    if (artigoEncontrado.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Artigo não pode ser alterado, verifique o nome do " + "autor e data "
              + artigoEntityNovo);
    }

    ArtigoEntity artigoEntityNoBD = artigoEncontrado.get();
    artigoEntityNoBD.setTituloDoArtigo(artigoEntityNovo.getTituloDoArtigo());
    artigoEntityNoBD.setAutor(artigoEntityNovo.getAutor());
    artigoEntityNoBD.setCorpo(artigoEntityNovo.getCorpo());
    artigoEntityNoBD.setDataDaPublicacao(artigoEntityNovo.getDataDaPublicacao());

    artigoRepositoryDB.save(artigoEntityNoBD);

    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @DeleteMapping
  public ResponseEntity<Void> deletarArtigo(@RequestParam(value = "id") Long id) {

    artigoRepositoryDB.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);

  }

  private ArtigoEntity atualizarViewls(Optional<ArtigoEntity> optionalArtigo) {
    ArtigoEntity artigoEntity = optionalArtigo.get();
    Long views = Optional.ofNullable(artigoEntity.getViews()).orElse(0L);
    views++;
    artigoEntity.setViews(views);
    return artigoRepositoryDB.save(artigoEntity);
  }

  // TODO: 02/06/22 criar o DTO DE PESSOA criar o converter
}

