package academy.wakanda.wakacop.sessaovotacao.application.api;

import academy.wakanda.wakacop.sessaovotacao.domain.SessaoVotacao;
import lombok.Value;

import java.util.UUID;

@Value
public class
SessaoAberturaResponse {
    private UUID id;

    public SessaoAberturaResponse(SessaoVotacao sessaoVotacao) {
        this.id = sessaoVotacao.getId();
    }
}