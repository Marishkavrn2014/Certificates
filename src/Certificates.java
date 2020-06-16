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
import javax.swing.*;
import java.awt.event.*;
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
    String firstName;
    String lastName;
    String level;
    String courseName;
    String hours;
    String from;
    String to;


    public Certificates(){
        setContentPane(rootPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(550,390);
        //when you click the create button, it starts checking the input and then creates a document
        ookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //create InputStream for synchronising id of certificate
                ObjectInputStream in;
                CertificateCreator cc = new CertificateCreator();
                try {
                    in = new ObjectInputStream(new FileInputStream(System.getProperty("user.dir")+"/log/Creator.out"));
                    cc = (CertificateCreator) in.readObject();
                    in.close();
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
                //initializing fields
                firstName = textField1.getText();
                lastName  = textField3.getText();
                hours = textField2.getText();
                from = comboBox2.getSelectedItem().toString();
                to = comboBox3.getSelectedItem().toString();
                //if the user needs to write another name
                if(comboBox4.getSelectedItem().toString().equals("Manual input")){
                    courseName = textField6.getText();
                } else {
                    courseName = comboBox4.getSelectedItem().toString();
                }
                if(comboBox1.getSelectedItem().toString().equals("Manual input")){
                    level = textField5.getText();
                } else {
                    level = comboBox1.getSelectedItem().toString();
                }
                //if input is ok, starts to create a document and write a copy in Output stream
                if(checkText(firstName) && checkText(lastName) && checkText(lastName) && checkNull(courseName) && checkNumbers(hours) && checkNull(level)){
                    String[] s1 = {firstName, lastName, level, hours, from, to, courseName};
                    try {
                        if(cc.createDoc(s1)) {
                            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir")+"/log/Creator.out"));
                            SuccessfulWindow sw = new SuccessfulWindow();
                            o.writeObject(cc);
                            o.flush();
                            o.close();
                        }
                        } catch (IOException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                        }

                }
            }
        });
    }

    public static void main(String[] args) {
       SwingUtilities.invokeLater(
               new Runnable() {
                   @Override
                   public void run() {
                       new Certificates();
                   }
               }
       );
    }
    //checking that the name is correct and not empty
    boolean checkText(String text){
        if(!text.equals("")) {
            for (char c : text.toCharArray()) {
                if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c == 45) {
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
            if(c >= 48 && c <= 57){
                continue;
            } else {
                message.setText("Hours must contain only digits!");
                return false;
            }
        }
        return true;
    }
    //checking that the "Course name" field is not empty
    boolean checkNull(String text) {
        if(text.equals("")){
            message.setText("Please check the name of the course and level!");
            return false;
        }
        return true;
    }


}

