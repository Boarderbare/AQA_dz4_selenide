package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.awt.*;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class FormTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] .input__control").setValue("Казань");
        form.$("[data-test-id=date] .input__control").setValue("12.12.2022");
        form.$("[data-test-id=name] .input__control").setValue("Василий Белов-Задунайский");
        form.$("[data-test-id=phone] .input__control").setValue("+79685554433");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$$(".button").find(exactText("Забронировать")).click();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shoulCityError() {

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] .input__control").setValue("Муром");
        form.$("[data-test-id=date] .input__control").setValue("12.12.2022");
        form.$("[data-test-id=name] .input__control").setValue("Василй Иванов");
        form.$("[data-test-id=phone] .input__control").setValue("+79685554433");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$$(".button").find(exactText("Забронировать")).click();
        form.$("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldDateError() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] .input__control").setValue("Мурманск");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.DELETE);
        form.$("[data-test-id=name] .input__control").setValue("Василий Иванов");
        form.$("[data-test-id=phone] .input__control").setValue("+79685554433");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$$(".button").find(exactText("Забронировать")).click();
        form.$("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldNameError() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] .input__control").setValue("Мурманск");
        form.$("[data-test-id=date] .input__control").setValue("02.08.2022");
        form.$("[data-test-id=name] .input__control").setValue("Василий dfgdfg");
        form.$("[data-test-id=phone] .input__control").setValue("+79685554433");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$$(".button").find(exactText("Забронировать")).click();
        form.$("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldPhoneError() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] .input__control").setValue("Мурманск");
        form.$("[data-test-id=date] .input__control").setValue("02.08.2022");
        form.$("[data-test-id=name] .input__control").setValue("Василий Иванов");
        form.$("[data-test-id=phone] .input__control").setValue("+796855");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$$(".button").find(exactText("Забронировать")).click();
        form.$("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldEmptyCheckBox() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] .input__control").setValue("Мурманск");
        form.$("[data-test-id=date] .input__control").setValue("02.08.2022");
        form.$("[data-test-id=name] .input__control").setValue("Василий Иванов");
        form.$("[data-test-id=phone] .input__control").setValue("+79685554433");
        form.$$(".button").find(exactText("Забронировать")).click();
        form.$("[data-test-id=agreement] .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldEmptyFieldCity() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=date] .input__control").setValue("02.08.2022");
        form.$("[data-test-id=name] .input__control").setValue("Василий Иванов");
        form.$("[data-test-id=phone] .input__control").setValue("+79685554433");
        form.$$(".button").find(exactText("Забронировать")).click();
        form.$("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEmptFieldName() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] .input__control").setValue("Мурманск");
        form.$("[data-test-id=date] .input__control").setValue("02.08.2022");
        form.$("[data-test-id=phone] .input__control").setValue("+79685554433");
        form.$$(".button").find(exactText("Забронировать")).click();
        form.$("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEmptyFieldPhone() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] .input__control").setValue("Мурманск");
        form.$("[data-test-id=date] .input__control").setValue("02.08.2022");
        form.$("[data-test-id=name] .input__control").setValue("Василий Иванов");
        form.$$(".button").find(exactText("Забронировать")).click();
        form.$("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldChoiseCityFromList() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] .input__control").sendKeys(Keys.chord("ва"));
        $$(".popup .popup__content .menu-item").find(matchText("Москва")).click();
        form.$("[data-test-id=date] .input__control").setValue("12.12.2022");
        form.$("[data-test-id=name] .input__control").setValue("Василий Белов-Задунайский");
        form.$("[data-test-id=phone] .input__control").setValue("+79685554433");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$$(".button").find(exactText("Забронировать")).click();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldChoiseDateFromCalendar() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] .input__control").setValue("Казань");
        $(".calendar-input .input__box button").click();
        $$(".popup__container .popup__content .calendar__day").find(exactText("10")).click();
        form.$("[data-test-id=name] .input__control").setValue("Василий Белов-Задунайский");
        form.$("[data-test-id=phone] .input__control").setValue("+79685554433");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$$(".button").find(exactText("Забронировать")).click();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }
}
