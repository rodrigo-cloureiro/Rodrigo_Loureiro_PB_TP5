package br.com.infnet.rodrigo_loureiro_pb_tp5.controller;

import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoRequestDto;
import br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto.ProdutoResponsePayload;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/produtos")
public interface ProdutoOperacoes {

  @GetMapping("/listar")
  ResponseEntity<ProdutoResponsePayload> listar();

  @GetMapping("/listar/id/{id}")
  ResponseEntity<ProdutoResponsePayload> buscarPorId(@PathVariable UUID id);

  @GetMapping("/listar/nome/{nome}")
  ResponseEntity<ProdutoResponsePayload> buscarPorNome(@PathVariable String nome);

  @PostMapping("/salvar")
  ResponseEntity<ProdutoResponsePayload> salvar(@RequestBody ProdutoRequestDto produto);

  @PutMapping("/editar/{id}")
  ResponseEntity<ProdutoResponsePayload> editar(@PathVariable UUID id,
      @RequestBody ProdutoRequestDto produto);

  @DeleteMapping("/remover/{id}")
  ResponseEntity<ProdutoResponsePayload> removerPorId(@PathVariable UUID id);
}
