package nl.rwslinkman.firebasetester.http;

import okhttp3.*;
import sun.plugin.dom.exception.InvalidStateException;

import java.io.IOException;

/**
 * @author Rick Slinkman
 */
public class ProdHttpClient implements HttpClient
{
    private static final MediaType JSON = MediaType.parse("application/json");
    private final OkHttpClient client;
    private HttpUrl mUrl;
    private String mApiKey;

    public ProdHttpClient()
    {
        this.client = new OkHttpClient();
    }

    @Override
    public Response sendRequest(String body) throws IOException
    {
        if(mUrl == null) {
            throw new InvalidStateException("Please provide URL before sending request");
        }
        if(mApiKey == null || mApiKey.isEmpty()) {
            throw new InvalidStateException("Please provide API key before sending request");
        }
        if(body == null) {
            body = "";
        }

        // Create request after validation
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request request = new Request.Builder()
                .url(mUrl)
                .header("Authorization", "key="+mApiKey)
                .post(requestBody)
                .build();
        // Send
        okhttp3.Response response = client.newCall(request).execute();
        Response formattedResponse = new Response();
        formattedResponse.setResponseBody(response.body().string());
        formattedResponse.setResponseCode(response.code());
        return formattedResponse;
    }

    @Override
    public void prepareRequest(HttpUrl url) {
        this.mUrl = url;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.mApiKey = apiKey;
    }
}
