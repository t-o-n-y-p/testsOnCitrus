package demo.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import demo.pojo.CreateUserResponse;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

public class FirstTestCreateUser extends TestNGCitrusTestRunner {

    private TestContext context;
    private String name = "Nick";
    private String job = "Teacher";

    @Test(description = "Create user", enabled = true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.createTestContext();

        http(httpActionBuilder -> httpActionBuilder
            .client("restClientReqres")
            .send()
            .post("users")
            .payload("{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"job\": \"" + job + "\"\n" +
                "}"
            )
        );

        http(httpActionBuilder -> httpActionBuilder
            .client("restClientReqres")
            .receive()
            .response(HttpStatus.CREATED)
            .messageType(MessageType.JSON)
            .payload(getResponseData(name, job), "objectMapper")
            .extractFromPayload("$.id", "currentId")
            .extractFromPayload("$.createdAt", "createdAt")
            .ignore("$.createdAt")
        );

        echo("currentId = ${currentId} and createdAt = ${createdAt}");


    }

    public CreateUserResponse getResponseData(String name, String job) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setName(name);
        createUserResponse.setJob(job);
        createUserResponse.setId("@isNumber()@");
        createUserResponse.setCreatedAt("unknown");
        return createUserResponse;
    }

}
