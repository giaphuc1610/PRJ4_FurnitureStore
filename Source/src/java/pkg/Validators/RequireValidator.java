package pkg.Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("requireValidator")
public class RequireValidator
        implements Validator {

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String str = value.toString().trim();
        if (str.length() == 0) {
            throw new ValidatorException(new FacesMessage("String is empty."));
        }
    }
}
