package com.gabrielosorioz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielosorioz.backend.entity.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade,Long> {
    
}
