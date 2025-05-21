package com.zoom.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.zoom.modelo.UnidadeRede;
import com.zoom.util.NegocioException;
import com.zoom.util.jpa.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
public class UnidadeRedeDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    @Transactional
    public UnidadeRede salvar(UnidadeRede unidadeRede) throws NegocioException {
        try {
            log.info("Salvando unidadeRede: " + unidadeRede.getNome());
            return manager.merge(unidadeRede);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NegocioException("Não foi possível executar a operação.");
        }
    }

    @Transactional
    public void excluir(UnidadeRede unidadeRede) throws NegocioException {
        unidadeRede = buscarPeloCodigo(unidadeRede.getCodigo());
        try {
            manager.remove(unidadeRede);
            manager.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new NegocioException("Não foi possível executar a operação.");
        }
    }

    public UnidadeRede buscarPeloCodigo(Long codigo) {
        return manager.find(UnidadeRede.class, codigo);
    }

    @SuppressWarnings("unchecked")
    public List<UnidadeRede> buscarTodos() {
        return manager.createQuery("SELECT u FROM UnidadeRede u").getResultList();
    }
}
