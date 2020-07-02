import ms.certificates.data.FieldData;
import org.junit.Assert;
import org.junit.Test;

public class FieldDataTest {

    FieldData fieldData = new FieldData();
    String courseName = "CourseName";
    String firstName = "FirstName";
    String lastName = "LastName";
    String level = "Level";
    String hours = "100";
    String from = "2020";
    String to = "2021";


    @Test
    public void setCourseName() {
        fieldData.setCourseName(courseName);
        Assert.assertEquals(courseName, fieldData.getCourseName());

    }

    @Test
    public void setFirstName() {
        fieldData.setFirstName(firstName);
        Assert.assertEquals(firstName, fieldData.getFirstName());
    }

    @Test
    public void setFrom() {
        fieldData.setFrom(from);
        Assert.assertEquals(from, fieldData.getFrom());
    }

    @Test
    public void setHours() {
        fieldData.setHours(hours);
        Assert.assertEquals(hours, fieldData.getHours());
    }

    @Test
    public void setLastName() {
        fieldData.setLastName(lastName);
        Assert.assertEquals(lastName, fieldData.getLastName());
    }

    @Test
    public void setLevel() {
        fieldData.setLevel(level);
        Assert.assertEquals(level, fieldData.getLevel());
    }

    @Test
    public void setTo() {
        fieldData.setTo(to);
        Assert.assertEquals(to, fieldData.getTo());
    }


}