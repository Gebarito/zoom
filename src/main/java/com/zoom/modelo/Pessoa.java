package com.zoom.modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author LindmanBassi
 *
 */
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class Pessoa implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8423771054396723948L;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long codigo;
    private String nome;
    
    @ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="codigo_atendido")
	private Atendido atendido;
}


