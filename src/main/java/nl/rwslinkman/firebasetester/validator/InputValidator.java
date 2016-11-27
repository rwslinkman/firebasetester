package nl.rwslinkman.firebasetester.validator;

import java.util.List;

/**
 * @author Rick Slinkman
 */
public interface InputValidator
{
    List<String> validateInput(String apiKey, String requestBody);
}
