package com.comarch.tomasz.kosacki.generators;

import java.time.LocalDateTime;

public class ZodiacGenerator {

    public String findZodiac(LocalDateTime dateOfBirth) {

        int month = dateOfBirth.getMonthValue();
        int day = dateOfBirth.getDayOfMonth();

        String zodiacSign = "";

        if (month == 12) {

            if (day < 22)
                zodiacSign = "sagittarius";
            else
                zodiacSign = "capricorn";
        } else if (month == 1) {
            if (day < 20)
                zodiacSign = "capricorn";
            else
                zodiacSign = "aquarius";
        } else if (month == 2) {
            if (day < 19)
                zodiacSign = "aquarius";
            else
                zodiacSign = "pisces";
        } else if (month == 3) {
            if (day < 21)
                zodiacSign = "pisces";
            else
                zodiacSign = "aries";
        } else if (month == 4) {
            if (day < 20)
                zodiacSign = "aries";
            else
                zodiacSign = "taurus";
        } else if (month == 5) {
            if (day < 21)
                zodiacSign = "taurus";
            else
                zodiacSign = "gemini";
        } else if (month == 6) {
            if (day < 21)
                zodiacSign = "gemini";
            else
                zodiacSign = "cancer";
        } else if (month == 7) {
            if (day < 23)
                zodiacSign = "cancer";
            else
                zodiacSign = "leo";
        } else if (month == 8) {
            if (day < 23)
                zodiacSign = "leo";
            else
                zodiacSign = "virgo";
        } else if (month == 9) {
            if (day < 23)
                zodiacSign = "virgo";
            else
                zodiacSign = "libra";
        } else if (month == 10) {
            if (day < 23)
                zodiacSign = "libra";
            else
                zodiacSign = "scorpio";
        } else if (month == 11) {
            if (day < 22)
                zodiacSign = "scorpio";
            else
                zodiacSign = "sagittarius";
        }
        return zodiacSign;
    }

}
