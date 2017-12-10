import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
//import static org.hamcrest.Matchers.*;
import static io.restassured.path.xml.XmlPath.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class XmlPathTest {
    @BeforeClass
    public static void beforeAll(){
        useRelaxedHTTPSValidation();
    }
    @Test
    public void xmlPathTest1(){
        String XML="<shopping>\n" +
                " <category type=\"groceries\">\n" +
                " <item>\n" +
                " <name>Chocolate</name>\n" +
                " <price>10</price>\n" +
                " </item>\n" +
                " <item>\n" +
                " <name>Coffee</name>\n" +
                " <price>20</price>\n" +
                " </item>\n" +
                " </category>\n" +
                " <category type=\"supplies\">\n" +
                " <item>\n" +
                " <name>Paper</name>\n" +
                " <price>5</price>\n" +
                " </item>\n" +
                " <item quantity=\"4\">\n" +
                " <name>Pens</name>\n" +
                " <price>15</price>\n" +
                " </item>\n" +
                " </category>\n" +
                " <category type=\"present\">\n" +
                " <item when=\"Aug 10\">\n" +
                " <name>Kathryn's Birthday</name>\n" +
                " <price>200</price>\n" +
                " </item>\n" +
                " </category>\n" +
                " </shopping>";


        System.out.println(with(XML).get("**.find{ it.name!=''}.name").toString());
        String name = with(XML).get("shopping.category.item[0].name");
        assertThat("name diff", name, equalTo("Chocolate2"));

        int priceOfChocolate = with(XML).getInt("**.find { it.name == 'Chocolate' }.price");
        System.out.println(priceOfChocolate);
        assertThat("int", priceOfChocolate, is(11));




    }
}
