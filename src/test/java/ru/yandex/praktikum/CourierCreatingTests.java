package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Enclosed.class)
public class CourierCreatingTests {

    public static class CourierCreatingTestWithCorrectParams {

        @Before
        public void setUp() {
            RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        }

        @After
        public void deleteCourier() {
            APIFunctions.deleteCourier(courierLoginData, APIFunctions.getCourierId(courierLoginData));
        }

        CourierCreatingConstructor courierWithCorrectParams = new CourierCreatingConstructor(RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10));
        CourierLoginConstructor courierLoginData = new CourierLoginConstructor(courierWithCorrectParams.getLogin(), courierWithCorrectParams.getPassword());

        @Test
        @DisplayName("Creating a courier with correct data")
        @Description("Should return HTTP201 if the courier is successfully created")
        public void shouldResponseOkWithCorrectParamsWithinCreating() {
            APIFunctions.createCourier(courierWithCorrectParams)
                    .then()
                    .assertThat()
                    .statusCode(201)
                    .body("ok", equalTo(true));
        }
    }

    @RunWith(Parameterized.class)
    public static class CourierParametrizedTests {

        @Before
        public void setUp() {
            RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        }

        private final CourierCreatingConstructor courierCreatingConstructor;
        private final int responseCode;
        private final String responseMessage;
        private final String body;


        static CourierCreatingConstructor courierWithoutLogin = new CourierCreatingConstructor(null, RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
        static CourierCreatingConstructor courierWithoutPassword = new CourierCreatingConstructor(RandomStringUtils.randomAlphabetic(5),null, RandomStringUtils.randomAlphabetic(5));
        static CourierCreatingConstructor courierWithoutFirstName = new CourierCreatingConstructor(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), null);

        public CourierParametrizedTests(CourierCreatingConstructor courierCreatingConstructor, int responseCode, String responseMessage, String body) {
            this.courierCreatingConstructor =  courierCreatingConstructor;
            this.responseCode = responseCode;
            this.responseMessage = responseMessage;
            this.body = body;
        }

        @Parameterized.Parameters
        public static Object[][] getTestData() {
            return new Object[][]{
                    {courierWithoutLogin, 400, "Недостаточно данных для создания учетной записи", "message"},
                    {courierWithoutPassword, 400, "Недостаточно данных для создания учетной записи", "message"},
                    {courierWithoutFirstName, 201, "true", "ok"},
                    {courierWithoutFirstName, 409, "Этот логин уже используется", "message"}
            };
        }


        @Test
        @DisplayName("Creating a courier with incomplete data")
        @Description("Should return HTTP400/409 when creating a courier with incomplete or duplicate data")
        public void shouldResponseBadRequestWithPartialParamsWithinCreating() {
            APIFunctions.createCourier(courierCreatingConstructor)
                    .then()
                    .assertThat()
                    .statusCode(responseCode)
                    .body(body, equalTo(String.valueOf(responseMessage)));
        }
    }
}
