package nl.rwslinkman.firebasetester.mock;

import nl.rwslinkman.firebasetester.http.HttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author Rick Slinkman
 */
public class MockHttpClient implements HttpClient {
    @Override
    public Response sendRequest(Request request) throws IOException {
        return null;
    }

    public boolean wasCalled() {
        return true;
    }
}
