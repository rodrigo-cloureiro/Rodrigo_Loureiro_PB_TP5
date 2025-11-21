package br.com.infnet.rodrigoloureiro.pb.tp5.model.produto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProdutoResponsePayload {

  private String mensagem;
  private List<ProdutoDto> produtos;
  @Builder.Default
  private LocalDateTime dataHora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
}
