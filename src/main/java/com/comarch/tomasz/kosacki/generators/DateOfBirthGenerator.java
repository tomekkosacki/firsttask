package com.comarch.tomasz.kosacki.generators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class DateOfBirthGenerator {

    public DateOfBirthGenerator() {
    }

    public LocalDateTime generateDateOfBirth(LocalDateTime dateOfCreation) {

        long maxDay = LocalDate.of(dateOfCreation.getYear() - 18, dateOfCreation.getMonthValue(), dateOfCreation.getDayOfMonth()).toEpochDay();
        long minDay = LocalDate.of(dateOfCreation.getYear() - 40, dateOfCreation.getMonthValue(), dateOfCreation.getDayOfMonth()).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        LocalDateTime dateOfBirth = randomDate.atStartOfDay();

        return dateOfBirth;
    }

}
