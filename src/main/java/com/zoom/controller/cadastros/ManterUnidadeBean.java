package com.zoom.controller.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.zoom.controller.LoginBean;
import com.zoom.modelo.Endereco;
import com.zoom.modelo.Unidade;
import com.zoom.modelo.enums.Uf;
import com.zoom.modelo.to.EnderecoTO;
import com.zoom.service.UnidadeService;
import com.zoom.service.rest.BuscaCEPService;
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
public class ManterUnidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;		

	private Unidade unidade;
	private List<Uf> ufs;
	private String cep = null;
	private EnderecoTO enderecoTO;	
	private List<Unidade> unidades = new ArrayList<>();
	private boolean scfv = false;
	
	@Inject
	private UnidadeService unidadeService;
	@Inject
	private BuscaCEPService buscaCEPService;
	

	@Inject
	private LoginBean usuarioLogado;
	
	@PostConstruct
	public void inicializar() {
		log.info("usuario : " + usuarioLogado.getUsuario());
		
		this.ufs = Arrays.asList(Uf.values());
		this.unidades = unidadeService.buscarTodos();
		this.limpar();
	}
	
	public void salvar() {

		try {
			log.info("unidade codigo = " + unidade.getCodigo());
			log.info("unidade nome = " + unidade.getNome());
			log.info("unidade endereco = " + unidade.getEndereco().getLogradouro());
			log.info("unidade numero = " + unidade.getEndereco().getNumero());
			
			unidade.getEndereco().setMunicipio(unidade.getEndereco().getMunicipio());
			Unidade unid = this.unidadeService.salvar(unidade);			
			this.unidades = unidadeService.buscarTodos();
			
			MessageUtil.sucesso("Unidade salva com sucesso!");
			log.info("unidade salva codigo = " + unid.getCodigo());
			log.info("unidade salva nome = " + unid.getNome());
			log.info("unidade salva endereco = " + unid.getEndereco().getLogradouro());
			log.info("unidade salva numero = " + unid.getEndereco().getNumero());
			
			log.info(unidade.getEndereco().toString());
			
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}

		this.limpar();
	}

	public void limpar() {
		this.unidade = new Unidade();
		this.unidade.setEndereco(new Endereco());
	}

	public void buscaEnderecoPorCEP() {

		try {
			enderecoTO = buscaCEPService.buscaEnderecoPorCEP(unidade.getEndereco().getCep());

			/*
			 * Preenche o Endereco com os dados buscados
			 */
			unidade.getEndereco()
					.setLogradouro(enderecoTO.getTipoLogradouro().concat(" ").concat(enderecoTO.getLogradouro()));
			unidade.getEndereco().setBairro(enderecoTO.getBairro());
			unidade.getEndereco().setMunicipio(enderecoTO.getCidade());
			unidade.getEndereco().setUf(enderecoTO.getEstado());
			
			

			if (enderecoTO.getResultado() != 1) {
				MessageUtil.erro("Endereço não encontrado para o CEP fornecido.");
			}
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}

	public void carregarUnidades() {

		unidades = unidadeService.buscarUnidades();
	}

	public void excluir() {
		try {
			unidadeService.excluir(unidade);			
			this.unidades = unidadeService.buscarTodos();
			MessageUtil.sucesso("Unidade excluída com sucesso.");
			log.info("unidade excluida = " + unidade.getNome());
			
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
}
