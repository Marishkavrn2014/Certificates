/* Copyright 2020 Marina Shadrina
shadrina_ma@scvrn.ru

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


This program allows you to create a PDF file of a certificate of completion
of a language school indicating the student’s name, surname, level of study,
course name, number of school hours and year of study period.
The program works with English. The certificate itself is attached by the
user to the directory structure of the program and should be called Cert.jpg.
 */
package ms.certificates.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ms.certificates.creator.CertificateCreator;
import ms.certificates.data.FieldData;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

//create UI
public class Certificates extends JFrame {
    private JPanel rootPanel;
    private JButton ookButton;
    private JTextField textField1;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JTextField textField2;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JLabel message;
    private JTextField textField5;
    private JTextField textField6;
    private JComboBox comboBox4;
    private String firstName;
    private String lastName;
    private String level;
    private String courseName;
    private String hours;
    private String from;
    private String to;


    public Certificates() {
        setContentPane(rootPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(550, 390);
        //when you click the create button, it starts checking the input and then creates a document
        ookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //create InputStream for synchronising id of certificate
                CertificateCreator certificateCreator = new CertificateCreator();
                final File previous = new File(System.getProperty("user.dir") + File.separator + "log" + File.separator + "Creator.out");
                if (previous.exists()) {
                    try (final ObjectInputStream in = new ObjectInputStream(
                            new FileInputStream(previous))) {
                        certificateCreator = (CertificateCreator) in.readObject();
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                }

                //initializing fields
                firstName = textField1.getText();
                lastName = textField3.getText();
                hours = textField2.getText();
                from = comboBox2.getSelectedItem().toString();
                to = comboBox3.getSelectedItem().toString();
                //if the user needs to write another name
                if ("Manual input".equals(comboBox4.getSelectedItem().toString())) {
                    courseName = textField6.getText();
                } else {
                    courseName = comboBox4.getSelectedItem().toString();
                }
                if ("Manual input".equals(comboBox1.getSelectedItem().toString())) {
                    level = textField5.getText();
                } else {
                    level = comboBox1.getSelectedItem().toString();
                }
                //if input is ok, starts to create a document and write a copy in Output stream
                if (checkText(firstName) && checkText(lastName) && checkEmpty(hours) && checkEmpty(courseName) && checkNumbers(hours) && checkEmpty(level)) {

                    // set fields to FieldData
                    FieldData fieldData = new FieldData();
                    fieldData.setCourseName(courseName);
                    fieldData.setFirstName(firstName);
                    fieldData.setLastName(lastName);
                    fieldData.setFrom(from);
                    fieldData.setTo(to);
                    fieldData.setHours(hours);
                    fieldData.setLevel(level);

                    // create a document, check that the method return true
                    if (certificateCreator.createDoc(fieldData)) {
                        try (final ObjectOutputStream o = new ObjectOutputStream(
                                new FileOutputStream(System.getProperty("user.dir") + File.separator + "log" + File.separator + "Creator.out"))) {

                            //show confirmation window
                            SuccessfulWindow sw = new SuccessfulWindow();
                            message.setText("Certificate has been saved!");

                            //synchronizing object version
                            o.writeObject(certificateCreator);
                            o.flush();

                        } catch (IOException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();

                        }
                    }
                }
            }
        });
    }


    //checking that the name is correct and not empty
    boolean checkText(String text) {
        if (!text.isEmpty()) {
            for (char c : text.toCharArray()) {
                if (Character.toString(c).matches("[a-zA-Z\\-]")) {
                    continue;
                } else {
                    message.setText("The name must contain only characters!");
                    return false;
                }
            }
        } else {
            message.setText("Field Name is empty!");
            return false;
        }
        return true;
    }

    //checking that the "hours" field contain only digits
    boolean checkNumbers(String text) {
        for (char c : text.toCharArray()) {
            if (Character.toString(c).matches("[0-9]")) {
                continue;
            } else {
                message.setText("Hours must contain only digits!");
                return false;
            }
        }
        return true;
    }

    //checking that the "Course name" field is not empty
    boolean checkEmpty(String text) {
        if (text.isEmpty()) {
            message.setText("Please check the name of the course, hours and level!");
            return false;
        }
        return true;
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(14, 6, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4511972)), "Certificates", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        textField1 = new JTextField();
        rootPanel.add(textField1, new GridConstraints(0, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField3 = new JTextField();
        rootPanel.add(textField3, new GridConstraints(1, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("First Name");
        rootPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 18), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Last Name");
        rootPanel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 18), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Certificate");
        rootPanel.add(label3, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 18), null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Manual input");
        defaultComboBoxModel1.addElement("Pre-Starters");
        defaultComboBoxModel1.addElement("Starters");
        defaultComboBoxModel1.addElement("Pre-Movers");
        defaultComboBoxModel1.addElement("Movers");
        defaultComboBoxModel1.addElement("Pre-Flyers");
        defaultComboBoxModel1.addElement("Flyers");
        defaultComboBoxModel1.addElement("Starter");
        defaultComboBoxModel1.addElement("Elementary");
        defaultComboBoxModel1.addElement("Pre-Intermediate");
        defaultComboBoxModel1.addElement("Intermediate");
        defaultComboBoxModel1.addElement("Upper-Intermediate");
        defaultComboBoxModel1.addElement("Advanced");
        defaultComboBoxModel1.addElement("Proficiency");
        defaultComboBoxModel1.addElement("Low-Intermediate");
        defaultComboBoxModel1.addElement("Pre-Advanced");
        defaultComboBoxModel1.addElement("Mid-Intermediate");
        defaultComboBoxModel1.addElement("A1");
        defaultComboBoxModel1.addElement("A1+");
        defaultComboBoxModel1.addElement("A2");
        defaultComboBoxModel1.addElement("A2+");
        defaultComboBoxModel1.addElement("B1");
        defaultComboBoxModel1.addElement("B1+");
        defaultComboBoxModel1.addElement("B2");
        defaultComboBoxModel1.addElement("B2+");
        defaultComboBoxModel1.addElement("C1");
        defaultComboBoxModel1.addElement("C2");
        comboBox1.setModel(defaultComboBoxModel1);
        rootPanel.add(comboBox1, new GridConstraints(7, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ookButton = new JButton();
        ookButton.setText("Generate");
        rootPanel.add(ookButton, new GridConstraints(12, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Course Name");
        rootPanel.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 18), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("From");
        rootPanel.add(label5, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 18), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("To");
        rootPanel.add(label6, new GridConstraints(9, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("2019");
        defaultComboBoxModel2.addElement("2020");
        defaultComboBoxModel2.addElement("2021");
        defaultComboBoxModel2.addElement("2022");
        defaultComboBoxModel2.addElement("2023");
        defaultComboBoxModel2.addElement("2024");
        defaultComboBoxModel2.addElement("2025");
        defaultComboBoxModel2.addElement("2026");
        defaultComboBoxModel2.addElement("2027");
        defaultComboBoxModel2.addElement("2028");
        defaultComboBoxModel2.addElement("2029");
        comboBox2.setModel(defaultComboBoxModel2);
        rootPanel.add(comboBox2, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(125, 30), null, 0, false));
        comboBox3 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("2019");
        defaultComboBoxModel3.addElement("2020");
        defaultComboBoxModel3.addElement("2021");
        defaultComboBoxModel3.addElement("2022");
        defaultComboBoxModel3.addElement("2023");
        defaultComboBoxModel3.addElement("2024");
        defaultComboBoxModel3.addElement("2025");
        defaultComboBoxModel3.addElement("2026");
        defaultComboBoxModel3.addElement("2027");
        defaultComboBoxModel3.addElement("2028");
        defaultComboBoxModel3.addElement("2029");
        comboBox3.setModel(defaultComboBoxModel3);
        rootPanel.add(comboBox3, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(135, 30), null, 0, false));
        final Spacer spacer1 = new Spacer();
        rootPanel.add(spacer1, new GridConstraints(13, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        message = new JLabel();
        message.setText("Create cetrificate:");
        rootPanel.add(message, new GridConstraints(11, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        rootPanel.add(spacer2, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textField5 = new JTextField();
        rootPanel.add(textField5, new GridConstraints(8, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField6 = new JTextField();
        rootPanel.add(textField6, new GridConstraints(4, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        comboBox4 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Manual input");
        defaultComboBoxModel4.addElement("General English");
        defaultComboBoxModel4.addElement("Deutsch");
        defaultComboBoxModel4.addElement("Español");
        defaultComboBoxModel4.addElement("Italiano");
        defaultComboBoxModel4.addElement("Business English");
        comboBox4.setModel(defaultComboBoxModel4);
        rootPanel.add(comboBox4, new GridConstraints(3, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Hour's");
        rootPanel.add(label7, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 18), null, 0, false));
        textField2 = new JTextField();
        rootPanel.add(textField2, new GridConstraints(5, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText(" If you select \"Manual input\", write in the field under this name");
        rootPanel.add(label8, new GridConstraints(2, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText(" If you select \"Manual input\", write in the field under this name");
        rootPanel.add(label9, new GridConstraints(6, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}

