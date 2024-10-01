package academy.wakanda.wakacop.pauta.application.api;

import academy.wakanda.wakacop.pauta.application.service.PautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PautaController implements PautaAPI {
    private final PautaService pautaService;

    @Override
    public PautaCadastradaResponse cadastraPauta(NovaPautaRequest novaPauta) {
        log.info("[Start]PautaController - cadastraPauta");
        PautaCadastradaResponse pautaCadastrada = pautaService.cadastraPauta(novaPauta);
        log.info("[Finish]PautaController - cadastraPauta");
        return pautaCadastrada;
    }
}