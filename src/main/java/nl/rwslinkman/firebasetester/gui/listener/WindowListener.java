package nl.rwslinkman.firebasetester.gui.listener;

import java.awt.event.WindowEvent;

/**
 * @author Rick Slinkman
 */
public class WindowListener implements java.awt.event.WindowListener
{
    private UserInterfaceEventListener eventListener;

    public WindowListener(UserInterfaceEventListener listener) {
        if(listener == null) {
            throw new IllegalArgumentException("Event listener cannot be null");
        }
        this.eventListener = listener;
    }

    public void windowClosing(WindowEvent e) {
        this.eventListener.onExit();
    }

    public void windowOpened(WindowEvent e) {
        // NOP
    }

    public void windowClosed(WindowEvent e) {
        // NOP
    }

    public void windowIconified(WindowEvent e) {
        // NOP
    }

    public void windowDeiconified(WindowEvent e) {
        // NOP
    }

    public void windowActivated(WindowEvent e) {
        // NOP
    }

    public void windowDeactivated(WindowEvent e) {
        // NOP
    }
}
