package com.gabrielosorioz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielosorioz.backend.entity.Permissao;
import java.util.List;


@Repository
public interface PermissaoRepository extends JpaRepository<Permissao,Long>{
    
    List<Permissao> findByNome(String nome);
}
