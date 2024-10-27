package academy.wakanda.wakacop.associado.application.service;

import academy.wakanda.wakacop.associado.infra.client.ConsultaCPFResponse;
import academy.wakanda.wakacop.associado.infra.client.SerproClientFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AssociadoApplicationService implements AssociadoService {

    private final SerproClientFeign serproClientFeign;

    @Override
    public void validaAssociadoAptoVoto(String cpfAssociado) {
        log.debug("[Start] AssociadoApplicationService - validaAssociadoAptoVoto");
        ConsultaCPFResponse consultaCPFResponse = serproClientFeign.consutaCPF(TOKEN, cpfAssociado);
        valida(consultaCPFResponse);
        log.debug("[Finish] AssociadoApplicationService - validaAssociadoAptoVoto");
    }

    private void valida(ConsultaCPFResponse consultaCPFResponse) {
        if (consultaCPFResponse.isInvalid()) {
            throw new RuntimeException("CPF associado Inválido!");
        }
    }

    private static final String TOKEN = "Bearer 06aef429-a981-3ec5-a1f8-71d38d86481e";
}