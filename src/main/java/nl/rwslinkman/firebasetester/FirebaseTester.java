package nl.rwslinkman.firebasetester;

import nl.rwslinkman.firebasetester.gui.FirebaseTesterGUI;
import nl.rwslinkman.firebasetester.gui.GUI;
import nl.rwslinkman.firebasetester.gui.listener.UserInterfaceEventListener;
import nl.rwslinkman.firebasetester.http.HttpClient;
import nl.rwslinkman.firebasetester.http.ProdHttpClient;
import nl.rwslinkman.firebasetester.validator.InputValidator;
import nl.rwslinkman.firebasetester.validator.ProdInputValidator;
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

    private GUI mGUI;
    private HttpClient mHttpClient;
    private InputValidator mValidator;

    public static void main(String[] args) {
        System.out.println("FirebaseTester starting");
        new FirebaseTester()
                .setHttpClient(new ProdHttpClient())
                .setInputValidator(new ProdInputValidator())
                .setGUI(new FirebaseTesterGUI())
                .start();
    }

    public void start() {
        int errorCount = 0;
        if(mGUI == null) {
            System.out.println("ERROR: No GUI set");
            errorCount++;
        }
        if(mValidator == null) {
            System.out.println("ERROR: No InputValidator set");
            errorCount++;
        }
        if(mHttpClient == null) {
            System.out.println("ERROR: No HttpClient set");
            errorCount++;
        }

        if(errorCount > 0)
        {
            System.out.println("FirebaseTester shutting down with " + Integer.toString(errorCount) + " error(s)");
            onExit();
            return;
        }

        System.out.println("FirebaseTester can start without warnings");
        mGUI.createFrame();
    }

    public GUI getGUI() {
        return mGUI;
    }

    public void onExit() {
        System.out.println("FirebaseTester exit");
    }

    @Override
    public void onFirebaseTestSubmitted(String apiKey, String requestBody)
    {
        List<String> errors = mValidator.validateInput(apiKey, requestBody);
        if(errors.size() > 0)
        {
            mGUI.showErrors(errors);
            return;
        }

        requestBody = requestBody.replaceAll("(?!\\r)\\n", "\r\n");
        // TODO Create http request to Firebase
        System.out.println("Send request");
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

    public FirebaseTester setHttpClient(HttpClient httpClient) {
        this.mHttpClient = httpClient;
        return this;
    }

    public FirebaseTester setInputValidator(InputValidator inputValidator) {
        this.mValidator = inputValidator;
        return this;
    }

    public FirebaseTester setGUI(GUI gui) {
        this.mGUI = gui;
        this.mGUI.setUserInterfaceEventListener(this);
        return this;
    }
}
