package nl.rwslinkman.firebasetester;

import nl.rwslinkman.firebasetester.gui.FirebaseTesterGUI;
import nl.rwslinkman.firebasetester.gui.RequestBodyParser;
import nl.rwslinkman.firebasetester.gui.listener.UserInterfaceEventListener;

/**
 * @author Rick Slinkman
 */
public class FirebaseTester implements UserInterfaceEventListener
{
    public static final int VERSION_CODE = 1; // TODO Get from gradle
    public static final String VERSION_NAME = "0.1"; // TODO Get from gradle
    private FirebaseTesterGUI mGUI;

    public static void main(String[] args)
    {
        System.out.println("FirebaseTester starting");
        new FirebaseTester().start();
        System.out.println("FirebaseTester started");
    }

    private void start() {
        mGUI = new FirebaseTesterGUI(this);
        mGUI.setRequestBodyParser(new RequestBodyParser() {
            @Override
            public String getEmpty() {
                return "Waiting...";
            }

            @Override
            public String parseRequestBody(String rawInput) {
                return "Valid";
            }
        });
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
    }
}
