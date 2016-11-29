package nl.rwslinkman.firebasetester.validator;

import org.json.JSONObject;
import org.junit.After;
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
    private List<String> output;

    @Before
    public void setUp() throws Exception {
        this.inputValidator = new ProdInputValidator();
    }

    @After
    public void after() throws Exception {
        if(output.size() > 0)
        {
            for(String string : output)
            {
                System.out.println(string);
            }
        }
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

        output = this.inputValidator.validateInput(apiKey, requestBody);

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

        output = this.inputValidator.validateInput(apiKey, requestBody);

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

        output = this.inputValidator.validateInput(null, requestBody);

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

        output = this.inputValidator.validateInput(null, requestBody);

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

        output = this.inputValidator.validateInput("", requestBody);

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

        output = this.inputValidator.validateInput("", requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_API_KEY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataMissing_singleRecipient() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        output = this.inputValidator.validateInput(apiKey, null);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataMissing_multipleRecipients() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        output = this.inputValidator.validateInput(apiKey, null);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataEmpty_singleRecipient() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        output = this.inputValidator.validateInput(apiKey, "");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataEmpty_multipleRecipients() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        output = this.inputValidator.validateInput(apiKey, "");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessages_whenNothingProvided() throws Exception
    {
        output = this.inputValidator.validateInput(null, null);

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

        output = this.inputValidator.validateInput(apiKey, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_RECIPIENT_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenBodyIsNotJSON() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        output = this.inputValidator.validateInput(apiKey, "Hello world");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_INVALID_JSON);
    }

    @Test
    public void test_shouldReturnMessage_whenNoDataAndNoRecipients()
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";

        output = this.inputValidator.validateInput(apiKey, "{}");

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
        String requestBody = createSingleRecipientJSON(addr, "1234");

        output = this.inputValidator.validateInput(apiKey, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_DATA_NOT_OBJECT);
    }

    @Test
    public void test_shouldReturnMessage_whenDataIsNotString() throws Exception
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";
        String addr = "feuhjfeij293ur9if93k93kd92";
        String requestBody = createSingleRecipientJSON(addr, 1234);

        output = this.inputValidator.validateInput(apiKey, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_DATA_NOT_OBJECT);
    }

    @Test
    public void test_shouldReturnMessage_whenGivenAddressNotString_singleRecipient()
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createSingleRecipientJSON(1234, mapToJson(data));

        output = this.inputValidator.validateInput(apiKey, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_ADDRESS_INVALID);
    }

    @Test
    public void test_shouldReturnMessage_whenGivenAddressNotString_multipleRecipients()
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createMultiRecipientsJSON(1234, mapToJson(data));

        output = this.inputValidator.validateInput(apiKey, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_ADDRESSES_INVALID);
    }

    @Test
    public void test_shouldReturnMessage_whenJsonSuffixed()
    {
        String apiKey = "ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";
        String addr = "feuhjfeij293ur9if93k93kd92";
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        String requestBody = createSingleRecipientJSON(addr, mapToJson(data));

        output = this.inputValidator.validateInput(apiKey, requestBody+"test");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_INVALID_JSON);
    }

    // Helper methods
    private String createSingleRecipientJSON(Object address, Object data)
    {
        JSONObject obj = getDataObject(data);
        if(address != null) {
            obj.put("to", address);
        }
        return obj.toString();
    }

    private JSONObject getDataObject(Object data) {
        JSONObject obj = new JSONObject();
        if(data != null) {
            obj.put("data", data);
        }
        return obj;
    }

    private String createMultipleRecipientsJSON(List<String> addresses, Object data)
    {
        JSONObject obj = getDataObject(data);
        if(addresses != null) {
            String[] registrationIDs = addresses.toArray(new String[]{});
            obj.put("registration_ids", registrationIDs);
        }
        return obj.toString();
    }

    private String createMultiRecipientsJSON(Object addresses, Object data)
    {
        JSONObject obj = getDataObject(data);
        if(addresses != null) {
            obj.put("registration_ids", addresses);
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
        String mapString = mapData.toString();
        System.out.println(mapString);
        return mapString;
    }

    private void assertContains(List<String> list, String shouldContain)
    {
        assertTrue(list.contains(shouldContain));
    }
}