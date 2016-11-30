package nl.rwslinkman.firebasetester.mock;

import nl.rwslinkman.firebasetester.http.HttpClient;
import okhttp3.*;

import java.io.IOException;

/**
 * @author Rick Slinkman
 */
public class MockHttpClient implements HttpClient
{
    private static final MediaType JSON = MediaType.parse("application/json");
    private boolean mWasCalledSendRequest = false;
    private boolean mWasCalledPrepareRequest = false;
    private boolean mWasCalledSetApiKey = false;
    private Response mResponse;

    @Override
    public Response sendRequest(String body) throws IOException
    {
        mWasCalledSendRequest = true;
        Response usedResponse = mResponse;
        if(usedResponse == null) {
            usedResponse = defaultResponse();
        }
        return usedResponse;
    }

    @Override
    public void prepareRequest(HttpUrl url) {
        mWasCalledPrepareRequest = true;
    }

    @Override
    public void setApiKey(String apiKey) {
        // NOP
        mWasCalledSetApiKey = true;
    }

    public boolean wasCalledSendRequest() {
        return mWasCalledSendRequest;
    }

    private Response defaultResponse()
    {
        String body = "{\"multicast_id\": 7698556506725942200,\"success\": 1,\"failure\": 0,\"canonical_ids\": 0,\"results\": [{\"message_id\": \"0:1480533949615477%638ad945f9fd7ecd\"}]}";
        Response r = new Response();
        r.setResponseBody(body);
        r.setResponseCode(200);
        return r;
    }

    public boolean wasCalledPrepareRequest() {
        return mWasCalledPrepareRequest;
    }

    public boolean wasCalledSetApiKey() {
        return mWasCalledSetApiKey;
    }

    public void setResponse(Response mResponse) {
        this.mResponse = mResponse;
    }
}
