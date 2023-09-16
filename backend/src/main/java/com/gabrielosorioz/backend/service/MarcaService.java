package com.gabrielosorioz.backend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabrielosorioz.backend.entity.Marca;
import com.gabrielosorioz.backend.repository.MarcaRepository;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> buscarTodos(){
        return marcaRepository.findAll();
    }
    
    public Marca buscarPorId(Long id){
        return marcaRepository.findById(id).get();
    
    }

    public Marca inserir(Marca marca){
        marca.setDataCriacao(new Date());
        Marca novaMarca = marcaRepository.saveAndFlush(marca);
        return novaMarca;
        
    }

    public Marca alterar(Marca marca){
        marca.setDataAtualizacao(new Date());
        return marcaRepository.saveAndFlush(marca);
    }

    public void excluir(Long id){
        Marca marca = marcaRepository.findById(id).get();
        marcaRepository.delete(marca);

    }
    
}
