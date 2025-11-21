package br.com.infnet.rodrigoloureiro.pb.tp5.service;

import br.com.infnet.rodrigoloureiro.pb.tp5.exception.ProdutoNaoEncontradoException;
import br.com.infnet.rodrigoloureiro.pb.tp5.mapper.ProdutoMapper;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.Produto;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoDto;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoRequestDto;
import br.com.infnet.rodrigoloureiro.pb.tp5.repository.ProdutoRepository;
import br.com.infnet.rodrigoloureiro.pb.tp5.validation.ProdutoValidator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

  private final ProdutoRepository produtoRepository;
  private final ProdutoMapper produtoMapper;

  @Override
  public List<ProdutoDto> listar() {
    return produtoRepository.listar()
        .stream()
        .map(produtoMapper::paraProdutoDto)
        .toList();
  }

  @Override
  public ProdutoDto buscarPorId(UUID id) {
    Produto produto = produtoRepository.buscarPorId(id);
    if (produto.isNulo()) {
      throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado!");
    }

    return produtoMapper.paraProdutoDto(produto);
  }

  @Override
  public List<ProdutoDto> buscarPorNome(String nome) {
    ProdutoValidator.validarTexto(nome, "nome");

    List<Produto> produtos = produtoRepository.buscarPorNome(nome);
    if (produtos.isEmpty()) {
      throw new ProdutoNaoEncontradoException("Produto com nome '" + nome + "' não encontrado!");
    }

    return produtos.stream()
        .map(produtoMapper::paraProdutoDto)
        .toList();
  }

  @Override
  public ProdutoDto salvar(ProdutoRequestDto request) {
    UUID novoId = UUID.randomUUID();
    Produto novoProduto = produtoMapper.paraProduto(novoId, request);
    Produto produtoSalvo = produtoRepository.salvar(novoProduto);

    return produtoMapper.paraProdutoDto(produtoSalvo);
  }

  @Override
  public ProdutoDto editar(UUID id, ProdutoRequestDto request) {
    buscarPorId(id);
    Produto produtoEditado = produtoMapper.paraProduto(id, request);
    produtoRepository.editar(id, produtoEditado);
    return produtoMapper.paraProdutoDto(produtoEditado);
  }

  @Override
  public void removerPorId(UUID id) {
    buscarPorId(id);
    produtoRepository.removerPorId(id);
  }
}
