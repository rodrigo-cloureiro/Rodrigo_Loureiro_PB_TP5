package br.com.infnet.rodrigo_loureiro_pb_tp4.repository;

import br.com.infnet.rodrigo_loureiro_pb_tp4.exception.ProdutoNaoEncontradoException;
import br.com.infnet.rodrigo_loureiro_pb_tp4.mock.MockProduto;
import br.com.infnet.rodrigo_loureiro_pb_tp4.model.produto.Produto;
import br.com.infnet.rodrigo_loureiro_pb_tp4.model.produto.ProdutoNulo;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {
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
                .orElse(new ProdutoNulo());
    }

    @Override
    public List<Produto> buscarPorNome(String nome) {
        List<Produto> encontrados = new ArrayList<>();

        for (Produto produto : produtos.values()) {
            if (produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(produto);
            }
        }

        return encontrados;
    }

    @Override
    public Produto salvar(Produto produto) {
        produtos.put(produto.getId(), produto);
        return produto;
    }

    @Override
    public Produto editar(UUID id, Produto produto) {
        if (buscarPorId(id).isNulo())
            throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado!");

        produtos.put(id, produto);
        return produto;
    }

    @Override
    public void removerPorId(UUID id) {
        produtos.remove(id);
    }
}
