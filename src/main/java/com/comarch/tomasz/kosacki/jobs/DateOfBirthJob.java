package com.comarch.tomasz.kosacki.jobs;

import com.comarch.tomasz.kosacki.generators.DateOfBirthGenerator;
import com.comarch.tomasz.kosacki.service.UserService;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DateOfBirthJob implements Job {

    private UserService userService;
    private DateOfBirthGenerator dateOfBirthGenerator;
    private Logger log = LoggerFactory.getLogger(getClass());
    public DateOfBirthJob() {
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setDateOfBirthGenerator(DateOfBirthGenerator dateOfBirthGenerator) {
        this.dateOfBirthGenerator = dateOfBirthGenerator;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("DateOfBirthJob stated");
        JobDataMap dataMap = context.getMergedJobDataMap();
        List<UserEntity> userEntityList = userService.getUserByDateOfBirthWhenNull();
        for(UserEntity user: userEntityList) {
            userService.updateDate(user.getId(), dateOfBirthGenerator.generateDateOfBirth(user.getCreationDate()));
            log.info("updating user: {} date of birth", user.getId());
        }

    }
}
