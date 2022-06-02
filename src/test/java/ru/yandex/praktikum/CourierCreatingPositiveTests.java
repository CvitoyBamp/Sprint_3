package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import ru.yandex.api.CourierClient;
import ru.yandex.model.Courier;
import ru.yandex.model.CourierLogin;

import static org.hamcrest.core.IsEqual.equalTo;

public class CourierCreatingPositiveTests {

    @After
    public void deleteCourier() {
        CourierClient.deleteCourier(courierLoginData, CourierClient.getCourierId(courierLoginData));
    }

    Courier courierWithCorrectParams = new Courier(RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10));
    CourierLogin courierLoginData = new CourierLogin(courierWithCorrectParams.getLogin(), courierWithCorrectParams.getPassword());

    @Test
    @DisplayName("Creating a courier with correct data")
    @Description("Should return HTTP201 if the courier is successfully created")
    public void shouldResponseOkWithCorrectParamsWithinCreating() {
        CourierClient.createCourier(courierWithCorrectParams)
                .then()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Creating a courier with duplicate data")
    @Description("Should return HTTP409 if the courier's data is already exist")
    public void shouldNotCreateCourierWithDuplicateData() {
        CourierClient.createCourier(courierWithCorrectParams);
        CourierClient.createCourier(courierWithCorrectParams)
                .then()
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

}
