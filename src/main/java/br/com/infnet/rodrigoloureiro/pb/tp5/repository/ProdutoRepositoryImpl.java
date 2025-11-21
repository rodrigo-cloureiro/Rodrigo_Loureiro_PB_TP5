package br.com.infnet.rodrigoloureiro.pb.tp5.repository;

import br.com.infnet.rodrigoloureiro.pb.tp5.mock.MockProduto;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.Produto;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoNulo;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {

  private static final Produto PRODUTO_NULO = new ProdutoNulo();
  private final HashMap<UUID, Produto> produtos;

  public ProdutoRepositoryImpl() {
    this.produtos = MockProduto.gerarMockProdutos();
  }

  @Override
  public List<Produto> listar() {
    return List.copyOf(produtos.values()
        .stream()
        .sorted(Comparator.comparing(Produto::getNome))
        .toList()
    );
  }

  @Override
  public Produto buscarPorId(UUID id) {
    return Optional.ofNullable(produtos.get(id))
        .orElse(PRODUTO_NULO);
  }

  @Override
  public List<Produto> buscarPorNome(String nome) {
    return produtos.values()
        .stream()
        .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
        .toList();
  }

  @Override
  public Produto salvar(Produto produto) {
    produtos.put(produto.getId(), produto);
    return produto;
  }

  @Override
  public Produto editar(UUID id, Produto produto) {
    produtos.put(id, produto);
    return produto;
  }

  @Override
  public void removerPorId(UUID id) {
    produtos.remove(id);
  }
}
