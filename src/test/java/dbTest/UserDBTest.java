package dbTest;

import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import com.github.fakemongo.Fongo;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.UpdateException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UserDBTest {

    private Datastore datastore;
    private UserDB testUserDB;
    private List<UserEntity> userEntityList = new ArrayList<>();

    @Before
    public void init() {

        Morphia morphia = new Morphia();
        morphia.map(UserEntity.class);
        Fongo fongo = new Fongo("mongoTestServer");
        datastore = morphia.createDatastore(fongo.getMongo(), "testUserDB");
        datastore.ensureIndexes();
        testUserDB = new UserDB(datastore);
    }

    @Test
    public void getUserByIdForFilledDbForValidArgument() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        datastore.save(userEntityList);
        assertEquals(tempUserEntity1, testUserDB.getUserById("1"));
    }

    @Test
    public void getUserByIdFromEmptyDbForValidArgument() {

        assertNull(testUserDB.getUserById("1"));
    }

    @Test
    public void getUserByIdWithNullArgument() {

        assertNull(testUserDB.getUserById(null));
    }

    @Test
    public void getUserByIdWhenUserNotFound() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        datastore.save(userEntityList);
        assertNull(testUserDB.getUserById("2"));
    }

    @Test
    public void getUserByGetAllForEmptyDb() {

        assertEquals(userEntityList, testUserDB.getUserBy(null, null, null, null, 0, 0, null));
    }

    @Test
    public void getUserByGetAllForFilledDb() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        datastore.save(userEntityList);
        assertEquals(userEntityList, testUserDB.getUserBy(null, null, null, null, 0, 0, null));
    }

    @Test
    public void getUserByFirstNameForEmptyDb() {

        assertEquals(userEntityList, testUserDB.getUserBy(null, "Jan", null, null, 0, 0, null));
    }

    @Test
    public void getUserByFirstNameForFilledDb() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        datastore.save(userEntityList);
        assertEquals(userEntityList, testUserDB.getUserBy(null, "Jan", null, null, 0, 0, null));
    }

    @Test
    public void createUserForValidArgument() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        testUserDB.createUser(tempUserEntity1);
        assertEquals(userEntityList, datastore.createQuery(UserEntity.class).asList());

    }

    @Test(expected = UpdateException.class)
    public void createUserWithNullArgumentThrowUpdateException() {

        testUserDB.createUser(null);
    }

    @Test
    public void createUserWhenAlreadyExsist() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        UserEntity tempUserEntity2 = new UserEntity("1", "Jan", "Kowalski", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        testUserDB.createUser(tempUserEntity2);
        assertNotEquals(userEntityList, datastore.createQuery(UserEntity.class).asList());
    }

    @Test
    public void deleteUserFromDb() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        datastore.save(tempUserEntity1);
        testUserDB.deleteUser(tempUserEntity1);
        assertEquals(userEntityList, datastore.createQuery(UserEntity.class).asList());
    }

    @Test(expected = RuntimeException.class)
    public void deleteUserFromDbWhenUserNotFoundThrowRuntimeException() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        testUserDB.deleteUser(tempUserEntity1);
    }

    @Test(expected = NullPointerException.class)
    public void deleteUserFromDbWithNullArgumentThrowNullPointerException() {

        testUserDB.deleteUser(null);
    }

    @Test
    public void updateUserForValidUser() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        datastore.save(tempUserEntity1);
        tempUserEntity1.setLastName("Kowalski");
        testUserDB.updateUser("1", tempUserEntity1);
        assertEquals(tempUserEntity1, datastore.createQuery(UserEntity.class)
                .field("id").equal("1")
                .get());
    }

    @Test
    public void updateUserUserWhenNotFound() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        testUserDB.updateUser("1", tempUserEntity1);

    }

    @Test(expected = NullPointerException.class)
    public void updateUserWithNullArgumentThrowNullPointerException() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        datastore.save(tempUserEntity1);
        testUserDB.updateUser("1", null);

    }
}
