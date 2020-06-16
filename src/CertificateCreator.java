//This class creating a document in PDF
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.*;
import java.util.Date;

public class CertificateCreator implements Serializable{
    //it is synchronized so that when the program is opened and each document is generated, the document numbering continues from the previous one
    private int id;
    private static String prefix = "10";
    //generating a number consisting of a prefix and id
    private String generateNumber(){
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        int count = 10;
        for(int i = 1; i < 7; i++){
            if(id / count >= 1){
                count = count * 10;
            } else {
                for(int j = 0; j < (7 - i); j++){
                    sb.append("0");
                }
                break;
            }
        }
        sb.append(id);
        return sb.toString();
    }
    //this method puts the image in a pdf page
    public static void addImageToPage(PDDocument documentOut, float x, float y, String imagePath, PDPageContentStream contentStream)
            throws IOException {
        PDImageXObject ximage = PDImageXObject.createFromFile(imagePath, documentOut);
        contentStream.drawImage(ximage, x, y, 800, 600);
    }
    //create a journal document, print the text on the page, write the history of the creation of certificates
    public boolean createDoc(String[] s) {
        boolean result = false;
        id++;
        String num = generateNumber();
        try {
            System.setErr(new PrintStream(new File(System.getProperty("user.dir")+"/log/log.txt")));
            PDDocument document = new PDDocument();
            float POINTS_PER_INCH = 72;
            float POINTS_PER_MM = 1 / (10 * 2.54f) * POINTS_PER_INCH;
            PDPage page = new PDPage(new PDRectangle(297 * POINTS_PER_MM, 210 * POINTS_PER_MM));
            document.addPage(page);
            PDFont font = PDType1Font.HELVETICA_BOLD;
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            String path = System.getProperty("user.dir") + "/Cert.jpg";
            addImageToPage(document, 25, -10, path, contentStream);
            contentStream.beginText();
            contentStream.setFont(font, 24);
            contentStream.newLineAtOffset(370, 395);
            contentStream.showText(s[0] + " ");
            contentStream.showText(s[1]);
            contentStream.setFont(font, 20);
            contentStream.newLineAtOffset(-40, -160);
            contentStream.showText(s[2]);
            contentStream.setFont(font, 18);
            contentStream.newLineAtOffset(60, 105);
            contentStream.showText(s[3]);
            contentStream.newLineAtOffset(-20, -30);
            contentStream.showText(s[4]);
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText(s[5]);
            contentStream.setFont(font, 20);
            contentStream.newLineAtOffset(-280, 32);
            contentStream.showText(s[6]);
            contentStream.newLineAtOffset(500, 200);
            contentStream.showText(num);
            contentStream.endText();
            contentStream.close();
            PDDocument appendix1 = PDDocument.load(new File(System.getProperty("user.dir")+"/RQCR.pdf"));
            for(int i = 0; i < appendix1.getNumberOfPages(); i++){
                document.addPage(appendix1.getPage(i));
            }
            PDDocument appendix2 = PDDocument.load(new File(System.getProperty("user.dir")+"/RQCE.pdf"));
            for(int i = 0; i < appendix2.getNumberOfPages(); i++){
                document.addPage(appendix2.getPage(i));
            }
            StringBuilder sb = new StringBuilder();
            String pathDocs = System.getProperty("user.dir") + "/certificates/";
            sb.append(pathDocs);
            sb.append(s[0]);
            sb.append(s[1]);
            sb.append(s[2]);
            sb.append(id);
            sb.append(".pdf");
            document.save(new File(sb.toString()));
            document.close();
            writeLogs(sb.toString(), s);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //certificate creation history is required to control issued certificates
    public void writeLogs(String fileName, String[] data) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(new File(System.getProperty("user.dir")+"/log/logs.txt"), true));
        Date date = new Date();
        out.write("\n" + fileName + " \n");
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ");
        sb.append("FirstName: ");
        sb.append(data[0]);
        sb.append(" ");
        sb.append("Last Name: ");
        sb.append(data[1]);
        sb.append(" ");
        sb.append("Level: ");
        sb.append(data[2]);
        sb.append(" ");
        sb.append("course Name: ");
        sb.append(data[6]);
        sb.append(" ");
        sb.append("From - to: ");
        sb.append(data[4]);
        sb.append(" - ");
        sb.append(data[5]);
        sb.append("\n");
        sb.append(date);
        out.write(sb.toString());
        out.close();
    }

}