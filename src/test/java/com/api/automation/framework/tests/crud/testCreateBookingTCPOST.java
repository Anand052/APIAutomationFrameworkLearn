package com.api.automation.framework.tests.crud;

import com.api.automation.framework.base.BaseTest;
import com.api.automation.framework.endpoints.APIConstants;
import com.api.automation.framework.pojos.BookingResponse;
import com.api.automation.framework.utils.PropertyReader;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;


public class testCreateBookingTCPOST extends BaseTest {
    @Link(name = "Link to TC", url = "https://bugz.atlassian.net/browse/RBT-4")
    @Issue("JIRA_RBT-4")
    @TmsLink("RBT-4")
    @Owner("Promode")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that POST request is working fine.")
    @Test
    public void testVerifyCreateBookingPOST01() {
        requestSpecification
                .basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString()).post();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(Integer.parseInt(PropertyReader.readKey("booking.post.statuscode.success")));


        //Default Rest Assured
        validatableResponse.body("booking.firstname", Matchers.equalTo(PropertyReader.readKey("booking.post.firstname")));


        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // AssertJ
        assertThat(bookingResponse.getBookingid()).isNotNull();
        assertThat(bookingResponse.getBooking().getFirstname()).isNotNull().isNotBlank();
        assertThat(bookingResponse.getBooking().getFirstname()).isEqualTo(PropertyReader.readKey("booking.post.firstname"));

        // TestNG Assertions
        assertActions.verifyStatusCode(response,200);
    }
}
