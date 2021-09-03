package com.patronus.harrypotternaoreativo.Controller;


import com.patronus.harrypotternaoreativo.Entity.Casa;
import com.patronus.harrypotternaoreativo.Entity.Personagem;
import com.patronus.harrypotternaoreativo.Repository.PersonagemRepository;
import com.patronus.harrypotternaoreativo.Request.PersonagemRequest;
import com.patronus.harrypotternaoreativo.Response.PersonagemResponse;
import com.patronus.harrypotternaoreativo.Service.PersonagemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hp/person")
@AllArgsConstructor
public class PersonagemRestController {

    private final PersonagemService personagemService;

    @PostMapping("add")
    public PersonagemResponse addPersonagem(@RequestBody PersonagemRequest personagemRequest) {
        Personagem personagem = personagemRequest.convert();
        personagem.setId_casa(personagemService.sortearCasa().getId());
        personagemService.gravar(personagem);
        Casa casa = personagemService.retornaDetalhesCasaAtribuida(personagem);
        return new PersonagemResponse(personagem, casa);
    }

    @GetMapping("{nome}")
    public ResponseEntity<PersonagemResponse> consultarPersonagem(@PathVariable String nome) {

        Personagem personagem = personagemService.consultaPersonagem(nome);
        Casa casa = personagemService.retornaDetalhesCasaAtribuida(personagem);

        return ResponseEntity.ok(new PersonagemResponse(personagem, casa));

    }


    @GetMapping("todos")
    public ResponseEntity<List<PersonagemResponse>> listarTodos() {
        List<Personagem> personagens = personagemService.listarTodos();
        List<PersonagemResponse> personagensResponse = personagemService.converter(personagens);
        return ResponseEntity.ok().body(personagensResponse);
    }

}


