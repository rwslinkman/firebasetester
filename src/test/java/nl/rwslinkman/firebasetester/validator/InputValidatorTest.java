package nl.rwslinkman.firebasetester.validator;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Rick Slinkman
 */
public class InputValidatorTest
{
    private ProdInputValidator inputValidator;

    @Before
    public void setUp() throws Exception {
        this.inputValidator = new ProdInputValidator();
    }

    @Test
    public void test_shouldReturnEmptyList_whenGivenValidInput_singleRecipient() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";
        String addr = "feuhjfeij293ur9if93k93kd92";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createSingleRecepientJSON(addr, mapToJson(data));

        List<String> output = this.inputValidator.validateInput(apiKey, requestBody);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

    @Test
    public void test_shouldReturnEmptyList_whenGivenValidInput_multipleRecipients() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";
        List<String> addresses = Arrays.asList("feuhjfeij293ur9if93k93kd92", "feuhjfeij293ur9if93k93kd92x", "feuhjfeij293ur9if93k93kd92y");
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createMultipleRecepientsJSON(addresses, mapToJson(data));

        List<String> output = this.inputValidator.validateInput(apiKey, requestBody);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

    private String createSingleRecepientJSON(String address, Object data)
    {
        JSONObject obj = new JSONObject();
        obj.put("to", address);
        obj.put("data", data);
        return obj.toString();
    }

    private String createMultipleRecepientsJSON(List<String> addresses, Object data)
    {
        String[] registrationIDs = addresses.toArray(new String[]{});
        JSONObject obj = new JSONObject();
        obj.put("registration_ids", registrationIDs);
        obj.put("data", data);
        return obj.toString();
    }

    private String mapToJson(Map<String, Object> data)
    {
        JSONObject mapData = new JSONObject();
        for(Map.Entry<String, Object> entry : data.entrySet())
        {
            mapData.put(entry.getKey(), entry.getValue());
        }
        return mapData.toString();
    }
}