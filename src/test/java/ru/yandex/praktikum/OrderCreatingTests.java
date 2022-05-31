package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreatingTests {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    private String[] colors;

    static OrderCreatingConstructor orderData = new OrderCreatingConstructor(
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
    @DisplayName("Создание заказа с различными предпочитаемыми цветами")
    @Description("Должно вернуться HTTP201 и trakeid при различных вариациях необязательного параметра colors")
    public void shouldResponseOkWithCorrectParams() {
        APIFunctions.createOrder(orderData)
                .then()
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }

}
