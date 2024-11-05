package academy.wakanda.wakacop.sessaovotacao.infra;

import academy.wakanda.wakacop.handler.APIException;
import academy.wakanda.wakacop.sessaovotacao.application.repository.SessaoVotacaoRepository;
import academy.wakanda.wakacop.sessaovotacao.domain.SessaoVotacao;
import academy.wakanda.wakacop.sessaovotacao.domain.StatusSessaoVotacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Log4j2
@RequiredArgsConstructor
public class SessaoVotacaoInfraRepository implements SessaoVotacaoRepository {
    private final SessaoVotacaoSpringDataJPARepository sessaoVotacaoSpringDataJPARepository;

    @Override
    public SessaoVotacao salva(SessaoVotacao sessaoVotacao) {
        log.info("[Start]SessaoVotacaoInfraRepository - salva");
        sessaoVotacaoSpringDataJPARepository.save(sessaoVotacao);
        log.info("[Finish]SessaoVotacaoInfraRepository - salva");
        return sessaoVotacao;
    }

    @Override
    public SessaoVotacao buscaPorId(UUID idSessao) {
        log.info("[Start]SessaoVotacaoInfraRepository - buscaPorId");
        SessaoVotacao sessao = sessaoVotacaoSpringDataJPARepository.findById(idSessao)
                .orElseThrow(() -> APIException.build(HttpStatus.NOT_FOUND, "Sessão não encontrada!"));
        log.info("[Finish]SessaoVotacaoInfraRepository - buscaPorId");
        return sessao;
    }

    @Override
    public List<SessaoVotacao> buscaAbertas() {
        log.info("[Start]SessaoVotacaoInfraRepository - buscaAbertas");
        List<SessaoVotacao> sessoes = sessaoVotacaoSpringDataJPARepository.findByStatus(StatusSessaoVotacao.ABERTA);
        log.info("[Finish]SessaoVotacaoInfraRepository - buscaAbertas");
        return sessoes;
    }
}