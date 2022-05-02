package com.example.cadastro.repository;


import com.example.cadastro.domain.Artigo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ArtigoRepository {

    private List<Artigo> listaDeArtigosPrincipal = new ArrayList<>();


    public void adicionarArtigo(Artigo novoArtigo) {

        for (int index = 0; index < listaDeArtigosPrincipal.size(); index++) {

            Artigo artigo1 = listaDeArtigosPrincipal.get(index);

            if (artigo1.getTituloDoArtigo().equals(novoArtigo.getTituloDoArtigo()) && artigo1.getNomeDoAutor()
                    .equals(novoArtigo.getNomeDoAutor())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artigo já existente. " + novoArtigo);
            }
        }
        listaDeArtigosPrincipal.add(novoArtigo);
    }

    public List<Artigo> buscarArtigo(String nomeDoAutor, String tituloDoArtigo) {

        if (nomeDoAutor == null && tituloDoArtigo == null) {
            return listaDeArtigosPrincipal;
        }

        List<Artigo> artigosEncontrados = new ArrayList<>();

        for (int index = 0; index < listaDeArtigosPrincipal.size(); index++) {

            Artigo artigo = listaDeArtigosPrincipal.get(index);

            if (Objects.nonNull(nomeDoAutor)) {
                if (artigo.getNomeDoAutor().contains(nomeDoAutor)) {
                    artigosEncontrados.add(artigo);
                }
            }

            if (Objects.nonNull(tituloDoArtigo)) {
                if (artigo.getTituloDoArtigo().contains(tituloDoArtigo)) {
                    artigosEncontrados.add(artigo);
                }
            }
        }

        if (artigosEncontrados.size() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não encontrado, dados incorretos. " +
                    (Objects.nonNull(nomeDoAutor) ? nomeDoAutor : "") + " " + (Objects.nonNull(tituloDoArtigo) ? tituloDoArtigo : ""));
        }

        return artigosEncontrados;
    }


    public void atualizarArtigo(Artigo novoArtigo) {

        for (int index = 0; index < listaDeArtigosPrincipal.size(); index++) {

            Artigo artigo = listaDeArtigosPrincipal.get(index);

            if (artigo.getNomeDoAutor().equals(novoArtigo.getNomeDoAutor())
                    && artigo.getDataDaPublicacao().equals(novoArtigo.getDataDaPublicacao())) {

                artigo.setTituloDoArtigo(novoArtigo.getTituloDoArtigo());
                artigo.setCorpo(novoArtigo.getCorpo());
                return;
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artigo não pode ser alterado, verifique o nome do " +
                "autor e data " + novoArtigo);

    }

    public void deletarArtigo(String tituloDoArtigo, String nomeDoAutor) {

        for (int index = 0; index < listaDeArtigosPrincipal.size(); index++) {

            Artigo artigo = listaDeArtigosPrincipal.get(index);

            if (artigo.getTituloDoArtigo().equals(tituloDoArtigo) && artigo.getNomeDoAutor().
                    equals(nomeDoAutor)) {
                listaDeArtigosPrincipal.remove(artigo);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artigo não pode ser deletado");
    }
}
// TODO: 27/04/22 mandar para o git
