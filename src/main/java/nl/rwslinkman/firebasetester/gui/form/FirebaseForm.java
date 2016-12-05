package nl.rwslinkman.firebasetester.gui.form;

import javax.swing.*;
import java.util.List;

/**
 * @author Rick Slinkman
 */
public abstract class FirebaseForm extends JPanel
{
    public abstract void setupUI();
    public abstract JPanel getGuiPanel();
    public abstract void showErrors(List<String> errors);

    public String getFormName()
    {
        return getClass().getName();
    }
}
