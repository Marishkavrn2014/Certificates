
import ms.certificates.creator.CertificateCreator;
import ms.certificates.data.FieldData;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CertificateCreatorTest {

    static CertificateCreator certificateCreator = new CertificateCreator();

    static FieldData fieldData = new FieldData();
    static String courseName = "CourseName";
    static String firstName = "FirstName";
    static String lastName = "LastName";
    static String level = "Level";
    static String hours = "100";
    static String from = "2020";
    static String to = "2021";

    @BeforeClass
    public static void creator() {

        fieldData.setTo(to);
        fieldData.setLevel(level);
        fieldData.setLastName(lastName);
        fieldData.setHours(hours);
        fieldData.setFirstName(firstName);
        fieldData.setCourseName(courseName);
        fieldData.setFrom(from);

    }

    @Test
    public void generateNumber() {
        String expectedNumber = "10000001";
        certificateCreator.id = 1;
        Assert.assertEquals(expectedNumber, certificateCreator.generateNumber());
    }

    @Test
    public void createDoc() {
        certificateCreator.createDoc(fieldData);
        String fileName = System.getProperty("user.dir") + "/certificates/" + firstName + lastName + level + certificateCreator.id + ".pdf";
        File file = new File(fileName);
//        Assert.assertTrue(file.exists());
    }

    @Test
    @Ignore
    public void writeLogs() throws IOException {

        String filename = "." + File.separator + firstName + lastName + level + certificateCreator.id + ".pdf";
        certificateCreator.writeLogs(filename, fieldData);
        final File logs = new File(System.getProperty("user.dir") + File.separator + "log" + File.separator + "logs.txt");
        List<String> logsList = new LinkedList<>();

        // TODO  проверить что файл существует и только после этого читать
        // TODO  обернуть в try with resource
        FileReader fr = new FileReader(logs);
        BufferedReader bf = new BufferedReader(fr);
        String line;
        while ((line = bf.readLine()) != null) {
            logsList.add(line);
        }
        boolean resultFileName = false;
        boolean resultRow = false;
        String expRow = certificateCreator.id + " FirstName: " + firstName + " Last Name: " + lastName +
                " Level: " + level + " course Name: " + courseName + " From - to: " + from + " - " + to;
        for (String s : logsList) {
            if (s.contains(filename)) {
                resultFileName = true;
            }
            if (s.contains(expRow)) {
                resultRow = true;
            }
        }
        Assert.assertTrue(resultFileName && resultRow);
    }
}