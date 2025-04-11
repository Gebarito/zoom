package com.zoom.util.security;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@SuppressWarnings("rawtypes")
@FacesValidator("requiredCheckboxValidator")
public class RequiredCheckboxValidator implements Validator {

    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        if (value.equals(Boolean.FALSE)) {
            String requiredMessage = ((UIInput) component).getRequiredMessage();

            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, requiredMessage, requiredMessage));
        }
    }
}