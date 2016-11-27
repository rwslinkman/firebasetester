package nl.rwslinkman.firebasetester.mock;

import nl.rwslinkman.firebasetester.gui.GUI;
import nl.rwslinkman.firebasetester.gui.listener.UserInterfaceEventListener;

import java.util.List;

/**
 * @author Rick Slinkman
 */
public class MockGUI implements GUI{
    @Override
    public void setUserInterfaceEventListener(UserInterfaceEventListener listener) {

    }

    @Override
    public void createFrame() {

    }

    @Override
    public void showErrors(List<String> errors) {

    }
}
