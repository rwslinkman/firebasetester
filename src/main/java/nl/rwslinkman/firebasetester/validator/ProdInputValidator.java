package nl.rwslinkman.firebasetester.validator;

import org.json.JSONArray;
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
    static final String MESSAGE_REQUEST_BODY_ADDRESS_INVALID = "The provided 'to' property is not a invalid String";
    static final String MESSAGE_REQUEST_BODY_ADDRESSES_INVALID = "The 'registration_ids' property should be of type array";
    static final String MESSAGE_REQUEST_BODY_ADDRESSES_NOT_ONLY_STRINGS = "The provided 'registrations_id' array property can only contain Strings";
    static final String MESSAGE_REQUEST_BODY_DATA_NOT_OBJECT = "The provided 'data' property should be a JSON object";

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
            requestBody = requestBody.replaceAll("(?!\\r)\\n", "\r\n");
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
                    if(requestObject.has("to"))
                    {
                        if(!(requestObject.get("to") instanceof String))
                        {
                            errors.add(MESSAGE_REQUEST_BODY_ADDRESS_INVALID);
                        }
                        else if(requestObject.getString("to").isEmpty())
                        {
                            errors.add(MESSAGE_REQUEST_BODY_ADDRESS_INVALID);
                        }
                    }
                    else if(!(requestObject.get("registration_ids") instanceof JSONArray))
                    {
                        errors.add(MESSAGE_REQUEST_BODY_ADDRESSES_INVALID);
                    }
                    else {
                        JSONArray registrationIDs = requestObject.getJSONArray("registration_ids");
                        boolean hasStringsOnly = true;
                        for(int i = 0; i < registrationIDs.length(); i++)
                        {
                            if(!(registrationIDs.get(i) instanceof String))
                            {
                                hasStringsOnly = false;
                            }
                        }

                        if(!hasStringsOnly) {
                            errors.add(MESSAGE_REQUEST_BODY_ADDRESSES_NOT_ONLY_STRINGS);
                        }
                    }
                }
                if(!requestObject.has("data"))
                {
                    errors.add(MESSAGE_REQUEST_BODY_DATA_MISSING);
                }
                else
                {
                    Object data = requestObject.get("data");
                    if(!(data instanceof JSONObject))
                    {
                        if(data instanceof String)
                        {
                            if(!isValidJSON((String) data))
                            {
                                errors.add(MESSAGE_REQUEST_BODY_DATA_NOT_OBJECT);
                            }
                        }
                        else
                        {
                            errors.add(MESSAGE_REQUEST_BODY_DATA_NOT_OBJECT);
                        }
                    }
                }
            }
        }
        return errors;
    }

    private boolean isValidJSON(String requestBody) {
        try {
            new JSONObject(requestBody); // Checks from start of String, ignores after }
            return requestBody.endsWith("}");
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
