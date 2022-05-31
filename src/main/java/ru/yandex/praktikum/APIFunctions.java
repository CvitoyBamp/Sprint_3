package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.junit.Before;


public class APIFunctions {

    public enum API {
        LOGIN("/api/v1/courier/login"),
        CREATE("/api/v1/courier"),
        ORDER("/api/v1/orders");

        private final String api;

        API(String api) {
            this.api = api;
        }

        public String getApi() {
            return api;
        }
    }

    public static Response createCourier(CourierCreatingConstructor courierCreatingConstructor) {
        return given()
                .header("Content-type", "application/json")
                .body(courierCreatingConstructor)
                .post(API.CREATE.getApi());
    }

    public static Response loginCourier(CourierLoginConstructor courierLoginConstructor) {
        return given()
                .header("Content-type", "application/json")
                .body(courierLoginConstructor)
                .post(API.LOGIN.getApi());
    }

    public static Response createOrder(OrderCreatingConstructor orderCreatingConstructor) {
        return given()
                .header("Content-type", "application/json")
                .body(orderCreatingConstructor)
                .post(API.ORDER.getApi());
    }
}
