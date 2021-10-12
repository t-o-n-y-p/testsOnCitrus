package demo.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

public class FirstTestGetUser extends TestNGCitrusTestRunner {

    private TestContext context;

    @Test(description = "Get user", enabled = true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        context.setVariable("value", "superValue");
        echo("Property value = " + context.getVariable("value"));
        echo("We have userId = " + context.getVariable("userId"));
        echo("Property userId = " + "${userId}");

        variable("now", "citrus:currentDate()");
        echo("Today is: ${now}");

        http(httpActionBuilder -> httpActionBuilder
            .client("restClientReqres")
            .send()
            .get("users/${userId}")
        );

        http(httpActionBuilder -> httpActionBuilder
            .client("restClientReqres")
            .receive()
            .response(HttpStatus.OK)
            .messageType(MessageType.JSON)
            .payload("{\n"
                + "    \"data\": {\n"
                + "        \"id\": 2,\n"
                + "        \"email\": \"janet.weaver@reqres.in\",\n"
                + "        \"first_name\": \"Janet\",\n"
                + "        \"last_name\": \"Weaver\",\n"
                + "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n"
                + "    },\n"
                + "    \"support\": {\n"
                + "        \"url\": \"https://reqres.in/#support-heading\",\n"
                + "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n"
                + "    }\n"
                + "}\n")
        );

    }

}
