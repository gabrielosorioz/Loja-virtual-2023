package com.gabrielosorioz.backend.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.gabrielosorioz.backend.entity.Produto;
import com.gabrielosorioz.backend.entity.ProdutoImagens;
import com.gabrielosorioz.backend.repository.ProdutoImagensRepository;
import com.gabrielosorioz.backend.repository.ProdutoRepository;

@Service
public class ProdutoImagensService {

    @Autowired
    private ProdutoImagensRepository produtoImagensRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    public List<ProdutoImagens> buscarTodos(){
        return produtoImagensRepository.findAll();
    }

    public List<ProdutoImagens> buscarPorProduto(Long idProduto){
        List<ProdutoImagens> listaProdutoImagens = produtoImagensRepository.findByProdutoId(idProduto);

        for(ProdutoImagens produtoImagens : listaProdutoImagens){
            try(InputStream in = new FileInputStream("C:/Users/gabri/Downloads/imagens"+produtoImagens.getNome())){
              produtoImagens.setArquivo(IOUtils.toByteArray(in));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listaProdutoImagens;
    }
        
    
    public ProdutoImagens inserir(Long idProduto, MultipartFile file){
        
        Produto produto = produtoRepository.findById(idProduto).get();
        ProdutoImagens objeto = new ProdutoImagens();

		try {
			if (!file.isEmpty()) {
				
                byte[] bytes = file.getBytes();
                String nomeImagem = String.valueOf(produto.getId()) + file.getOriginalFilename();
				Path caminho = Paths.get("C:/Users/gabri/Downloads/imagens" +nomeImagem );
				Files.write(caminho, bytes);
                objeto.setNome(nomeImagem);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

        objeto.setProduto(produto);
        objeto.setDataCriacao(new Date());
        objeto = produtoImagensRepository.saveAndFlush(objeto);
        return objeto;

    }


    public ProdutoImagens alterar(ProdutoImagens produtoImagens){
        produtoImagens.setDataAtualizacao(new Date());
        return produtoImagensRepository.saveAndFlush(produtoImagens);
    }

    public void excluir (Long id){
        ProdutoImagens produtoImagens = produtoImagensRepository.findById(id).get();
        produtoImagensRepository.delete(produtoImagens);
    }

}
