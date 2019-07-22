import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import com.github.fakemongo.Fongo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserDBTest {

    private Datastore datastore;
    private UserDB testObject;
    private List<UserEntity> userEntityList;

    @BeforeEach
    public void init() {
        Fongo fongo = new Fongo("mongoTestServer");
        Morphia morphia = new Morphia();
        morphia.map(UserEntity.class);
        datastore = morphia.createDatastore(fongo.getMongo(), "testUserDB");
        datastore.ensureIndexes();
        testObject = new UserDB(datastore);
    }

    @Test
    public void getUserByIdForFilledDB() {
        UserEntity tempUserEntity1 = new UserEntity("1", "Tomek", "Kosacki", "tomek@kosacki.com", new Date());
        UserEntity tempUserEntity2 = new UserEntity("2", "Tomek", "Kosacki", "tomek2@kosacki.com", new Date());
        userEntityList = Arrays.asList(tempUserEntity1, tempUserEntity2);
        datastore.save(userEntityList);

        Assert.assertEquals(userEntityList, testObject.getUserBy(null, null, null, null, 0, 10, null));

    }

}
