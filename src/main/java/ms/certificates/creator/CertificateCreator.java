//This class creating a document in PDF
package ms.certificates.creator;

import ms.certificates.data.FieldData;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.Date;

public class CertificateCreator implements Serializable {
    //it is synchronized so that when the program is opened and each document is generated, the document numbering continues from the previous one
    public int id;
    private final static String prefix = "10";

    //generating a number consisting of a prefix and id
    public String generateNumber() {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        for (int i = 1; i < 7 - String.valueOf(id).length(); i++) {
            sb.append("0");
        }
        sb.append(id);
        return sb.toString();
    }

    //this method puts the image in a pdf page
    public static void addImageToPage(PDDocument documentOut, float x, float y, String imagePath, PDPageContentStream contentStream)
            throws IOException {
        PDImageXObject xImage = PDImageXObject.createFromFile(imagePath, documentOut);
        contentStream.drawImage(xImage, x, y, 800, 600);
    }

    //create a journal document, print the text on the page, write the history of the creation of main.certificates
    public boolean createDoc(FieldData fields) {
        //if ok, return true
        boolean result = false;

        //for unique id number
        id++;

        // get the full 8 - digits number with a prefix
        String num = generateNumber();

        // get all data fields for document
        final String firstName, lastName, level, hours, from, to, courseName;
        firstName = fields.getFirstName();
        lastName = fields.getLastName();
        level = fields.getLevel();
        hours = fields.getHours();
        from = fields.getFrom();
        to = fields.getTo();
        courseName = fields.getCourseName();

        //cross platform separator
        final String sDirSeparator = System.getProperty("file.separator");

        //open a document, set fonts, sizes, add an image and all PDF pages
        try {
            // write error log in /log/log.txt
            final File logFile = new File("." + sDirSeparator + "log" + sDirSeparator + "log.txt");
            if(!logFile.exists()) {
                Paths.get(logFile.getParent()).toFile().mkdirs();
            }
            System.setErr(new PrintStream(logFile));

            //create new PDF document
            final PDDocument document = new PDDocument();

            //set font, page size
            float POINTS_PER_INCH = 72;
            float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;
            PDPage page = new PDPage(new PDRectangle(297 * POINTS_PER_MM, 210 * POINTS_PER_MM));
            document.addPage(page);
            PDFont font = PDType1Font.HELVETICA_BOLD;
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            //create image path and convert image to PDF
            String path = "." + sDirSeparator + "Cert.jpg";
            File image =new File(path);
            if(!image.exists()) {
                JOptionPane.showMessageDialog(null, "Certificate file not found! Please add the file \"Cert.jpg\" to the current directory.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            addImageToPage(document, 25, -10, path, contentStream);

            //write all the text in the certificate
            contentStream.beginText();
            contentStream.setFont(font, 24);
            contentStream.newLineAtOffset(370, 395);
            contentStream.showText(firstName + " ");
            contentStream.showText(lastName);
            contentStream.setFont(font, 20);
            contentStream.newLineAtOffset(-40, -160);
            contentStream.showText(level);
            contentStream.setFont(font, 18);
            contentStream.newLineAtOffset(60, 105);
            contentStream.showText(hours);
            contentStream.newLineAtOffset(-20, -30);
            contentStream.showText(from);
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText(to);
            contentStream.setFont(font, 20);
            contentStream.newLineAtOffset(-280, 32);
            contentStream.showText(courseName);
            contentStream.newLineAtOffset(500, 200);
            contentStream.showText(num);
            contentStream.endText();
            contentStream.close();

            //add part of certificate
            File appFile1 = new File("." + sDirSeparator + "RQCR.pdf");
            if(!image.exists()) {
                JOptionPane.showMessageDialog(null, "file with attachment to certificate not found! Please add the file \"RQCR.pdf\" to the current directory.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            PDDocument appendix1 = PDDocument.load(appFile1);
            for (int i = 0; i < appendix1.getNumberOfPages(); i++) {
                document.addPage(appendix1.getPage(i));
            }
            File appFile2 = new File("." + sDirSeparator + "RQCE.pdf");
            if(!image.exists()) {
                JOptionPane.showMessageDialog(null, "file with attachment to certificate not found! Please add the file \"RQCE.pdf\" to the current directory.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            PDDocument appendix2 = PDDocument.load(appFile2);
            for (int i = 0; i < appendix2.getNumberOfPages(); i++) {
                document.addPage(appendix2.getPage(i));
            }

            //create a certificate name and save
            StringBuilder sb = new StringBuilder();
            String pathDocs = "." + sDirSeparator + "certificates" + sDirSeparator;
            Paths.get(pathDocs).toFile().mkdirs();
            sb.append(pathDocs);
            sb.append(firstName);
            sb.append(lastName);
            sb.append(level);
            sb.append(id);
            sb.append(".pdf");
            final File resultFile = new File(sb.toString());
            document.save(resultFile);
            document.close();

            //write logs for reporting
            writeLogs(sb.toString(), fields);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //certificate creation history is required to control issued main.certificates
    public void writeLogs(String fileName, FieldData fieldData){
        try(PrintWriter out = new PrintWriter(new FileWriter(
                new File("." + File.separator + "log" + File.separator + "logs.txt"), true))) {
            Date date = new Date();
            out.write("\n" + fileName + " \n");
            StringBuilder sb = new StringBuilder();
            sb.append(id).append(" ");
            sb.append("FirstName: ");
            sb.append(fieldData.getFirstName());
            sb.append(" ");
            sb.append("Last Name: ");
            sb.append(fieldData.getLastName());
            sb.append(" ");
            sb.append("Level: ");
            sb.append(fieldData.getLevel());
            sb.append(" ");
            sb.append("course Name: ");
            sb.append(fieldData.getCourseName());
            sb.append(" ");
            sb.append("From - to: ");
            sb.append(fieldData.getFrom());
            sb.append(" - ");
            sb.append(fieldData.getTo());
            sb.append("\n");
            sb.append(date);
            out.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}