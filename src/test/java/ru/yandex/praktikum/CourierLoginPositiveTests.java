package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import ru.yandex.api.CourierClient;
import ru.yandex.model.Courier;
import ru.yandex.model.CourierLogin;

import static org.hamcrest.core.IsNull.notNullValue;

public class CourierLoginPositiveTests {

    @After
    public void deleteCourier() {
        CourierClient.deleteCourier(courierLoginData, CourierClient.getCourierId(courierLoginData));
    }

    Courier courierWithCorrectParams = new Courier(RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10));
    CourierLogin courierLoginData = new CourierLogin(courierWithCorrectParams.getLogin(), courierWithCorrectParams.getPassword());

    @Test
    @DisplayName("Courier login in the system with the correct data")
    @Description("Should return HTTP200 and his id on successful authorization")
    public void shouldResponseOkWithCorrectParams() {
        CourierClient.createCourier(courierWithCorrectParams);
        CourierClient.loginCourier(courierLoginData)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }
}

