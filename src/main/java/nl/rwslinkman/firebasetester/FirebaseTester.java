package nl.rwslinkman.firebasetester;

import nl.rwslinkman.firebasetester.gui.FirebaseTesterGUI;
import nl.rwslinkman.firebasetester.gui.GUI;
import nl.rwslinkman.firebasetester.gui.listener.UserInterfaceEventListener;
import nl.rwslinkman.firebasetester.http.HttpClient;
import nl.rwslinkman.firebasetester.http.ProdHttpClient;
import nl.rwslinkman.firebasetester.validator.InputValidator;
import nl.rwslinkman.firebasetester.validator.ProdInputValidator;
import okhttp3.HttpUrl;

import java.io.IOException;
import java.util.List;

/**
 * @author Rick Slinkman
 */
public class FirebaseTester implements UserInterfaceEventListener
{
    public static final int VERSION_CODE = 1; // TODO Get from gradle
    public static final String VERSION_NAME = "0.1"; // TODO Get from gradle
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    private GUI mGUI;
    private HttpClient mHttpClient;
    private InputValidator mValidator;

    public static void main(String[] args)
    {
        System.out.println("FirebaseTester starting");
        FirebaseTester tester = new FirebaseTester.Builder()
                .setHttpClient(new ProdHttpClient())
                .setInputValidator(new ProdInputValidator())
                .setGUI(new FirebaseTesterGUI())
                .build();
        tester.start();
    }

    FirebaseTester(HttpClient httpClient, InputValidator validator, GUI gui)
    {
        this.mHttpClient = httpClient;
        this.mValidator = validator;
        this.mGUI = gui;
        this.mGUI.setUserInterfaceEventListener(this);
    }

    private void start() {
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

    @Override
    public void onFirebaseTestSubmitted(String apiKey, String requestBody)
    {
        List<String> errors = mValidator.validateInput(apiKey, requestBody);
        if(errors.size() > 0)
        {
            mGUI.showErrors(errors);
            return;
        }

        mHttpClient.setApiKey(apiKey);
        mHttpClient.prepareRequest(HttpUrl.parse(FIREBASE_API_URL));

        // TODO Run on Thread
        try {
            requestBody = requestBody.replaceAll("(?!\\r)\\n", "\r\n");
            HttpClient.Response response = mHttpClient.sendRequest(requestBody);
            System.out.println(response.toString());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    static class Builder
    {

        private HttpClient mHttpClient;
        private InputValidator mValidator;
        private GUI mGUI;
        FirebaseTester.Builder setHttpClient(HttpClient httpClient) {
            this.mHttpClient = httpClient;
            return this;
        }

        FirebaseTester.Builder setInputValidator(InputValidator inputValidator) {
            this.mValidator = inputValidator;
            return this;
        }

        FirebaseTester.Builder setGUI(GUI gui) {
            this.mGUI = gui;
            return this;
        }

        FirebaseTester build()
        {
            if(mHttpClient == null) {
                throw new IllegalStateException("HttpClient cannot be null");
            }
            if(mValidator == null) {
                throw new IllegalStateException("InputValidator cannot be null");
            }
            if(mGUI == null) {
                throw new IllegalStateException("GUI cannot be null");
            }
            return new FirebaseTester(this.mHttpClient, mValidator, mGUI);
        }

    }

    public void onExit() {
        System.out.println("FirebaseTester exit");
    }
}
