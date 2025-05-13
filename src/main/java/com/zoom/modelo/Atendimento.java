package com.zoom.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.zoom.modelo.enums.StatusAtendimento;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Timpone
 *
 */
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@NamedQueries({
	@NamedQuery(name="Atendimento.buscarTodosAgendados", query="select c from Atendimento c where c.unidade = :unidade "
			+ "and c.statusAtendimento = :statusAtendimento "
			+ "and c.dataAgendamento > :data")
})
public class Atendimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ToString.Include
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;

	private String nome;
	private String telefone;
	private String descricao;

	private LocalDateTime dataAgendamento;
	private LocalDateTime dataAtendimento;

	@Enumerated(EnumType.STRING)
	private StatusAtendimento statusAtendimento;

	@ManyToOne
	@JoinColumn(name = "codigo_unidade")
	private Unidade unidade;
	
	@ManyToOne
	@JoinColumn(name = "codigo_tecnicoAgendamento")
	private Usuario tecnicoAgendamento;
	@ManyToOne
	@JoinColumn(name = "codigo_tecnicoAtendimento")
	private Usuario tecnicoAtendimento;

	/*
	 * Datas de Criação e Modificação
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataModificacao;

	
	public Atendimento() {}
	
	public Atendimento(String nome, String telefone, String descricao, LocalDateTime dataAgendamento, StatusAtendimento statusAtendimento, Unidade unidade, Usuario tecnicoAtendimento) {
		this.nome = nome;
		this.telefone = telefone;
		this.descricao = descricao;
		this.setDataAgendamento(dataAgendamento);
		this.statusAtendimento = statusAtendimento;
		this.unidade = unidade;
		this.tecnicoAtendimento = tecnicoAtendimento;
	}
	
	@PrePersist
	@PreUpdate
	public void configuraDatasCriacaoAlteracao() {
		this.setDataModificacao( new Date() );

		if (this.getDataCriacao() == null) {
			this.setDataCriacao( new Date() );
		}		
	}
}