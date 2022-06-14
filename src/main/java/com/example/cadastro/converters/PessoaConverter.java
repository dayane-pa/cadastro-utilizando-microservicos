package com.example.cadastro.converters;

import com.example.cadastro.domain.PessoaEntity;
import com.example.cadastro.dto.PessoaDto;
import org.springframework.stereotype.Service;

@Service
public class PessoaConverter {

  public PessoaDto converterEntidadeDePessoaParaDto (PessoaEntity pessoaEntity){

    return PessoaDto.builder()
        .withId(pessoaEntity.getId())
        .withBirthDate(pessoaEntity.getBirthDate())
        .withEmail(pessoaEntity.getEmail())
        .withName(pessoaEntity.getName())
        .withLastName(pessoaEntity.getLastName())
        .build();
  }

  public PessoaEntity converterPessoaDtoParaEntidade (PessoaDto pessoaDto){

    PessoaEntity pessoaEntity = new PessoaEntity();

    pessoaEntity.setBirthDate(pessoaDto.getBirthDate());
    pessoaEntity.setEmail(pessoaDto.getEmail());
    pessoaEntity.setId(pessoaDto.getId());
    pessoaEntity.setLastName(pessoaDto.getLastName());
    pessoaEntity.setName(pessoaDto.getName());

    return pessoaEntity;
  }

}
