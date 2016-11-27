package nl.rwslinkman.firebasetester.gui;

import nl.rwslinkman.firebasetester.gui.listener.UserInterfaceEventListener;

import java.util.List;

/**
 * @author Rick Slinkman
 */
public interface GUI
{
    void setUserInterfaceEventListener(UserInterfaceEventListener listener);
    void createFrame();
    void showErrors(List<String> errors);
}
