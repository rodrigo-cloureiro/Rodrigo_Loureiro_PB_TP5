package br.com.infnet.rodrigo_loureiro_pb_tp5.mapper;

import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.Produto;
import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoDTO;
import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoReal;
import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoRequestDTO;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapperImpl implements ProdutoMapper {

  @Override
  public ProdutoDTO paraProdutoDTO(Produto produto) {
    return new ProdutoDTO(
        produto.getId(),
        produto.getNome(),
        produto.getDescricao(),
        produto.getPreco(),
        produto.getQuantidade()
    );
  }

  @Override
  public Produto paraProduto(UUID id, ProdutoRequestDTO produtoRequestDTO) {
    return new ProdutoReal.Builder()
        .id(id)
        .nome(produtoRequestDTO.getNome())
        .descricao(produtoRequestDTO.getDescricao())
        .preco(produtoRequestDTO.getPreco())
        .quantidade(produtoRequestDTO.getQuantidade())
        .build();
  }
}
