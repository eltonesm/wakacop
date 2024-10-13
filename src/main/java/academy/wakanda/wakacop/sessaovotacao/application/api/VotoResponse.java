package academy.wakanda.wakacop.sessaovotacao.application.api;

import academy.wakanda.wakacop.sessaovotacao.domain.VotoPauta;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class VotoResponse {
    private UUID id;
    private UUID idSessao;
    private String cpfAssociado;
    private LocalDateTime momentoVoto;

    public VotoResponse(VotoPauta voto) {
        this.id = voto.getId();
        this.idSessao = voto.getIdSessao();
        this.cpfAssociado = voto.getCpfAssociado();
        this.momentoVoto = LocalDateTime.now();
    }
}