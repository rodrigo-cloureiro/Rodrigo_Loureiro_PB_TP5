package br.com.infnet.rodrigoloureiro.pb.tp5.service;

import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoDto;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoRequestDto;
import java.util.List;
import java.util.UUID;

public interface ProdutoService {

  List<ProdutoDto> listar();

  ProdutoDto buscarPorId(UUID id);

  List<ProdutoDto> buscarPorNome(String nome);

  ProdutoDto salvar(ProdutoRequestDto request);

  ProdutoDto editar(UUID id, ProdutoRequestDto request);

  void removerPorId(UUID id);
}
