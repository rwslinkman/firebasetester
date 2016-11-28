package nl.rwslinkman.firebasetester.validator;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

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

    /*
     * input => output
     * [x] No API key provided (null)   => 1 message
     * [-] Empty API key provided       => 1 message
     * [-] No recipient provided        => 1 message
     * [-] No data provided             => 1 message
     * [-] No address(es) provided      => 1 message
     */

    @Test
    public void test_shouldReturnEmptyList_whenGivenValidInput_singleRecipient() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";
        String addr = "feuhjfeij293ur9if93k93kd92";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createSingleRecipientJSON(addr, mapToJson(data));

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
        String requestBody = createMultipleRecipientsJSON(addresses, mapToJson(data));

        List<String> output = this.inputValidator.validateInput(apiKey, requestBody);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

    @Test
    public void test_shouldReturnMessage_whenApiKeyMissing_singleRecipient() throws Exception
    {
        String addr = "feuhjfeij293ur9if93k93kd92";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createSingleRecipientJSON(addr, mapToJson(data));

        List<String> output = this.inputValidator.validateInput(null, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_API_KEY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenApiKeyMissing_multipleRecipients() throws Exception
    {
        List<String> addresses = Arrays.asList("feuhjfeij293ur9if93k93kd92", "feuhjfeij293ur9if93k93kd92x", "feuhjfeij293ur9if93k93kd92y");
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createMultipleRecipientsJSON(addresses, mapToJson(data));

        List<String> output = this.inputValidator.validateInput(null, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_API_KEY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenApiKeyEmpty_singleRecipient() throws Exception
    {
        String addr = "feuhjfeij293ur9if93k93kd92";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createSingleRecipientJSON(addr, mapToJson(data));

        List<String> output = this.inputValidator.validateInput("", requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_API_KEY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenApiKeyEmpty_multipleRecipients() throws Exception
    {
        List<String> addresses = Arrays.asList("feuhjfeij293ur9if93k93kd92", "feuhjfeij293ur9if93k93kd92x", "feuhjfeij293ur9if93k93kd92y");
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createMultipleRecipientsJSON(addresses, mapToJson(data));

        List<String> output = this.inputValidator.validateInput("", requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_API_KEY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataMissing_singleRecipient() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        List<String> output = this.inputValidator.validateInput(apiKey, null);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataMissing_multipleRecipients() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        List<String> output = this.inputValidator.validateInput(apiKey, null);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataEmpty_singleRecipient() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        List<String> output = this.inputValidator.validateInput(apiKey, "");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataEmpty_multipleRecipients() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        List<String> output = this.inputValidator.validateInput(apiKey, "");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessages_whenNothingProvided() throws Exception
    {
        List<String> output = this.inputValidator.validateInput(null, null);

        assertNotNull(output);
        assertEquals(2, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
        assertContains(output, ProdInputValidator.MESSAGE_API_KEY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenNoRecipientsProvided() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createMultipleRecipientsJSON(null, mapToJson(data));

        List<String> output = this.inputValidator.validateInput(apiKey, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_RECIPIENT_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenBodyIsNotJSON() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        List<String> output = this.inputValidator.validateInput(apiKey, "Hello world");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_INVALID_JSON);
    }

    @Test
    public void test_shouldReturnMessage_whenNoDataAndNoRecipients()
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        List<String> output = this.inputValidator.validateInput(apiKey, "{}");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_RECIPIENT_MISSING);
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_DATA_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataIsNotJSON() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";
        String addr = "feuhjfeij293ur9if93k93kd92";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createSingleRecipientJSON(addr, mapToJson(data));

        List<String> output = this.inputValidator.validateInput(apiKey, requestBody);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

    // Helper methods
    private String createSingleRecipientJSON(String address, Object data)
    {
        JSONObject obj = new JSONObject();
        if(address != null) {
            obj.put("to", address);
        }
        if(data != null) {
            obj.put("data", data);
        }
        return obj.toString();
    }

    private String createMultipleRecipientsJSON(List<String> addresses, Object data)
    {
        JSONObject obj = new JSONObject();
        if(addresses != null) {
            String[] registrationIDs = addresses.toArray(new String[]{});
            obj.put("registration_ids", registrationIDs);
        }
        if(data != null) {
            obj.put("data", data);
        }
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

    private void assertContains(List<String> list, String shouldContain)
    {
        assertTrue(list.contains(shouldContain));
    }
}