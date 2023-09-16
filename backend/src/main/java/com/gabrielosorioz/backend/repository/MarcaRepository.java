package com.gabrielosorioz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielosorioz.backend.entity.Marca;

public interface MarcaRepository extends JpaRepository<Marca,Long>{
    
}
