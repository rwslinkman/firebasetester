package nl.rwslinkman.firebasetester.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author Rick Slinkman
 */
public class ProdHttpClient implements HttpClient
{
    private final OkHttpClient client;

    public ProdHttpClient()
    {
        this.client = new OkHttpClient();
    }


    @Override
    public Response sendRequest(Request request) throws IOException {
        return client.newCall(request).execute();
    }
}
