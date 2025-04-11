package com.zoom.modelo.converter;

import jakarta.faces.convert.EnumConverter;
import jakarta.faces.convert.FacesConverter;

import com.zoom.modelo.enums.DiaSemana;

/**
 * @author murakamiadmin
 *
 */
@FacesConverter(value="diaSemanaConverter")
public class DiaSemanaConverter extends EnumConverter {

    public DiaSemanaConverter() {
        super(DiaSemana.class);
    }

}
