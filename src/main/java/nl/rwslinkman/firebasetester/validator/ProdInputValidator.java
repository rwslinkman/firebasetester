package nl.rwslinkman.firebasetester.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick Slinkman
 */
public class ProdInputValidator implements InputValidator
{
    @Override
    public List<String> validateInput(String apiKey, String requestBody) {
        List<String> errors = new ArrayList<>();
        errors.add("No errors at this time");
        return errors;
    }
}
