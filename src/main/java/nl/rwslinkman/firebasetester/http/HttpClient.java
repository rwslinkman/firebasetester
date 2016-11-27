package nl.rwslinkman.firebasetester.http;

import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author Rick Slinkman
 */
public interface HttpClient
{
    Response sendRequest(Request request) throws IOException;
}
