package br.com.e_commerce.produto_server.mapper;

import org.mapstruct.Mapper;

import br.com.e_commerce.produto_server.dto.ProdutoRespostaCriacaoDto;
import br.com.e_commerce.produto_server.entity.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoRespostaCriacaoMapper {
		
	ProdutoRespostaCriacaoDto toDto(Produto produto);

}
