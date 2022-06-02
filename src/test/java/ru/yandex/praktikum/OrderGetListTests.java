package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.yandex.api.OrderClient;

import java.util.List;

import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;

public class OrderGetListTests {

    @Test
    @DisplayName("View list of orders")
    @Description("Should return list of orders")
    public void shouldBeListOfOrders() {
        OrderClient.getOrderList()
                .then()
                .assertThat()
                .statusCode(200)
                .body("orders", notNullValue())
                .and()
                .body("orders", isA(List.class));
    }
}
