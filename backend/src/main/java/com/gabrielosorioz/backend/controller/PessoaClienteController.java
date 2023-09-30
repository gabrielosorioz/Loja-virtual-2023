package com.gabrielosorioz.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gabrielosorioz.backend.dto.PessoaClienteRequestDTO;
import com.gabrielosorioz.backend.entity.Pessoa;
import com.gabrielosorioz.backend.service.PessoaClienteService;


@RestController
@RequestMapping("api/cliente")
@CrossOrigin
public class PessoaClienteController {
    
    @Autowired
    private PessoaClienteService pessoaClienteService;

     @PostMapping("/")
     public Pessoa inserir(@RequestBody PessoaClienteRequestDTO pessoaClienteRequestDTO){    
        return pessoaClienteService.registrar(pessoaClienteRequestDTO);
     
    }

}
