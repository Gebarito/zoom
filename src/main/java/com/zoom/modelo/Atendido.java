package com.zoom.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Atendido implements Serializable {

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
}


