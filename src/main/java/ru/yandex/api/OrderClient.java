package ru.yandex.api;

import io.restassured.response.Response;
import ru.yandex.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient{

    public static Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .post(orderAPI);
    }

    public static Response getOrderList() {
        return given()
                .header("Content-type", "application/json")
                .get(orderAPI);
    }
}
