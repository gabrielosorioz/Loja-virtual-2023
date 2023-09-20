package com.gabrielosorioz.backend.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabrielosorioz.backend.entity.Pessoa;
import com.gabrielosorioz.backend.repository.PessoaRepository;

@Service
public class PessoaGerenciamentoService {
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired  
    private EmailService emailService;

    public String solicitarCodigo(String email){
        Pessoa pessoa = pessoaRepository.findByEmail(email);
        pessoa.setCodigoRecuperacaoSenha(getCodigoRecuperacaoSenha(pessoa.getId()));
        pessoa.setDataEnvioCodigo(new Date());
        pessoaRepository.saveAndFlush(pessoa);
        emailService.enviarEmailTexto(pessoa.getEmail(), "Código de recuperação de Senha ", 
        "Olá, o seu código para recuperação de senha "+
         "é o seguinte: "+pessoa.getCodigoRecuperacaoSenha());

        
        return "Código enviado";

    }

    public String alterarSenha(Pessoa pessoa){

        Pessoa pessoaBanco = pessoaRepository.findByEmailAndCodigoRecuperacaoSenha(pessoa.getEmail(), pessoa.getCodigoRecuperacaoSenha());
        
        if (pessoaBanco != null){
            Date diferenca = new Date(new Date().getTime() - pessoaBanco.getDataEnvioCodigo().getTime());
            if (diferenca.getTime() / 1000 < 900){
                //spring security precisa criptografar a senha
                pessoaBanco.setSenha(pessoa.getSenha());
                pessoaBanco.setCodigoRecuperacaoSenha(null);
                pessoaRepository.saveAndFlush(pessoaBanco);
                return "Senha alterada com sucesso";
            }else {
                return "Tempo expirado, solicite um novo código";
            }

        } else {
            return "Email ou código não encontrado" + pessoa.getEmail() + pessoa.getCodigoRecuperacaoSenha();
        }
       
    }


    public String getCodigoRecuperacaoSenha(Long id){


        DateFormat format = new SimpleDateFormat("ddMMyyyyHHmmssmm");
        return format.format(new Date())+id;   
    
    }
}
