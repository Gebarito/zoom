package com.zoom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.zoom.dao.UnidadeDAO;

import lombok.extern.log4j.Log4j;

@Log4j
public class CriarDBTest {
	
	private static EntityManager em;


	private static UnidadeDAO unidadeDAO = new UnidadeDAO();
	
	@PersistenceUnit
	private static EntityManagerFactory factory;
	
	@BeforeAll
	static void setup() {
	    log.info("@BeforeAll - executes once before all test methods in this class");
	    factory = Persistence.createEntityManagerFactory("zoomPU");
	    em = factory.createEntityManager();
	    unidadeDAO.setEntityManager(em);
	}

	@Test
	public void migrarTest() {
		
		log.info(unidadeDAO.buscarTodos());
	}
}
