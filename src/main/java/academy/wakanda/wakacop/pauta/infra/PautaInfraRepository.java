package academy.wakanda.wakacop.pauta.infra;

import academy.wakanda.wakacop.handler.APIException;
import academy.wakanda.wakacop.pauta.application.repository.PautaRepository;
import academy.wakanda.wakacop.pauta.domain.Pauta;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Log4j2
@RequiredArgsConstructor
public class PautaInfraRepository implements PautaRepository {
    private final PautaSpringDataJPARepository pautaSpringDataJPARepository;

    @Override
    public Pauta salva(Pauta pauta) {
        log.info("[Start]PautaInfraRepository - cadastraPauta");
        pautaSpringDataJPARepository.save(pauta);
        log.info("[Finish]PautaInfraRepository - cadastraPauta");
        return pauta;
    }

    @Override
    public Pauta buscaPautaPorId(UUID idPauta) {
        log.info("[Start]PautaInfraRepository - buscaPautaPorId");
        Pauta pautaPorid = pautaSpringDataJPARepository.findById(idPauta)
                        .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Pauta não encontrada!"));
        log.info("[Finish]PautaInfraRepository - buscaPautaPorId");
        return pautaPorid;
    }
}
