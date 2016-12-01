package nl.rwslinkman.firebasetester.http;

import okhttp3.HttpUrl;

import java.io.IOException;

/**
 * @author Rick Slinkman
 */
public interface HttpClient
{
    Response sendRequest(String body) throws IOException;
    void prepareRequest(HttpUrl url);
    void setApiKey(String apiKey);

    class Response {
        private int responseCode;
        private String responseBody;

        public int getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }

        public String getResponseBody() {
            return responseBody;
        }

        public void setResponseBody(String responseBody) {
            this.responseBody = responseBody;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "responseCode=" + responseCode +
                    ", responseBody='" + responseBody + '\'' +
                    '}';
        }
    }
}
