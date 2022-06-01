package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.isA;

public class OrderGetListTests {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("View list of orders")
    @Description("Should return list of orders")
    public void shouldBeListOfOrders() {
        APIFunctions.getOrderList()
                .then()
                .assertThat()
                .statusCode(200)
                .body("orders", isA(List.class));
    }
}
