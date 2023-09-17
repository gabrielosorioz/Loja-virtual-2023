package com.gabrielosorioz.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielosorioz.backend.entity.ProdutoImagens;

@Repository
public interface ProdutoImagensRepository extends JpaRepository<ProdutoImagens,Long> {
    
    public List<ProdutoImagens> findByProdutoId(Long id);
    
}
