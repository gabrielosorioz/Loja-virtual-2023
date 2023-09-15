package com.gabrielosorioz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielosorioz.backend.entity.Estado;

public interface EstadoRepository extends JpaRepository<Estado,Long> {
    
}
