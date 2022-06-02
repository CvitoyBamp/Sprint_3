package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import ru.yandex.api.CourierClient;
import ru.yandex.model.Courier;

import static org.hamcrest.core.IsEqual.equalTo;

public class CourierCreatingNegativeTests {

    public static Courier courierWithoutLogin = new Courier(null, RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
    public static Courier courierWithoutPassword = new Courier(RandomStringUtils.randomAlphabetic(5),null, RandomStringUtils.randomAlphabetic(5));

    @Test
    @DisplayName("Creating a courier without login")
    @Description("Should return HTTP400 when creating a courier without login-field")
    public void shouldResponseBadRequestWithoutCourierLogin() {
        CourierClient.createCourier(courierWithoutLogin)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Creating a courier without password")
    @Description("Should return HTTP400 when creating a courier without password-field")
    public void shouldResponseBadRequestWithoutCourierPassword() {
        CourierClient.createCourier(courierWithoutPassword)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
