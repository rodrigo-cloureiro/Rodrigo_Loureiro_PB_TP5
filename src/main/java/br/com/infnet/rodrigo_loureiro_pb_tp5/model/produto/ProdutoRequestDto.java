package br.com.infnet.rodrigo_loureiro_pb_tp5.model.produto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ProdutoRequestDto {

  private String nome;
  private String descricao;
  private BigDecimal preco;
  private int quantidade;
}
