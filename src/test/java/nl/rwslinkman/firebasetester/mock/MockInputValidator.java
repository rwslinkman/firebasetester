package nl.rwslinkman.firebasetester.mock;

import nl.rwslinkman.firebasetester.validator.InputValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick Slinkman
 */
public class MockInputValidator implements InputValidator {
    @Override
    public List<String> validateInput(String apiKey, String requestBody) {
        return new ArrayList<>();
    }

    public boolean wasCalled() {
        return false;
    }
}
