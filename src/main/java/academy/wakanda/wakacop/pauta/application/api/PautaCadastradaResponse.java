package academy.wakanda.wakacop.pauta.application.api;

import academy.wakanda.wakacop.pauta.domain.Pauta;
import lombok.Value;

import java.util.UUID;

@Value
public class PautaCadastradaResponse {
    private UUID id;

    public PautaCadastradaResponse(Pauta pauta) {
        this.id = pauta.getId();
    }
}