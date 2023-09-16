package com.gabrielosorioz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielosorioz.backend.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long>{
    
}
