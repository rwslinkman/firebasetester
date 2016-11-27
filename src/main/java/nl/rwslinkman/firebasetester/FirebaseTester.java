package nl.rwslinkman.firebasetester;

import nl.rwslinkman.firebasetester.gui.FirebaseTesterGUI;
import nl.rwslinkman.firebasetester.gui.listener.UserInterfaceEventListener;
import nl.rwslinkman.firebasetester.http.HttpClient;
import nl.rwslinkman.firebasetester.http.ProdHttpClient;
import nl.rwslinkman.firebasetester.validator.InputValidator;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

/**
 * @author Rick Slinkman
 */
public class FirebaseTester implements UserInterfaceEventListener
{
    public static final int VERSION_CODE = 1; // TODO Get from gradle
    public static final String VERSION_NAME = "0.1"; // TODO Get from gradle
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private FirebaseTesterGUI mGUI;
    private HttpClient mHttpClient;
    private InputValidator mValidator;

    public static void main(String[] args)
    {
        System.out.println("FirebaseTester starting");
        new FirebaseTester().start();
        System.out.println("FirebaseTester started");
    }

    private void start() {
        mHttpClient = new ProdHttpClient();
        mValidator = null; // TODO Create FirebaseRequestValidator


        mGUI = new FirebaseTesterGUI(this);
        mGUI.createFrame();
    }

    public FirebaseTesterGUI getGUI() {
        return mGUI;
    }

    public void onExit() {
        System.out.println("FirebaseTester exit");
    }

    @Override
    public void onFirebaseTestSubmitted(String apiKey, String requestBody)
    {
        System.out.println("Send request with api key and request body");
        // TODO make mValidator return list of responses (empty if valid request)
        List<String> errors = mValidator.validateInput(apiKey, requestBody);

        // Temp response to button click
        errors.add("No errors found");
        mGUI.showErrors(errors);
    }

    private String post(String url, String json) throws IOException
    {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = mHttpClient.sendRequest(request);

        return response.body().string();
    }
}
