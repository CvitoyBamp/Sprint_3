package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.api.OrderClient;
import ru.yandex.model.Order;

import java.time.LocalDate;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreatingTests {

    private final String[] colors;

    static Order orderData = new Order(
            RandomStringUtils.randomAlphabetic(6),
            RandomStringUtils.randomAlphabetic(6),
            RandomStringUtils.randomAlphabetic(10),
            Integer.parseInt(RandomStringUtils.randomNumeric(1)),
            "+7" + RandomStringUtils.randomNumeric(10),
            Integer.parseInt(RandomStringUtils.randomNumeric(1)),
            LocalDate.now().toString(),
            RandomStringUtils.randomAlphabetic(10)
    );

    public OrderCreatingTests(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                { new String[] {"BLACK"}},
                { new String[] {"GREY"}},
                { new String[] {"BLACK", "GREY"}},
                { new String[] {}}
        };
    }

    @Test
    @DisplayName("Creating an order with different preferred colors")
    @Description("Should return HTTP201 and trackid with different variations of the optional colors parameter")
    public void shouldResponseOkWithCorrectParams() {
        OrderClient.createOrder(orderData)
                .then()
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }

}