package br.com.infnet.rodrigoloureiro.pb.tp5.mapper;

import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.Produto;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoDto;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoReal;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoRequestDto;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapperImpl implements ProdutoMapper {

  @Override
  public ProdutoDto paraProdutoDto(Produto produto) {
    return new ProdutoDto(
        produto.getId(),
        produto.getNome(),
        produto.getDescricao(),
        produto.getPreco(),
        produto.getQuantidade()
    );
  }

  @Override
  public Produto paraProduto(UUID id, ProdutoRequestDto produtoRequestDto) {
    return new ProdutoReal.Builder()
        .id(id)
        .nome(produtoRequestDto.getNome())
        .descricao(produtoRequestDto.getDescricao())
        .preco(produtoRequestDto.getPreco())
        .quantidade(produtoRequestDto.getQuantidade())
        .build();
  }
}
