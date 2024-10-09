package academy.wakanda.wakacop.sessaovotacao.application.api;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.Optional;
import java.util.UUID;

@Value
public class SessaoAberturaRequest {
    @NotNull
    private UUID idPauta;
    private Integer tempoDuracao;

    public Optional<Integer> getTempoDuracao(){
        return Optional.ofNullable(this.tempoDuracao);
    }
}
