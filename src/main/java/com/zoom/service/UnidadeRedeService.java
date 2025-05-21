package com.zoom.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.zoom.dao.UnidadeRedeDAO;
import com.zoom.modelo.UnidadeRede;
import com.zoom.util.NegocioException;

import lombok.extern.log4j.Log4j;

@Log4j
public class UnidadeRedeService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UnidadeRedeDAO unidadeRedeDAO;

    public UnidadeRede salvar(UnidadeRede unidadeRede) throws NegocioException {
        log.info("Unidade de Rede nome = " + unidadeRede.getNome());
        return this.unidadeRedeDAO.salvar(unidadeRede);
    }

    public UnidadeRede buscarPeloCodigo(long codigo) {
        return unidadeRedeDAO.buscarPeloCodigo(codigo);
    }

    public List<UnidadeRede> buscarTodos() {
        return unidadeRedeDAO.buscarTodos();
    }

    public void excluir(UnidadeRede unidadeRede) throws NegocioException {
        unidadeRedeDAO.excluir(unidadeRede);
    }

    public List<UnidadeRede> buscarUnidades() {
        log.info("Buscando unidades de rede...");
        return unidadeRedeDAO.buscarTodos();
    }
}
