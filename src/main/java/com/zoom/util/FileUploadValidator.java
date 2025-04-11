package com.zoom.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.servlet.http.Part;
 
/**
 * @author murakamiadmin
 *
 */
@FacesValidator(value = "fileUploadValidator")
public class FileUploadValidator implements Validator<Object> {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Part file = (Part) value;

		FacesMessage message = null;

		try {

			if (file == null || file.getSize() <= 0 || file.getContentType().isEmpty())
				message = new FacesMessage("Selecione um arquivo válido");
			else if (!file.getContentType().endsWith("pdf"))
				message = new FacesMessage("Selecione um arquivo PDF");
			else if (file.getSize() > 1000000)
				message = new FacesMessage("O arquivo ultrapassa 1 MB.");

			if (message != null && !message.getDetail().isEmpty()) {
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(message);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ValidatorException(new FacesMessage(ex.getMessage()));
		}

	}

}