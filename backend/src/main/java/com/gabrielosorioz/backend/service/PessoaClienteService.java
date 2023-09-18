package com.gabrielosorioz.backend.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gabrielosorioz.backend.dto.PessoaClienteRequestDTO;
import com.gabrielosorioz.backend.entity.Pessoa;
import com.gabrielosorioz.backend.repository.PessoaClienteRepository;


@Service
public class PessoaClienteService {
    
    @Autowired
    private PessoaClienteRepository pessoaClienteRepository;

    @Autowired
    private PermissaoPessoaService permissaoPessoaService;

    @Autowired
    private EmailService emailService;

    private String mensagem = "O registro na loja foi realizado com Sucesso. Em breve você receberá a senha de acesso por e-mail" ;

    public Pessoa registrar(PessoaClienteRequestDTO pessoaClienteRequestDTO){
        Pessoa pessoa = new PessoaClienteRequestDTO().converter(pessoaClienteRequestDTO);
        pessoa.setDataCriacao(new Date());
        Pessoa newPessoa = pessoaClienteRepository.saveAndFlush(pessoa);
        permissaoPessoaService.vincularPessoaPermissaoCliente(newPessoa);
        emailService.enviarEmailTexto(newPessoa.getEmail(), "Cadastro na Loja MegaSource", mensagem);

        return newPessoa;
    }
}
