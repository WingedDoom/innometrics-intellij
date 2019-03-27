package actions;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.AnimatedIcon;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.fields.ExpandableTextField;
import com.intellij.ui.components.fields.ExtendableTextComponent;
import core.Constants;
import org.jetbrains.annotations.Nullable;
import services.InnometricsCoreService;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class AuthDialogWrapper extends DialogWrapper {
    private JPanel containerPanel;
    private JBLabel statusLabel;
    private ExpandableTextField usernameField;
    private JBPasswordField passwordField;
    private JBTextField urlField;

    public AuthDialogWrapper() {
        super(true);
        init();
        setTitle("Log In to Innometrics");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        // configure text fields
        usernameField = new ExpandableTextField();
        usernameField.setExtensions(Collections.emptyList());
        usernameField.setToolTipText("Innometrics account email");
        usernameField.getEmptyText().setText("Enter username");

        passwordField = new JBPasswordField();
        passwordField.setToolTipText("Innometrics account password");
        passwordField.getEmptyText().setText("Enter password");

        urlField = new JBTextField();
        urlField.setToolTipText("Base URL of your custom Innometrics server");
        urlField.getEmptyText().setText(Constants.DEFAULT_BASE_URL);

        // align fields with labels
        int numberOfRows = 3;
        containerPanel = new JPanel(new BorderLayout());
        JPanel annotatedFieldsPanel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(numberOfRows, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(numberOfRows, 1));

        labelPanel.add(new JLabel("Username", JLabel.LEFT));
        labelPanel.add(new JLabel("Password", JLabel.LEFT));
        labelPanel.add(new JLabel("Custom Base URL", JLabel.LEFT));

        Dimension textFieldSize = new Dimension(300, 30);
        usernameField.setPreferredSize(textFieldSize);
        passwordField.setPreferredSize(textFieldSize);
        urlField.setPreferredSize(textFieldSize);
        fieldPanel.add(usernameField);
        fieldPanel.add(passwordField);
        fieldPanel.add(urlField);

        statusLabel = new JBLabel(" ");

        // compose root container
        annotatedFieldsPanel.add(labelPanel, BorderLayout.WEST);
        annotatedFieldsPanel.add(fieldPanel, BorderLayout.EAST);

        containerPanel.add(annotatedFieldsPanel, BorderLayout.PAGE_START);
        containerPanel.add(statusLabel, BorderLayout.PAGE_END);

        return containerPanel;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        ValidationInfo validationInfo = null;

        // validate that password and username are nonempty
        if (usernameField.getText().isEmpty())
            validationInfo = new ValidationInfo("Username is required", usernameField);
        else if (passwordField.getPassword().length == 0)
            validationInfo = new ValidationInfo("Password is required", passwordField);
        else if (!urlField.getText().isEmpty()) {
            // validate if entered URL is valid one
            try {
                new URL(urlField.getText());
            } catch (MalformedURLException e) {
                validationInfo = new ValidationInfo("Incorrect URL: " + e.getMessage(), urlField);
            }
        }

        return validationInfo;
    }

    private void startLoading() {
        statusLabel.setText("");
        statusLabel.updateUI();

        usernameField.addExtension(ExtendableTextComponent.Extension
                .create(new AnimatedIcon.Default(), null, null));

        usernameField.setEnabled(false);
        passwordField.setEnabled(false);
        urlField.setEnabled(false);
        myOKAction.setEnabled(false);
        myCancelAction.setEnabled(false);
    }

    private void stopLoading(@Nullable String status) {
        usernameField.setEnabled(true);
        passwordField.setEnabled(true);
        urlField.setEnabled(true);
        myOKAction.setEnabled(true);
        myCancelAction.setEnabled(true);

        usernameField.setExtensions(Collections.emptyList());

        if (status != null) {
            statusLabel.setText(status);
            statusLabel.updateUI();
        }
    }

    @Override
    protected void doOKAction() {
        startLoading();

        ApplicationManager.getApplication().invokeLater(() -> {
            InnometricsCoreService coreService = ServiceManager.getService(InnometricsCoreService.class);
            try {
                coreService.login(usernameField.getText(), new String(passwordField.getPassword()), urlField.getText());
                close(OK_EXIT_CODE);
            } catch (Exception e) {
                stopLoading(e.getMessage());
            }
        });
    }
}
