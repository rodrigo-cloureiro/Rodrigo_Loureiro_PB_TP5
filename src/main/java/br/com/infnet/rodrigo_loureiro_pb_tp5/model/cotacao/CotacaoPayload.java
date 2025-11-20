package br.com.infnet.rodrigo_loureiro_pb_tp5.model.cotacao;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
public class CotacaoPayload {
    //    @JsonProperty("BRLUSD")
    @JsonAlias({"BRLUSD", "BRLEUR"})
    private Cotacao cotacao;
}
