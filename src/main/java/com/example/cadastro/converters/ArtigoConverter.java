package com.example.cadastro.converters;

import com.example.cadastro.domain.ArtigoEntity;
import com.example.cadastro.dto.ArtigoDto;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ArtigoConverter {

  public ArtigoDto converterEntidadeParaDto(ArtigoEntity artigoEntity) {

    return ArtigoDto.builder()
        .withTituloDoArtigo(artigoEntity.getTituloDoArtigo())
        .withAutor(artigoEntity.getAutor())
        .withCorpo(artigoEntity.getCorpo())
        .withId(artigoEntity.getId())
        .withViews(Optional.ofNullable(artigoEntity.getViews()).orElse(0L))
        .withDataDaPublicacao(artigoEntity.getDataDaPublicacao())
        .build();

//    artigoDto.setTituloDoArtigo(artigoEntity.getTituloDoArtigo());
//    artigoDto.setAutor(artigoEntity.getAutor());
//    artigoDto.setCorpo(artigoEntity.getCorpo());
//
//    Long views = Optional.ofNullable(artigoEntity.getViews()).orElse(0L);
//    artigoDto.setViews(views);
//    artigoDto.setDataDaPublicacao(artigoEntity.getDataDaPublicacao());
//    artigoDto.setId(artigoEntity.getId());

   // return artigoDto;
  }

}
