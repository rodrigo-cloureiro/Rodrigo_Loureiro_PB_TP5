package br.com.infnet.rodrigoloureiro.pb.tp5.repository;

import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.Produto;
import java.util.List;
import java.util.UUID;

public interface ProdutoRepository {

  List<Produto> listar();

  Produto buscarPorId(UUID id);

  List<Produto> buscarPorNome(String nome);

  Produto salvar(Produto produto);

  Produto editar(UUID id, Produto produto);

  void removerPorId(UUID id);
}
