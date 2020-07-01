package main.java.ms.certificates.view;//this window requires the user to verify that the certificate is created
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SuccessfulWindow extends JDialog {
    private JPanel contentPane;
    private JButton okButton;
    private JLabel message;

    public SuccessfulWindow() {
        setContentPane(contentPane);
        setVisible(true);
        setSize(200, 200);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }

}
