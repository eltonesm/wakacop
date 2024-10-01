package academy.wakanda.wakacop.pauta.application.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pauta")
public interface PautaAPI {
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    PautaCadastradaResponse cadastraPauta(@Valid @RequestBody NovaPautaRequest novaPauta);
}
