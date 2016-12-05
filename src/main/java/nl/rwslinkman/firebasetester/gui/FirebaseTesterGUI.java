package nl.rwslinkman.firebasetester.gui;

import nl.rwslinkman.firebasetester.FirebaseTester;
import nl.rwslinkman.firebasetester.gui.form.FirebaseForm;
import nl.rwslinkman.firebasetester.gui.form.FirebaseTesterBuilderGUI;
import nl.rwslinkman.firebasetester.gui.form.FirebaseTesterRawGUI;
import nl.rwslinkman.firebasetester.gui.form.FormInteractionListener;
import nl.rwslinkman.firebasetester.gui.listener.UserInterfaceEventListener;
import nl.rwslinkman.firebasetester.gui.listener.WindowListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick Slinkman
 */
public class FirebaseTesterGUI implements GUI, FormInteractionListener {
    private UserInterfaceEventListener eventListener;
    private WindowListener windowListener;

    private FirebaseForm mCurrentForm;
    private List<FirebaseForm> mAllForms;
    private JPanel mSwapPanel;
    private JFrame mFrame;

    public FirebaseTesterGUI()
    {
        this.mCurrentForm = new FirebaseTesterRawGUI(this);
        mAllForms = new ArrayList<>();
        mAllForms.add(new FirebaseTesterBuilderGUI(this));
        mAllForms.add(new FirebaseTesterRawGUI(this));
    }

    @Override
    public void createFrame()
    {
        mSwapPanel = new JPanel(new CardLayout());
        for(FirebaseForm form : mAllForms)
        {
            form.setupUI();
            mSwapPanel.add(form.getGuiPanel(), form.getClass().getName());
        }
        mFrame = new JFrame("Firebase Tester v " + FirebaseTester.VERSION_NAME);
        mFrame.getContentPane().add(mSwapPanel);
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        if (this.windowListener != null) {
            mFrame.addWindowListener(this.windowListener);
        }
        mFrame.pack();
        mFrame.setSize(new Dimension(750, 500));
        mFrame.setLocationRelativeTo(null);
        // Show frame
        mFrame.setVisible(true);
    }


    @Override
    public void setUserInterfaceEventListener(UserInterfaceEventListener listener) {
        eventListener = listener;
        if (this.eventListener != null) {
            this.windowListener = new WindowListener(this.eventListener);
        }
    }

    @Override
    public void showErrors(List<String> errors)
    {
        mCurrentForm.showErrors(errors);
    }

    @Override
    public void onFormSubmitted(String apiKey, String body)
    {
        this.eventListener.onFirebaseTestSubmitted(apiKey, body);
    }

    @Override
    public void changeFormPanel(String panelName)
    {
        ((CardLayout)mSwapPanel.getLayout()).show(mSwapPanel, panelName);
//        mFrame.getContentPane().get();
//        mFrame.getContentPane().invalidate();
//
//        mFrame.getContentPane().add(panel);
//        mFrame.getContentPane().revalidate();

//        mCurrentForm = panel;
    }
}
