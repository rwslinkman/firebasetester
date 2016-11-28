package nl.rwslinkman.firebasetester.validator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick Slinkman
 */
public class ProdInputValidator implements InputValidator
{
    static final String MESSAGE_API_KEY_MISSING = "Please provide a Firebase API Key";
    static final String MESSAGE_REQUEST_BODY_MISSING = "Please provide a request body";
    static final String MESSAGE_REQUEST_BODY_INVALID_JSON = "The request body should be a JSON String";
    static final String MESSAGE_REQUEST_BODY_RECIPIENT_MISSING = "No recipient provided. Please set the 'to' or 'registration_ids' property in the JSON body";
    static final String MESSAGE_REQUEST_BODY_DATA_MISSING = "No data provided. Please set the 'data' property in the JSON body";

    @Override
    public List<String> validateInput(String apiKey, String requestBody)
    {
        List<String> errors = new ArrayList<>();
        if(!isValidApiKey(apiKey)) {
            errors.add(MESSAGE_API_KEY_MISSING);
        }
        if(!isRequestBodyValidateable(requestBody))
        {
            errors.add(MESSAGE_REQUEST_BODY_MISSING);
        }
        else
        {
            if(!isValidJSON(requestBody))
            {
                errors.add(MESSAGE_REQUEST_BODY_INVALID_JSON);
            }
            else {
                JSONObject requestObject = new JSONObject(requestBody);
                if(!requestObject.has("to") && !requestObject.has("registration_ids"))
                {
                    errors.add(MESSAGE_REQUEST_BODY_RECIPIENT_MISSING);
                }
                else {
                    if(requestObject.has("to") && requestObject.get("to") instanceof String && !requestObject.getString("to").isEmpty())
                    {

                    }
                }
                if(!requestObject.has("data"))
                {
                    errors.add(MESSAGE_REQUEST_BODY_DATA_MISSING);
                }
            }
        }
        return errors;
    }

    private boolean isValidJSON(String requestBody) {
        try {
            new JSONObject(requestBody);
            return true;

        }
        catch(Exception je){
            return false;
        }
    }

    private boolean isRequestBodyValidateable(String requestBody) {
        return requestBody != null && !requestBody.isEmpty();
    }

    private boolean isValidApiKey(String apiKey)
    {
        return apiKey != null && !apiKey.isEmpty();
    }
}
