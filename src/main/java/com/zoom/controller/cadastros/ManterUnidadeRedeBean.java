package com.zoom.controller.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.zoom.controller.LoginBean;
import com.zoom.modelo.UnidadeRede;
import com.zoom.service.UnidadeRedeService;
import com.zoom.util.MessageUtil;
import com.zoom.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class ManterUnidadeRedeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private UnidadeRede unidadeRede;
    private List<UnidadeRede> unidades = new ArrayList<>();

    @Inject
    private UnidadeRedeService unidadeRedeService;

    @Inject
    private LoginBean usuarioLogado;

    @PostConstruct
    public void inicializar() {
        log.info("Usuário logado: " + usuarioLogado.getUsuario());

        this.unidades = unidadeRedeService.buscarTodos();
        this.limpar();
    }

    public void salvar() {
        try {
            log.info("Unidade Rede - código: " + unidadeRede.getCodigo());
            log.info("Unidade Rede - nome: " + unidadeRede.getNome());
            log.info("Unidade Rede - telefone: " + unidadeRede.getTelefone());

            UnidadeRede unid = this.unidadeRedeService.salvar(unidadeRede);
            this.unidades = unidadeRedeService.buscarTodos();

            MessageUtil.sucesso("Unidade de rede salva com sucesso!");
            log.info("Unidade Rede salva - código: " + unid.getCodigo());
            log.info("Unidade Rede salva - nome: " + unid.getNome());

        } catch (NegocioException e) {
            e.printStackTrace();
            MessageUtil.erro(e.getMessage());
        }

        this.limpar();
    }

    public void limpar() {
        this.unidadeRede = new UnidadeRede();
    }

    public void carregarUnidades() {
        unidades = unidadeRedeService.buscarTodos();
    }

    public void excluir() {
        try {
            unidadeRedeService.excluir(unidadeRede);
            this.unidades = unidadeRedeService.buscarTodos();
            MessageUtil.sucesso("Unidade de rede excluída com sucesso.");
            log.info("Unidade Rede excluída: " + unidadeRede.getNome());

        } catch (NegocioException e) {
            e.printStackTrace();
            MessageUtil.erro(e.getMessage());
        }
    }
}
