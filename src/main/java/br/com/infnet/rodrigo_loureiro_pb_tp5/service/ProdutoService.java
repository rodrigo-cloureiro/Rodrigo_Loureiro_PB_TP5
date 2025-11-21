package br.com.infnet.rodrigo_loureiro_pb_tp5.service;

import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoDto;
import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoRequestDto;
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
