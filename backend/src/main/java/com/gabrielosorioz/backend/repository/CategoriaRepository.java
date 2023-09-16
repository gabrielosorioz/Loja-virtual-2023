package com.gabrielosorioz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielosorioz.backend.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long>{
    
}
