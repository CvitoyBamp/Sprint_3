package ru.yandex.praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierCreatingConstructor {
    private String login;
    private String password;
    private String firstName;

    public CourierCreatingConstructor(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierCreatingConstructor() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
