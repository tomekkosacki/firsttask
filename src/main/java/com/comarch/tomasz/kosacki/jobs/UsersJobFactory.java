package com.comarch.tomasz.kosacki.jobs;

import com.comarch.tomasz.kosacki.configurationClass.ProjectConfiguration;
import com.comarch.tomasz.kosacki.dao.UserDao;
import com.comarch.tomasz.kosacki.generators.DateOfBirthGenerator;
import com.comarch.tomasz.kosacki.generators.ZodiacGenerator;
import com.comarch.tomasz.kosacki.service.UserService;
import com.comarch.tomasz.kosacki.tags.TagClient;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;

public class UsersJobFactory extends SimpleJobFactory {

    private UserService userService;
    private DateOfBirthGenerator dateOfBirthGenerator;
    private ProjectConfiguration configuration;
    private ZodiacGenerator zodiacGenerator;
    private UserDao userDao;
    private TagClient tagClient;


    public UsersJobFactory() {
    }

    public UsersJobFactory(Builder builder) {
        userService = builder.userService;
        dateOfBirthGenerator = builder.dateOfBirthGenerator;
        configuration = builder.configuration;
        zodiacGenerator = builder.zodiacGenerator;
        userDao = builder.userDao;
        tagClient = builder.tagClient;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler) throws SchedulerException {
        Job job = super.newJob(bundle, Scheduler);

        if (job instanceof DateOfBirthJob) {
            DateOfBirthJob dateOfBirthJob = (DateOfBirthJob) job;
            dateOfBirthJob.setDateOfBirthGenerator(dateOfBirthGenerator);
            dateOfBirthJob.setUserService(userService);
            dateOfBirthJob.setLimitPagging(configuration.getLimitPagging());
            dateOfBirthJob.setUserDao(userDao);
        } else if (job instanceof ZodiacJob) {
            ZodiacJob zodiacJob = (ZodiacJob) job;
            zodiacJob.setZodiacGenerator(zodiacGenerator);
            zodiacJob.setLimitPagging(configuration.getLimitPagging());
            zodiacJob.setUserDao(userDao);
            zodiacJob.setTagClient(tagClient);
        }
        return job;
    }

    private JobDetail dateOfBirthJob() {
        return JobBuilder
                .newJob(DateOfBirthJob.class)
                .withIdentity("DateOFBirthJob", "group1")
                .build();
    }

    private Trigger dateOfBirthTrigger(ProjectConfiguration configuration) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity("DateOfBirthTrigger", "group1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(configuration.getDateOfBirthJobIntervalInSeconds())
                                .repeatForever())
                .build();
    }

    private JobDetail zodiacJob() {
        return JobBuilder
                .newJob(ZodiacJob.class)
                .withIdentity("ZodiacJob", "group2")
                .build();
    }

    private Trigger zodiacTrigger(ProjectConfiguration configuration) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity("ZodiacTrigger", "group2")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(configuration.getZodiacJobIntervalInSeconds())
                                .repeatForever())
                .build();
    }

    private Scheduler dateOfBirthScheduler(DateOfBirthGenerator dateOfBirthGenerator, UserService userService, ProjectConfiguration configuration, UserDao userDao) throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getDefaultScheduler();
        scheduler.setJobFactory(new Builder()
                .configuration(configuration)
                .userDao(userDao)
                .dateOfBirthGenerator(dateOfBirthGenerator)
                .userService(userService)
                .build());

        return scheduler;
    }

    private Scheduler zodiacScheduler(ZodiacGenerator zodiacGenerator, UserDao userDao, ProjectConfiguration configuration, TagClient tagClient) throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getDefaultScheduler();
        scheduler.setJobFactory(new UsersJobFactory
                .Builder()
                .configuration(configuration)
                .userDao(userDao)
                .zodiacGenerator(zodiacGenerator)
                .tagClient(tagClient)
                .build());

        return scheduler;
    }

    public void runDateOFBirthJob(DateOfBirthGenerator dateOfBirthGenerator, UserService userService, ProjectConfiguration configuration, UserDao userDao) throws SchedulerException {

        Scheduler scheduler = dateOfBirthScheduler(dateOfBirthGenerator, userService, configuration, userDao);
        scheduler.scheduleJob(dateOfBirthJob(), dateOfBirthTrigger(configuration));
        scheduler.start();
    }

    public void runZodiacJob(ZodiacGenerator zodiacGenerator, UserDao userDao, ProjectConfiguration configuration, TagClient tagClient) throws SchedulerException {

        Scheduler scheduler = zodiacScheduler(zodiacGenerator, userDao, configuration, tagClient);
        scheduler.scheduleJob(zodiacJob(), zodiacTrigger(configuration));
        scheduler.start();
    }

    public static class Builder {
        private UserService userService;
        private DateOfBirthGenerator dateOfBirthGenerator;
        private ProjectConfiguration configuration;
        private ZodiacGenerator zodiacGenerator;
        private UserDao userDao;
        private TagClient tagClient;

        public Builder userService(UserService val) {
            this.userService = val;
            return this;
        }

        public Builder dateOfBirthGenerator(DateOfBirthGenerator val) {
            this.dateOfBirthGenerator = val;
            return this;
        }

        public Builder configuration(ProjectConfiguration val) {
            this.configuration = val;
            return this;
        }

        public Builder zodiacGenerator(ZodiacGenerator val) {
            this.zodiacGenerator = val;
            return this;
        }

        public Builder userDao(UserDao val) {
            this.userDao = val;
            return this;
        }

        public Builder tagClient(TagClient val) {
            this.tagClient = val;
            return this;
        }

        public UsersJobFactory build() {
            return new UsersJobFactory(this);
        }
    }

}
