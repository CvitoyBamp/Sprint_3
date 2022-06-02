package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.yandex.api.CourierClient;
import ru.yandex.model.Courier;
import ru.yandex.model.CourierLogin;

import static org.hamcrest.core.IsEqual.equalTo;

public class CourierLoginNegativeTests {

    public static Courier courierWithCorrectParams = new Courier(RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10),RandomStringUtils.randomAlphabetic(10));
    public static CourierLogin courierWithoutLogin = new CourierLogin(null, courierWithCorrectParams.getPassword());
    public static CourierLogin courierWithoutPassword = new CourierLogin(courierWithCorrectParams.getLogin(),null);
    public static CourierLogin courierWithNonExistentData = new CourierLogin(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));

    @Test
    @DisplayName("Courier authorizing without login")
    @Description("Should return HTTP400 when authorizing courier without login")
    public void shouldResponseBadRequestWhileAuthorizingWithoutLogin() {
        CourierClient.createCourier(courierWithCorrectParams);
        CourierClient.loginCourier(courierWithoutLogin)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Courier authorizing without password")
    @Description("Should return HTTP400 when authorizing courier without password")
    public void shouldResponseBadRequestWhileAuthorizingWithoutPassword() {
        CourierClient.createCourier(courierWithCorrectParams);
        CourierClient.loginCourier(courierWithoutPassword)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Courier authorizing with non-existent data")
    @Description("Should return HTTP404 when authorizing courier with non-existent data")
    public void shouldResponseBadRequestWhileAuthorizingWithNonexistentData() {
        CourierClient.createCourier(courierWithCorrectParams);
        CourierClient.loginCourier(courierWithNonExistentData)
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

}
