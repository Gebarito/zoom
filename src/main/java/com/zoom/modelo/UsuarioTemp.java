package com.zoom.modelo;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.validation.constraints.Email;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@NamedQueries({
	@NamedQuery(name="UsuarioTemp.buscarPorEmail", query="select u from UsuarioTemp u "
			+ "where u.email = :email")
})	
public class UsuarioTemp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Email
    @Column(unique = true)
    private String email;
    private String senha;
    private String validacao;
    private String token;
    
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
