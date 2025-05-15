package com.zoom.dao;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.zoom.modelo.Atendimento;
import com.zoom.modelo.Unidade;
import com.zoom.modelo.enums.StatusAtendimento;
import com.zoom.util.NegocioException;
import com.zoom.util.jpa.Transactional;

import lombok.extern.log4j.Log4j;

/**
 * @author Timpone
 *
 */
@Log4j

public class AtendimentoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;


	@Transactional
	public Atendimento merge(Atendimento Atendimento) throws NegocioException {
		try {
			return manager.merge(Atendimento);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Error e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		}
	}

	@Transactional
	public void excluir(Atendimento Atendimento) throws NegocioException {
		Atendimento = buscarPeloCodigo(Atendimento.getCodigo());
		try {
			manager.remove(Atendimento);
			manager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Error e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		}
	}



	/*
	 * Buscas
	 */
	
	public Atendimento buscarPeloCodigo(Long codigo) {
		return manager.find(Atendimento.class, codigo);
	}

	@SuppressWarnings("unchecked")
	public List<Atendimento> buscarTodos(Unidade unidade) throws ParseException {

		LocalDateTime data = LocalDateTime.now();
		data = data.minusYears(1);
		log.info("Data inicio da busca: " + data);
		return manager.createQuery("select a from Atendimento a where a.unidade = :unidade "
				+ "and a.dataAgendamento > :data")
				.setParameter("unidade", unidade)
				.setParameter("data", data)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Atendimento> buscarTodosAgendados(Unidade unidade) throws ParseException {

		LocalDateTime data = LocalDateTime.now();
		data = data.minusYears(1);
		log.info("Data inicio da busca: " + data);
		return manager.createQuery("select a from Atendimento a where a.unidade = :unidade "
				+ "and a.statusAtendimento = :statusAtendimento "
				+ "and a.dataAgendamento > :data")
				.setParameter("unidade", unidade)
				.setParameter("statusAtendimento", StatusAtendimento.AGENDADO)
				.setParameter("data", data)
				.getResultList();
	}
}