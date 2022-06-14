package com.example.cadastro.converters;

import com.example.cadastro.domain.ArtigoEntity;
import com.example.cadastro.dto.ArtigoDto;
import com.example.cadastro.dto.PessoaDto;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ArtigoConverter {

  private PessoaConverter pessoaConverter;
  public ArtigoConverter(PessoaConverter pessoaConverter) {
    this.pessoaConverter = pessoaConverter;
  }
  public ArtigoDto converterEntidadeDeArtigoParaDto(ArtigoEntity artigoEntity) {

    PessoaDto pessoaDto = pessoaConverter.converterEntidadeDePessoaParaDto(artigoEntity.getAutor());

    return ArtigoDto.builder()
        .withTituloDoArtigo(artigoEntity.getTituloDoArtigo())
        .withAutor(pessoaDto)
        .withCorpo(artigoEntity.getCorpo())
        .withId(artigoEntity.getId())
        .withViews(Optional.ofNullable(artigoEntity.getViews()).orElse(0L))
        .withDataDaPublicacao(artigoEntity.getDataDaPublicacao())
        .build();

  }

  public ArtigoEntity converterArtigoDtoEmEntidade (ArtigoDto artigoDto){

    ArtigoEntity artigoEntity= new ArtigoEntity();

    artigoEntity.setViews(artigoDto.getViews());
    artigoEntity.setTituloDoArtigo(artigoDto.getTituloDoArtigo());
    artigoEntity.setId(artigoDto.getId());
    artigoEntity.setCorpo(artigoDto.getCorpo());
    artigoEntity.setDataDaPublicacao(artigoDto.getDataDaPublicacao());
    artigoEntity.setAutor(pessoaConverter.converterPessoaDtoParaEntidade(artigoDto.getAutor()));

    return artigoEntity;
  }

}
