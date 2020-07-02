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
package ms.certificates;

import ms.certificates.view.Certificates;

import javax.swing.*;

public class RunCertificates {
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
}
