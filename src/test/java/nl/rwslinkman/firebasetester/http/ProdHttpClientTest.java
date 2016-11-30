package nl.rwslinkman.firebasetester.http;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Rick Slinkman
 */
public class ProdHttpClientTest {
    private static final String MOCK_SERVER_URL = "/my-cool-url.computer/v1/message";
    private static final String ADDRESS = "feuhjfeij293ur9if93k93kd92";
    private HttpClient client;

    @Before
    public void setUp() {
        client = new ProdHttpClient();
        client.setApiKey("ThisIsOneVeryComplicatedApiKeyRight?");
    }

    @Test
    public void test_shouldSendRequest_whenGivenValidParameters() throws Exception {
        // Mock server to receive requests
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("hello, world!"));
        server.start();

        // Prepare request
        HttpUrl baseUrl = server.url(MOCK_SERVER_URL);
        client.prepareRequest(baseUrl);

        // Send request
        client.sendRequest(getValidRequestString());

        // Assert request
        RecordedRequest request1 = server.takeRequest();
        assertEquals(MOCK_SERVER_URL, request1.getPath());
        assertNotNull(request1.getHeader("Authorization"));
        assertEquals("application/json; charset=utf-8", request1.getHeader("Content-Type"));
        assertEquals(getValidRequestString(), request1.getBody().readUtf8());

        // Shut down the server. Instances cannot be reused.
        server.shutdown();
    }

    @Test(expected = InvalidStateException.class)
    public void test_shouldNotSendRequest_whenNotPreparedForURL() throws Exception {
        // Send request
        client.sendRequest(getValidRequestString());
    }

    @Test(expected = InvalidStateException.class)
    public void test_shouldNotSendRequest_whenApiKeyNotSet() throws Exception
    {
        // Prepare request
        client.setApiKey(null);
        client.prepareRequest(HttpUrl.parse(MOCK_SERVER_URL));

        // Send request
        client.sendRequest(getValidRequestString());
    }

    @Test(expected = InvalidStateException.class)
    public void test_shouldNotSendRequest_whenApiKeyEmpty() throws Exception
    {
        // Prepare request
        client.setApiKey("");
        client.prepareRequest(HttpUrl.parse(MOCK_SERVER_URL));

        // Send request
        client.sendRequest(getValidRequestString());
    }

    @Test
    public void test_shouldSendRequest_whenNoBodyProvided() throws Exception {
        // Mock server to receive requests
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("hello, world!"));
        server.start();

        // Prepare request
        HttpUrl baseUrl = server.url(MOCK_SERVER_URL);
        client.prepareRequest(baseUrl);

        // Send request
        client.sendRequest(null);

        // Assert request
        RecordedRequest request1 = server.takeRequest();
        assertEquals(MOCK_SERVER_URL, request1.getPath());
        assertNotNull(request1.getHeader("Authorization"));
        assertEquals("application/json; charset=utf-8", request1.getHeader("Content-Type"));
        assertEquals("", request1.getBody().readUtf8());

        // Shut down the server. Instances cannot be reused.
        server.shutdown();
    }

    @Test
    public void test_shouldSendRequest_whenEmptyBodyProvided() throws Exception {
        // Mock server to receive requests
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("hello, world!"));
        server.start();

        // Prepare request
        HttpUrl baseUrl = server.url(MOCK_SERVER_URL);
        client.prepareRequest(baseUrl);

        // Send request
        client.sendRequest("");

        // Assert request
        RecordedRequest request1 = server.takeRequest();
        assertEquals(MOCK_SERVER_URL, request1.getPath());
        assertNotNull(request1.getHeader("Authorization"));
        assertEquals("application/json; charset=utf-8", request1.getHeader("Content-Type"));
        assertEquals("", request1.getBody().readUtf8());

        // Shut down the server. Instances cannot be reused.
        server.shutdown();
    }

    private String getValidRequestString()
    {
        Map<String, Object> data = mockMessageData();
        JSONObject obj = new JSONObject();
        obj.put("data", mapToJson(data));
        obj.put("to", ADDRESS);
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
}