package com.zoom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zoom.dao.AtendimentoDAO;
import com.zoom.modelo.Atendimento;
import com.zoom.modelo.Unidade;
import com.zoom.modelo.Usuario;
import com.zoom.service.AtendimentoService;
import com.zoom.service.UsuarioService;
import com.zoom.util.NegocioException;

public class AtendimentoServiceTest {

    @Mock
    private AtendimentoDAO atendimentoDAO;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AtendimentoService atendimentoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deveSalvarAtendimentoComSucesso() throws NegocioException {
        // Arrange (preparação)
        Atendimento atendimento = new Atendimento();
        atendimento.setDescricao("Teste de atendimento");

        when(atendimentoDAO.salvar(atendimento)).thenReturn(atendimento);

        // Act (ação)
        Atendimento resultado = atendimentoService.salvar(atendimento);

        // Assert (verificação)
        assertNotNull(resultado);
        assertEquals("Teste de atendimento", resultado.getDescricao());
        verify(atendimentoDAO, times(1)).salvar(atendimento); // Verifica se o método foi chamado
    }
}
