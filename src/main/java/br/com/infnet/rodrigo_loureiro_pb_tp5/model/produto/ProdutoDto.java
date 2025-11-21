package br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ProdutoDto {

  private UUID id;
  private String nome;
  private String descricao;
  private BigDecimal preco;
  private int quantidade;
}
