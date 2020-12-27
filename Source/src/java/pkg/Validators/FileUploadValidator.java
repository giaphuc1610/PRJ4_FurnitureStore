package pkg.Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

@FacesValidator("fileUploadValidator")
public class FileUploadValidator
        implements Validator {

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Part file = (Part) value;
        FacesMessage message = null;
        try {
            if (file == null || file.getSize() <= 0L || file.getContentType().isEmpty()) {
                message = new FacesMessage("+ Please select a valid file.");
            } else if (!file.getContentType().startsWith("image")) {
                message = new FacesMessage("+ Please select an image file.");
            } else if (file.getSize() > 1024000L) {
                message = new FacesMessage("+ File size too big. Please select a file whic has size less than or equal to 1 MB.");
            }

            if (message != null && !message.getDetail().isEmpty()) {
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        } catch (Exception ex) {
            throw new ValidatorException(new FacesMessage(ex.getMessage()));
        }
    }
}
