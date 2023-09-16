package com.gabrielosorioz.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielosorioz.backend.entity.Permissao;
import com.gabrielosorioz.backend.service.PermissaoService;

@RestController
@RequestMapping("/api/permissao")
public class PermissaoController{

    @Autowired
    private PermissaoService permissaoService;
    
    @GetMapping("/")
    public List<Permissao> buscarTodos(){
        return permissaoService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permissao> buscarPorId(@PathVariable ("id") Long id){
        return ResponseEntity.ok(permissaoService.buscarPorId(id));

    }

    @PostMapping("/")
    public Permissao inserir(@RequestBody Permissao permissao){
        return permissaoService.inserir(permissao);
    }

    @PutMapping("/")
    public Permissao alterar(@RequestBody Permissao permissao){
        return permissaoService.alterar(permissao);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        permissaoService.excluir(id);
        return ResponseEntity.ok().build();

    }

}