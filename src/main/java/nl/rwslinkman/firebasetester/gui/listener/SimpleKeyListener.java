package nl.rwslinkman.firebasetester.gui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Rick Slinkman
 */
public class SimpleKeyListener implements KeyListener
{
    private KeyTypedListener listener;

    public SimpleKeyListener(KeyTypedListener listener) {
        this.listener = listener;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        if(listener != null) {
            listener.onKeyTyped(key);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // NOP
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // NOP
    }
}
