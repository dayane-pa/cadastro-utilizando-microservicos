package com.example.cadastro.repository;

import com.example.cadastro.domain.Pessoa;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    // TODO: 26/04/22 criar um crud para artigo, novo repository, novo controle
    private final List<Pessoa> pessoaListDataBase = new ArrayList<>();

    public void addNewPerson(Pessoa newPessoa) {
        for (int index = 0; index < pessoaListDataBase.size(); index++) {
            Pessoa pessoa1 = pessoaListDataBase.get(index);
            if (pessoa1.getName().equals(newPessoa.getName()) && pessoa1.getLastName().equals(newPessoa.getLastName())
                    && pessoa1.getBirthDate().equals(newPessoa.getBirthDate())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The person exist. " + newPessoa);
            }
        }
        pessoaListDataBase.add(newPessoa);


    }

    public List<Pessoa> buscarPessoas(String nomeDaPessoa) {
        if (nomeDaPessoa == null) {
            return pessoaListDataBase;
        }


        List<Pessoa> finalList = new ArrayList<>();

        for (int index = 0; index < pessoaListDataBase.size(); index++) {

            Pessoa pessoa = pessoaListDataBase.get(index);
            if (pessoa.getName().equals(nomeDaPessoa)) {
                finalList.add(pessoa);
            }
        }
        return finalList;
    }

    public String getPersonBirthDate(String nomeDaPessoa) {

        for (int index = 0; index < pessoaListDataBase.size(); index++) {
            Pessoa pessoa = pessoaListDataBase.get(index);
            if (pessoa.getName().equals(nomeDaPessoa)) {
                return pessoa.getBirthDate();
            }
        }
        return null;
    }

    public void atualizarPessoa(Pessoa novaPessoa) {

        for (int index = 0; index < pessoaListDataBase.size(); index++) {
            Pessoa pessoaAntiga = pessoaListDataBase.get(index);
            if (pessoaAntiga.getId() == novaPessoa.getId()) {
                pessoaAntiga.setName(novaPessoa.getName());
                pessoaAntiga.setLastName(novaPessoa.getLastName());
                pessoaAntiga.setBirthDate(novaPessoa.getBirthDate());
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível atualizar. " + novaPessoa);
    }

    public void delete(String name, String lastName) {

        for (int index = 0; index < pessoaListDataBase.size(); index++) {
            Pessoa pessoa = pessoaListDataBase.get(index);
            if (pessoa.getName().equals(name) && pessoa.getLastName().equals(lastName)) {
                pessoaListDataBase.remove(pessoa);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa não encontrada. " + name + " " + lastName);
    }
}
