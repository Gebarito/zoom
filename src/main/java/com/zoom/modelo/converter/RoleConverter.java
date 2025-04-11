package com.zoom.modelo.converter;

import jakarta.faces.convert.EnumConverter;
import jakarta.faces.convert.FacesConverter;

import com.zoom.modelo.enums.Role;

/**
 * @author murakamiadmin
 *
 */
@FacesConverter(value="roleConverter")
public class RoleConverter extends EnumConverter {

    public RoleConverter() {
        super(Role.class);
    }

}
