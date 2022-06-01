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

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Enclosed.class)
public class CourierLoginTests {

    public static class CourierLoginTestWithCorrectParams {

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
        @DisplayName("Courier login in the system with the correct data")
        @Description("Should return HTTP200 and id on successful authorization")
        public void shouldResponseOkWithCorrectParamsWithinLogin() {
            APIFunctions.createCourier(courierWithCorrectParams);
            APIFunctions.loginCourier(courierLoginData)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body("id", notNullValue());
        }
    }

    @RunWith(Parameterized.class)
    public static class CourierLoginParametrizedTests {

        @Before
        public void setUp() {
            RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        }

        private final CourierLoginConstructor courierLoginConstructor;
        private final int responseCode;
        private final String responseMessage;

        static CourierCreatingConstructor courierWithCorrectParams = new CourierCreatingConstructor(RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10));
        static CourierLoginConstructor courierWithoutLogin = new CourierLoginConstructor(null, courierWithCorrectParams.getPassword());
        static CourierLoginConstructor courierWithoutPassword = new CourierLoginConstructor(courierWithCorrectParams.getLogin(),null);
        static CourierLoginConstructor courierWithNonExistentData = new CourierLoginConstructor(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));

        public CourierLoginParametrizedTests(CourierLoginConstructor courierLoginConstructor, int responseCode, String responseMessage) {
            this.courierLoginConstructor =  courierLoginConstructor;
            this.responseCode = responseCode;
            this.responseMessage = responseMessage;
        }

        @Parameterized.Parameters
        public static Object[][] getTestData() {
            return new Object[][]{
                    {courierWithoutLogin, 400, "Недостаточно данных для входа"},
                    {courierWithoutPassword, 400, "Недостаточно данных для входа"},
                    {courierWithNonExistentData, 404, "Учетная запись не найдена"},
            };
        }


        @Test
        @DisplayName("Courier login with incomplete and non-existent data")
        @Description("Should return HTTP400/404 when authorizing courier with incomplete or duplicate data")
        public void shouldResponseBadRequestWithPartialParamsWithinLogin() {
            APIFunctions.createCourier(courierWithCorrectParams);
            APIFunctions.loginCourier(courierLoginConstructor)
                    .then()
                    .assertThat()
                    .statusCode(responseCode)
                    .body("message", equalTo(responseMessage));
        }
    }
}
