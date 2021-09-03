package com.patronus.harrypotternaoreativo.Service;

import com.google.gson.Gson;
import com.patronus.harrypotternaoreativo.Entity.Casa;
import com.patronus.harrypotternaoreativo.Entity.SorteioDoChapeu;
import com.patronus.harrypotternaoreativo.Entity.Personagem;
import com.patronus.harrypotternaoreativo.Repository.PersonagemRepository;
import com.patronus.harrypotternaoreativo.Response.PersonagemResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonagemService {

    private final PersonagemRepository personagemRepository;

    public SorteioDoChapeu sortearCasa() {
        String url = "https://api-harrypotter.herokuapp.com/sortinghat";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resposta = restTemplate.getForEntity(url, String.class);
        Gson gson = new Gson();
        SorteioDoChapeu sorteioDoChapeu = gson.fromJson(resposta.getBody(), SorteioDoChapeu.class);
        return sorteioDoChapeu;
    }

    public Personagem gravar(Personagem personagem) {
        personagemRepository.save(personagem);
        return personagem;
    }

    public Personagem consultaPersonagem(String nome) {

        return personagemRepository.findByName(nome);

    }

    public Casa retornaDetalhesCasaAtribuida(Personagem personagem) {

        String url = "https://api-harrypotter.herokuapp.com/house/" + personagem.getId_casa();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resposta = restTemplate.getForEntity(url, String.class);
        Gson gson = new Gson();
        Casa casa = gson.fromJson(resposta.getBody(), Casa.class);
        return casa;
    }

    public List<Personagem> listarTodos() {
        return personagemRepository.findAll();
    }

    public List<PersonagemResponse> converter(List<Personagem> personagens) {
        List<PersonagemResponse> lista = new ArrayList<>();
        for (Personagem personagem : personagens) {
            Casa casa = retornaDetalhesCasaAtribuida(personagem);
            PersonagemResponse personagemResponse = new PersonagemResponse(personagem, casa);
            lista.add(personagemResponse);
        }
        return lista;
    }
}
