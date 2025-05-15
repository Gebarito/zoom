package com.zoom.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	
	@CreationTimestamp	
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;
	
	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataModificacao;
}