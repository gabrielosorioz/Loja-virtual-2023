package com.gabrielosorioz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielosorioz.backend.entity.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
    
    Pessoa findByEmail(String email);
    
    Pessoa findByEmailAndCodigoRecuperacaoSenha(String email,String codigoRecuperacaoSenha);

}
