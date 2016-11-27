package nl.rwslinkman.firebasetester.gui.listener;

/**
 * @author Rick Slinkman
 */
public interface UserInterfaceEventListener {
    void onExit();
    void onFirebaseTestSubmitted(String apiKey, String requestBody);
}
