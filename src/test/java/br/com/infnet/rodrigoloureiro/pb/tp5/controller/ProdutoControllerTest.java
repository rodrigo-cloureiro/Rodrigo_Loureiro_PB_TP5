package br.com.infnet.rodrigoloureiro.pb.tp5.controller;

import br.com.infnet.rodrigoloureiro.pb.tp5.exception.EntradaInvalidaException;
import br.com.infnet.rodrigoloureiro.pb.tp5.exception.ProdutoNaoEncontradoException;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.*;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoDto;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoRequestDto;
import br.com.infnet.rodrigoloureiro.pb.tp5.model.produto.ProdutoResponsePayload;
import br.com.infnet.rodrigoloureiro.pb.tp5.service.ProdutoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProdutoControllerTest {
    @Mock
    private ProdutoService produtoServiceMock;
    @InjectMocks
    private ProdutoController produtoController;

    private static final String nome = "Mouse";
    private static final String descricao = "Antigo";
    private static final BigDecimal preco = BigDecimal.TEN;
    private static final int quantidade = 10;

    private UUID idInexistente;
    private ProdutoDto produtoDtoPadrao;
    private ProdutoRequestDto produtoRequestDtoPadrao;
    private UUID idPadrao;

    @BeforeEach
    public void setUp() {
        this.idInexistente = UUID.randomUUID();
        this.idPadrao = UUID.randomUUID();
        this.produtoDtoPadrao = new ProdutoDto(idPadrao, nome, descricao, preco, quantidade);
        this.produtoRequestDtoPadrao = new ProdutoRequestDto(nome, descricao, preco, quantidade);
    }

    @AfterEach
    public void tearDown() {
        reset(produtoServiceMock);
    }

    @Test
    public void requisicoesListarProdutosDeveSobrecarregarOSistema() throws InterruptedException, ExecutionException {
        int totalRequests = 30;
        List<Future<Integer>> futures;
        try (ExecutorService executor = Executors.newFixedThreadPool(totalRequests)) {

            when(produtoServiceMock.listar()).thenAnswer(invocation -> {
                Thread.sleep(200);
                return Collections.emptyList();
            });

            List<Callable<Integer>> tasks = new ArrayList<>();
            for (int i = 0; i < totalRequests; i++) {
                tasks.add(() -> {
                    ResponseEntity<ProdutoResponsePayload> response = produtoController.listar();
                    return response.getStatusCode().value();
                });
            }

            futures = executor.invokeAll(tasks);
            executor.shutdown();
        }

        int count429 = 0;
        for (Future<Integer> future : futures) {
            if (future.get() == 429) count429++;
        }

        assertTrue(count429 > 0);
        verify(produtoServiceMock, atLeast(1)).listar();
    }

    @Test
    public void deveRetornarProdutosComSucesso() {
        List<ProdutoDto> produtos = List.of(produtoDtoPadrao);

        when(produtoServiceMock.listar()).thenReturn(produtos);

        ResponseEntity<ProdutoResponsePayload> response = produtoController.listar();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(produtos.size(), response.getBody().getProdutos().size());
        assertEquals("200 OK", response.getBody().getMensagem());
        verify(produtoServiceMock, times(1)).listar();
    }

    @Test
    public void deveRetornarProdutoPorIdComSucesso() {
        when(produtoServiceMock.buscarPorId(idPadrao)).thenReturn(produtoDtoPadrao);

        ResponseEntity<ProdutoResponsePayload> response = produtoController.buscarPorId(idPadrao);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getProdutos().size());
        verify(produtoServiceMock, times(1)).buscarPorId(idPadrao);
    }

    @Test
    public void deveRetornarRespostaExcecaoAoBuscarProdutoPorIdInexistente() {
        when(produtoServiceMock.buscarPorId(idInexistente)).thenThrow(
                new ProdutoNaoEncontradoException("Produto com ID " + idInexistente + " não encontrado!")
        );

        ResponseEntity<ProdutoResponsePayload> response = produtoController.buscarPorId(idInexistente);

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getProdutos());
        assertEquals(
                String.format("Produto com ID %s não encontrado!", idInexistente),
                response.getBody().getMensagem()
        );
        verify(produtoServiceMock, times(1)).buscarPorId(idInexistente);
    }

    @Test
    public void deveRetornarProdutosPorNomeComSucesso() {
        String busca = "Mo";
        List<ProdutoDto> produtoDtos = List.of(produtoDtoPadrao);

        when(produtoServiceMock.buscarPorNome(busca)).thenReturn(produtoDtos);

        ResponseEntity<ProdutoResponsePayload> response = produtoController.buscarPorNome(busca);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getProdutos().size());
        assertTrue(response.getBody().getProdutos().getFirst().getNome().contains(busca));
        verify(produtoServiceMock, times(1)).buscarPorNome(busca);
    }

    @Test
    public void deveRetornarRespostaExcecaoAoBuscarProdutoPorNomeInexistente() {
        String busca = "Rodrigo";

        when(produtoServiceMock.buscarPorNome(busca)).thenThrow(
                new ProdutoNaoEncontradoException("Produto com nome '" + busca + "' não encontrado!")
        );

        ResponseEntity<ProdutoResponsePayload> response = produtoController.buscarPorNome(busca);

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Produto com nome '" + busca + "' não encontrado!", response.getBody().getMensagem());
        verify(produtoServiceMock, times(1)).buscarPorNome(busca);
    }

    @Test
    public void deveCriarProdutoComSucesso() {
        when(produtoServiceMock.salvar(produtoRequestDtoPadrao)).thenReturn(produtoDtoPadrao);

        ResponseEntity<ProdutoResponsePayload> response = produtoController.salvar(
            produtoRequestDtoPadrao);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getProdutos().size());
        assertEquals("201 CREATED", response.getBody().getMensagem());
        verify(produtoServiceMock, times(1)).salvar(produtoRequestDtoPadrao);
    }

    @ParameterizedTest
    @CsvSource({
            "Mouse, Gamer RGB, -5.00, 10",
            "Mouse, Gamer RGB, 599.90, -2",
            "Mouse, Gamer RGB, 599.90, 2",
    })
    public void deveRetornarRespostaExcecaoQuandoSalvarProdutoInvalido(
            String nome, String descricao, BigDecimal preco, int quantidade
    ) {
        ProdutoRequestDto produtoRequestDTO = new ProdutoRequestDto(nome, descricao, preco, quantidade);
        String mensagem = "";

        if (quantidade < 0)
            mensagem = "A quantidade não pode ser negativa ou nula.";
        if (preco.compareTo(BigDecimal.ZERO) < 0)
            mensagem = "O preço não pode ser negativo ou nulo.";

        when(produtoServiceMock.salvar(produtoRequestDTO)).thenThrow(
                new EntradaInvalidaException(mensagem)
        );

        ResponseEntity<ProdutoResponsePayload> response = produtoController.salvar(produtoRequestDTO);

        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(mensagem, response.getBody().getMensagem());
        verify(produtoServiceMock, times(1)).salvar(produtoRequestDTO);
    }

    @Test
    public void deveEditarProdutoComSucesso() {
        ProdutoRequestDto produtoRequestDTO = new ProdutoRequestDto(nome, descricao, preco, 5);
        ProdutoDto produtoDTO = new ProdutoDto(idPadrao, nome, descricao, preco, 5);

        when(produtoServiceMock.editar(idPadrao, produtoRequestDTO)).thenReturn(produtoDTO);

        ResponseEntity<ProdutoResponsePayload> response = produtoController.editar(idPadrao, produtoRequestDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(produtoDTO, response.getBody().getProdutos().getFirst());
        verify(produtoServiceMock, times(1)).editar(idPadrao, produtoRequestDTO);
    }

    @Test
    public void deveRetornarRespostaExcecaoQuandoEditarProdutoInexistente() {
        ProdutoRequestDto produtoRequestDtoMock = mock(ProdutoRequestDto.class);

        when(produtoServiceMock.editar(idInexistente, produtoRequestDtoMock)).thenThrow(
                new ProdutoNaoEncontradoException("Produto com ID " + idInexistente + " não encontrado!")
        );

        ResponseEntity<ProdutoResponsePayload> response = produtoController.editar(idInexistente,
            produtoRequestDtoMock);

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getProdutos());
        assertEquals(
                String.format("Produto com ID %s não encontrado!", idInexistente),
                response.getBody().getMensagem()
        );
        verify(produtoServiceMock, times(1)).editar(idInexistente, produtoRequestDtoMock);
    }

    @Test
    public void deveRetornarRespostaExcecaoQuandoEditarProdutoRequestInvalido() {
        ProdutoRequestDto produtoRequestDTO = new ProdutoRequestDto(
                nome, descricao, BigDecimal.valueOf(-5), quantidade
        );

        when(produtoServiceMock.editar(idInexistente, produtoRequestDTO)).thenThrow(
                new EntradaInvalidaException("O preço não pode ser negativo ou nulo.")
        );

        ResponseEntity<ProdutoResponsePayload> response = produtoController.editar(idInexistente, produtoRequestDTO);

        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getProdutos());
        assertEquals("O preço não pode ser negativo ou nulo.", response.getBody().getMensagem());
        verify(produtoServiceMock, times(1)).editar(idInexistente, produtoRequestDTO);
    }

    @Test
    public void deveRemoverProdutoPorIdComSucesso() {
        doNothing().when(produtoServiceMock).removerPorId(idPadrao);

        ResponseEntity<ProdutoResponsePayload> response = produtoController.removerPorId(idPadrao);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(produtoServiceMock, times(1)).removerPorId(idPadrao);
    }

    @Test
    public void deveRetornarRespostaExcecaoAoRemoverProdutoPorIdInexistente() {

        doThrow(
                new ProdutoNaoEncontradoException("Produto com ID " + idInexistente + " não encontrado!")
        ).when(produtoServiceMock).removerPorId(idInexistente);

        ResponseEntity<ProdutoResponsePayload> response = produtoController.removerPorId(idInexistente);

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Produto com ID " + idInexistente + " não encontrado!", response.getBody().getMensagem());
        verify(produtoServiceMock, times(1)).removerPorId(idInexistente);
    }
}
