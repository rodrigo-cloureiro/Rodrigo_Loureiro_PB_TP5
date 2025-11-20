package br.com.infnet.rodrigo_loureiro_pb_tp5.mapper;

import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.Produto;
import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoDTO;
import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoRequestDTO;

import java.util.UUID;

public interface ProdutoMapper {
    ProdutoDTO paraProdutoDTO(Produto produto);

    Produto paraProduto(UUID id, ProdutoRequestDTO produtoRequestDTO);
}
