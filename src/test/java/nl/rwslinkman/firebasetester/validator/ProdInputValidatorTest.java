package nl.rwslinkman.firebasetester.validator;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Rick Slinkman
 */
public class ProdInputValidatorTest
{
    private static final String MOCK_API_KEY = "key=ILIHWELUBEF784efefijwineuifNIWI48485651fwiu";
    private static final List<String> ADDRESSES = Arrays.asList("feuhjfeij293ur9if93k93kd92x", "feuhjfeij293ur9if93k93kd92y", "feuhjfeij293ur9if93k93kd92z");
    public static final String ADDRESS = "feuhjfeij293ur9if93k93kd92";
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

    @Test
    public void test_shouldReturnEmptyList_whenGivenValidInput_singleRecipient() throws Exception
    {
        Map<String, Object> data = mockMessageData();
        String requestBody = createSingleRecipientJSON(ADDRESS, mapToJson(data));

        output = this.inputValidator.validateInput(MOCK_API_KEY, requestBody);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

    @Test
    public void test_shouldReturnEmptyList_whenGivenValidInput_multipleRecipients() throws Exception
    {
        Map<String, Object> data = mockMessageData();
        String requestBody = createMultipleRecipientsJSON(ADDRESSES, mapToJson(data));

        output = this.inputValidator.validateInput(MOCK_API_KEY, requestBody);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

    @Test
    public void test_shouldReturnMessage_whenApiKeyMissing_singleRecipient() throws Exception
    {
        Map<String, Object> data = mockMessageData();
        String requestBody = createSingleRecipientJSON(ADDRESS, mapToJson(data));

        output = this.inputValidator.validateInput(null, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_API_KEY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenApiKeyMissing_multipleRecipients() throws Exception
    {
        Map<String, Object> data = mockMessageData();
        String requestBody = createMultipleRecipientsJSON(ADDRESSES, mapToJson(data));

        output = this.inputValidator.validateInput(null, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_API_KEY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenApiKeyEmpty_singleRecipient() throws Exception
    {
        Map<String, Object> data = mockMessageData();
        String requestBody = createSingleRecipientJSON(ADDRESS, mapToJson(data));

        output = this.inputValidator.validateInput("", requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_API_KEY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenApiKeyEmpty_multipleRecipients() throws Exception
    {
        Map<String, Object> data = mockMessageData();
        String requestBody = createMultipleRecipientsJSON(ADDRESSES, mapToJson(data));

        output = this.inputValidator.validateInput("", requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_API_KEY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataMissing_singleRecipient() throws Exception
    {
        output = this.inputValidator.validateInput(MOCK_API_KEY, null);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataMissing_multipleRecipients() throws Exception
    {
        output = this.inputValidator.validateInput(MOCK_API_KEY, null);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataEmpty_singleRecipient() throws Exception
    {
        output = this.inputValidator.validateInput(MOCK_API_KEY, "");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataEmpty_multipleRecipients() throws Exception
    {
        output = this.inputValidator.validateInput(MOCK_API_KEY, "");

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
        Map<String, Object> data = mockMessageData();
        String requestBody = createMultipleRecipientsJSON(null, mapToJson(data));

        output = this.inputValidator.validateInput(MOCK_API_KEY, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_RECIPIENT_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenBodyIsNotJSON() throws Exception
    {
        output = this.inputValidator.validateInput(MOCK_API_KEY, "Hello world");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_INVALID_JSON);
    }

    @Test
    public void test_shouldReturnMessage_whenNoDataAndNoRecipients()
    {
        output = this.inputValidator.validateInput(MOCK_API_KEY, "{}");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_RECIPIENT_MISSING);
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_DATA_MISSING);
    }

    @Test
    public void test_shouldReturnMessage_whenDataIsNotJSON() throws Exception
    {
        String addr = "feuhjfeij293ur9if93k93kd92";
        String requestBody = createSingleRecipientJSON(addr, "1234");

        output = this.inputValidator.validateInput(MOCK_API_KEY, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_DATA_NOT_OBJECT);
    }

    @Test
    public void test_shouldReturnMessage_whenDataIsNotString() throws Exception
    {
        String addr = "feuhjfeij293ur9if93k93kd92";
        String requestBody = createSingleRecipientJSON(addr, 1234);

        output = this.inputValidator.validateInput(MOCK_API_KEY, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_DATA_NOT_OBJECT);
    }

    @Test
    public void test_shouldReturnMessage_whenGivenAddressNotString_singleRecipient()
    {
        Map<String, Object> data = mockMessageData();
        String requestBody = createSingleRecipientJSON(1234, mapToJson(data));

        output = this.inputValidator.validateInput(MOCK_API_KEY, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_ADDRESS_INVALID);
    }

    @Test
    public void test_shouldReturnMessage_whenGivenAddressNotString_multipleRecipients()
    {
        Map<String, Object> data = mockMessageData();
        JSONObject obj = getDataObject(data);
        obj.put("registration_ids", (Object) 1234);
        String requestBody = obj.toString();

        output = this.inputValidator.validateInput(MOCK_API_KEY, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_ADDRESSES_INVALID);
    }

    @Test
    public void test_shouldReturnMessage_whenJsonSuffixed()
    {
        String addr = "feuhjfeij293ur9if93k93kd92";
        Map<String, Object> data = mockMessageData();
        String requestBody = createSingleRecipientJSON(addr, mapToJson(data));

        output = this.inputValidator.validateInput(MOCK_API_KEY, requestBody+"test");

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_INVALID_JSON);
    }

    @Test
    public void test_shouldReturnMessage_whenRegistrationIDsNotOnlyStrings()
    {
        List<Object> addresses = Arrays.asList(ADDRESSES.get(0), ADDRESSES.get(1), 1234L);
        Map<String, Object> data = mockMessageData();
        String requestBody = createMultiObjectsRecipientsJSON(addresses, mapToJson(data));

        output = this.inputValidator.validateInput(MOCK_API_KEY, requestBody);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertContains(output, ProdInputValidator.MESSAGE_REQUEST_BODY_ADDRESSES_NOT_ONLY_STRINGS);
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

    private String createMultiObjectsRecipientsJSON(List<Object> addresses, Object data)
    {
        JSONObject obj = getDataObject(data);
        if(addresses != null) {
            Object[] registrationIDs = addresses.toArray(new Object[]{});
            obj.put("registration_ids", registrationIDs);
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

    private Map<String, Object> mockMessageData() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Hello world");
        return data;
    }

    private void assertContains(List<String> list, String shouldContain)
    {
        assertTrue(list.contains(shouldContain));
    }
}