package br.com.infnet.rodrigo_loureiro_pb_tp5.mapper;

import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.Produto;
import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoDto;
import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoRequestDto;
import java.util.UUID;

public interface ProdutoMapper {

  ProdutoDto paraProdutoDto(Produto produto);

  Produto paraProduto(UUID id, ProdutoRequestDto produtoRequestDto);
}
