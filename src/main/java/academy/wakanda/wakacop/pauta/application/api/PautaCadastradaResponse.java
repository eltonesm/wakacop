package academy.wakanda.wakacop.pauta.application.api;

import academy.wakanda.wakacop.pauta.domain.Pauta;
import lombok.Value;

import java.util.UUID;

@Value
public class PautaCadastradaResponse {
    private UUID idPauta;

    public PautaCadastradaResponse(Pauta pauta) {
        this.idPauta = pauta.getId();
    }
}