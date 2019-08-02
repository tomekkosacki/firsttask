package com.comarch.tomasz.kosacki.jobs;

import com.comarch.tomasz.kosacki.dao.UserDao;
import com.comarch.tomasz.kosacki.generators.ZodiacGenerator;
import com.comarch.tomasz.kosacki.tags.TagClient;
import com.comarch.tomasz.kosacki.tags.TagEntity;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ZodiacJob implements Job {

    private ZodiacGenerator zodiacGenerator;
    private UserDao userDao;
    private TagClient tagClient;

    private Logger log = LoggerFactory.getLogger(getClass());
    private int limitPagging;
    private String tagName = "zodiac";
    private int listSize;

    public ZodiacJob() {
    }

    public void setZodiacGenerator(ZodiacGenerator zodiacGenerator) {
        this.zodiacGenerator = zodiacGenerator;
    }

    public void setLimitPagging(int limitPagging) {
        this.limitPagging = limitPagging;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTagClient(TagClient tagClient) {
        this.tagClient = tagClient;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("ZodiacJob started");

        int iterator2 = 0;

        do {
            List<UserEntity> userEntityList = userDao.getUserBy(null, null, null, null, limitPagging * iterator2, limitPagging, null);
            for (UserEntity userEntity : userEntityList) {
                int iterator = 0;
                do {
                    List<TagEntity> tagEntityList = tagClient.getTagBy(userEntity.getId(), tagName, limitPagging, limitPagging * iterator);
                    if (tagEntityList.isEmpty()) {
                        String zodiacSign = zodiacGenerator.findZodiac(userEntity.getDateOfBirth());
                        TagEntity zodiacTag = new TagEntity(userEntity.getId(), tagName, zodiacSign);
                        tagClient.createTag(zodiacTag);
                        log.info("Creating zodiac for user: {} ", userEntity.getId());
                    }
                    iterator++;
                    listSize = tagEntityList.size();
                } while (listSize == limitPagging);
            }
            iterator2++;
            listSize = userEntityList.size();
        } while (listSize == limitPagging);
    }
}
