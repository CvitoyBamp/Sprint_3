package ru.yandex.praktikum;

public class CourierLoginConstructor {

    private String login;
    private String password;

    public CourierLoginConstructor(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierLoginConstructor() {
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

}
