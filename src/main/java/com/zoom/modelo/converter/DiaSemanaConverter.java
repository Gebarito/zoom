package com.zoom.modelo.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

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
