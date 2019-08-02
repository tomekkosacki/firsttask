package com.comarch.tomasz.kosacki.jobs;

import com.comarch.tomasz.kosacki.generators.DateOfBirthGenerator;
import com.comarch.tomasz.kosacki.service.UserService;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DateOfBirthJob implements Job {

    private UserService userService;
    private DateOfBirthGenerator dateOfBirthGenerator;
    private Logger log = LoggerFactory.getLogger(getClass());
    private int limitPagging;
    private String fieldName = "dateOfBirth";

    public DateOfBirthJob() {
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setDateOfBirthGenerator(DateOfBirthGenerator dateOfBirthGenerator) {
        this.dateOfBirthGenerator = dateOfBirthGenerator;
    }

    public void setLimitPagging(int limitPagging) {
        this.limitPagging = limitPagging;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("DateOfBirthJob started");

        List<UserEntity> userEntityList = userService.getUserByFieldWhenNull(limitPagging, fieldName);
        for (UserEntity user : userEntityList) {
            userService.updateUserField(user.getId(), dateOfBirthGenerator.generateDateOfBirth(user.getCreationDate()), fieldName);
            log.info("updating user: {} date of birth", user.getId());
        }

    }
}
