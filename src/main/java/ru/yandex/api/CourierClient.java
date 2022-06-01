package ru.yandex.api;

import io.restassured.response.Response;
import ru.yandex.model.Courier;
import ru.yandex.model.CourierLogin;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient{

    public static Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(createAPI);
    }

    public static Response loginCourier(CourierLogin courierLogin) {
        return given()
                .header("Content-type", "application/json")
                .body(courierLogin)
                .post(loginAPI);
    }

    public static int getCourierId(CourierLogin courierLogin) {
        return given()
                .header("Content-type", "application/json")
                .body(courierLogin)
                .post(loginAPI)
                .then()
                .extract()
                .path("id");
    }

    public static Response deleteCourier(CourierLogin courierLogin, int courierId) {
        return given()
                .header("Content-type", "application/json")
                .body(courierLogin)
                .delete(createAPI+String.valueOf(courierId));
    }

}
