package nl.rwslinkman.firebasetester.gui.form;

/**
 * @author Rick Slinkman
 */
public interface FormInteractionListener
{
    void onFormSubmitted(String apiKey, String body);
    void changeFormPanel(String panelName);
}
