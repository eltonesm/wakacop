package academy.wakanda.wakacop.sessaovotacao.application.repository;

import academy.wakanda.wakacop.sessaovotacao.domain.SessaoVotacao;

import java.util.List;
import java.util.UUID;

public interface SessaoVotacaoRepository {
    SessaoVotacao salva(SessaoVotacao sessaoVotacao);
    SessaoVotacao buscaPorId(UUID idSessao);
    List<SessaoVotacao> buscaAbertas();
}