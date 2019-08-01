package com.comarch.tomasz.kosacki;

import com.comarch.tomasz.kosacki.generators.DateOfBirthGenerator;
import com.comarch.tomasz.kosacki.jobs.DateOfBirthJob;
import com.comarch.tomasz.kosacki.service.UserService;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;

public class UsersJobFactory extends SimpleJobFactory {

    private UserService userService;
    private DateOfBirthGenerator date;


    public static class Builder {
        private UserService userService = null;
        private DateOfBirthGenerator date = null;

        public Builder userService(UserService val) {
            this.userService = val;
            return this;
        }

        public Builder date(DateOfBirthGenerator val) {
            this.date = val;
            return this;
        }

        public UsersJobFactory build() {
            return new UsersJobFactory(this);
        }
    }

    public UsersJobFactory(Builder builder) {
        userService=builder.userService;
        date=builder.date;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler Scheduler) throws SchedulerException {
        Job job =  super.newJob(bundle, Scheduler);

        if(job instanceof DateOfBirthJob){
            DateOfBirthJob dateOfBirthJob = (DateOfBirthJob) job;
            dateOfBirthJob.setDateOfBirthGenerator(date);
            dateOfBirthJob.setUserService(userService);
        }
        return job;
    }
}
