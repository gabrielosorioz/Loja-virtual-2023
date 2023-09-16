package com.gabrielosorioz.backend.service;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gabrielosorioz.backend.entity.Estado;
import com.gabrielosorioz.backend.repository.EstadoRepository;

@Service
public class EstadoService {


    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> buscarTodos() {
        return estadoRepository.findAll();
    }

    public Estado buscarPorId(Long id) {
        return estadoRepository.findById(id).get();
    }


    public Estado alterar(Estado estado){        
        estado.setDataAtualizacao(new Date());
        return estadoRepository.saveAndFlush(estado);
    }
    
    public Estado inserir(Estado estado) {
        estado.setDataCriacao(new Date());
        Estado estadoNovo = estadoRepository.saveAndFlush(estado);
        return estadoNovo;
    }

    public void excluir(Long id){
        Estado estado = estadoRepository.findById(id).get();
        estadoRepository.delete(estado);
    } 



}
