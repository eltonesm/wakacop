package academy.wakanda.wakacop.sessaovotacao.domain;

import academy.wakanda.wakacop.associado.application.service.AssociadoService;
import academy.wakanda.wakacop.pauta.domain.Pauta;
import academy.wakanda.wakacop.sessaovotacao.application.api.ResultadoSessaoResponse;
import academy.wakanda.wakacop.sessaovotacao.application.api.SessaoAberturaRequest;
import academy.wakanda.wakacop.sessaovotacao.application.api.VotoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessaoVotacaoTest {

    @Mock
    private AssociadoService associadoService;

    @Mock
    private PublicadorResultadoSessao publicadorResultadoSessao;

    private Pauta pauta;
    private SessaoAberturaRequest sessaoAberturaRequest;
    private SessaoVotacao sessaoVotacao;

    @BeforeEach
    void setUp() {
        pauta = Pauta.builder()
                .id(UUID.randomUUID())
                .titulo("Pauta Teste")
                .descricao("Descrição Teste")
                .idAssociadoAutor(UUID.randomUUID())
                .dataCriacao(LocalDateTime.now())
                .build();

        sessaoAberturaRequest = new SessaoAberturaRequest(pauta.getId(), 5);
        sessaoVotacao = new SessaoVotacao(sessaoAberturaRequest, pauta);
    }

    @Test
    void deveReceberVotoComSucesso() {
        // Dados de entrada
        VotoRequest votoRequest = new VotoRequest("12345678901", OpcaoVoto.SIM);

        // Mock da validação do associado
        doNothing().when(associadoService).validaAssociadoAptoVoto(votoRequest.getCpfAssociado());

        // Executa a ação
        VotoPauta voto = sessaoVotacao.recebeVoto(votoRequest, associadoService, publicadorResultadoSessao);

        // Verificações
        assertEquals(votoRequest.getCpfAssociado(), voto.getCpfAssociado());
        assertEquals(OpcaoVoto.SIM, voto.getOpcaoVoto());
        verify(associadoService, times(1)).validaAssociadoAptoVoto(votoRequest.getCpfAssociado());
    }

    @Test
    void deveLancarErroQuandoAssociadoJaVotou() {
        // Dados de entrada
        VotoRequest votoRequest = new VotoRequest("12345678901", OpcaoVoto.SIM);

        // Mock para o voto duplicado
        sessaoVotacao.getVotos().put(votoRequest.getCpfAssociado(), new VotoPauta(sessaoVotacao, votoRequest));

        // Execução e Verificação
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                sessaoVotacao.recebeVoto(votoRequest, associadoService, publicadorResultadoSessao));
        assertEquals("Associado Já Votou nessa Sessão!", exception.getMessage());
    }

    @Test
    void deveFecharSessaoComSucessoQuandoTempoEncerrado() {
        // Configuração para simular sessão já encerrada
        sessaoVotacao = SessaoVotacao.builder()
                .id(UUID.randomUUID())
                .idPauta(pauta.getId())
                .tempoDuracao(1)
                .momentoAbertura(LocalDateTime.now().minusMinutes(2))
                .momentoEncerramento(LocalDateTime.now().minusMinutes(1))
                .status(StatusSessaoVotacao.ABERTA)
                .votos(new HashMap<>())
                .build();

        // Execução
        sessaoVotacao.obtemResultado(publicadorResultadoSessao);

        // Verificações
        assertEquals(StatusSessaoVotacao.FECHADA, sessaoVotacao.getStatus());
        verify(publicadorResultadoSessao, times(1)).publica(any(ResultadoSessaoResponse.class));
    }

    @Test
    void deveLancarErroAoTentarVotarEmSessaoFechada() {
        // Configuração de sessão fechada
        sessaoVotacao = SessaoVotacao.builder()
                .id(UUID.randomUUID())
                .idPauta(pauta.getId())
                .status(StatusSessaoVotacao.FECHADA)
                .build();

        // Cria o VotoRequest
        VotoRequest votoRequest = new VotoRequest("12345678901", OpcaoVoto.SIM);

        // Execução e Verificação
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                sessaoVotacao.recebeVoto(votoRequest, associadoService, publicadorResultadoSessao));
        assertEquals("A Sessão está fechada!", exception.getMessage());
    }

    @Test
    void deveObterTotalVotosSimENao() {
        // Adiciona votos simulados
        sessaoVotacao.getVotos().put("12345678901", new VotoPauta(sessaoVotacao, new VotoRequest("12345678901", OpcaoVoto.SIM)));
        sessaoVotacao.getVotos().put("12345678902", new VotoPauta(sessaoVotacao, new VotoRequest("12345678902", OpcaoVoto.NAO)));
        sessaoVotacao.getVotos().put("12345678903", new VotoPauta(sessaoVotacao, new VotoRequest("12345678903", OpcaoVoto.SIM)));

        // Verificações
        assertEquals(3, sessaoVotacao.getTotalVotos());
        assertEquals(2, sessaoVotacao.getTotalSim());
        assertEquals(1, sessaoVotacao.getTotalNao());
    }
}
