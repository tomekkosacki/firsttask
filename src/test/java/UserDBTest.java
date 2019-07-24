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
    private UserDB testObject;
    private List<UserEntity> userEntityList = new ArrayList<>();

    @Before
    public void init() {

        Morphia morphia = new Morphia();
        morphia.map(UserEntity.class);
        Fongo fongo = new Fongo("mongoTestServer");
        datastore = morphia.createDatastore(fongo.getMongo(), "testUserDB");
        datastore.ensureIndexes();
        testObject = new UserDB(datastore);
    }

    @Test
    public void getUserByIdForFilledDbForValidArgument() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        datastore.save(userEntityList);
        assertEquals(tempUserEntity1, testObject.getUserById("1"));
    }

    @Test
    public void getUserByIdFromEmptyDbForValidArgument() {

        assertNull(testObject.getUserById("1"));
    }

    @Test
    public void getUserByIdWithNullArgument() {

        assertNull(testObject.getUserById(null));
    }

    @Test
    public void getUserByIdWhenUserNotFound() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        datastore.save(userEntityList);
        assertNull(testObject.getUserById("2"));
    }

    @Test
    public void getUserByGetAllForEmptyDb() {

        assertEquals(userEntityList, testObject.getUserBy(null, null, null, null, 0, 0, null));
    }

    @Test
    public void getUserByGetAllForFilledDb() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        datastore.save(userEntityList);
        assertEquals(userEntityList, testObject.getUserBy(null, null, null, null, 0, 0, null));
    }

    @Test
    public void getUserByFirstNameForEmptyDb() {

        assertEquals(userEntityList, testObject.getUserBy(null, "Jan", null, null, 0, 0, null));
    }

    @Test
    public void getUserByFirstNameForFilledDb() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        datastore.save(userEntityList);
        assertEquals(userEntityList, testObject.getUserBy(null, "Jan", null, null, 0, 0, null));
    }

    @Test
    public void createUserForValidArgument() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        testObject.createUser(tempUserEntity1);
        assertEquals(userEntityList, datastore.createQuery(UserEntity.class).asList());

    }

    @Test
    public void createUserWithNullArgument() {

        boolean thrown = false;
        try {
            testObject.createUser(null);
        } catch (UpdateException ex) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void createUserWhenAlreadyExsist() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        UserEntity tempUserEntity2 = new UserEntity("1", "Jan", "Kowalski", "nowak@mail.com", new Date());
        userEntityList.add(tempUserEntity1);
        testObject.createUser(tempUserEntity2);
        assertNotEquals(userEntityList, datastore.createQuery(UserEntity.class).asList());
    }

    @Test
    public void deleteUserFromDb() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        datastore.save(tempUserEntity1);
        testObject.deleteUser(tempUserEntity1);
        assertEquals(userEntityList, datastore.createQuery(UserEntity.class).asList());
    }

    @Test
    public void deleteUserFromDbWhenUserNotFound() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        UserEntity tempUserEntity2 = new UserEntity("2", "Jan2", "Nowak2", "nowak2@mail.com", new Date());
        boolean thrown = false;
        datastore.save(tempUserEntity1);
        try {
            testObject.deleteUser(tempUserEntity2);
        } catch (RuntimeException ex) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void deleteUserFromDbWithNullArgument() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        boolean thrown = false;
        datastore.save(tempUserEntity1);
        try {
            testObject.deleteUser(null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void updateUserForValidUser() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        datastore.save(tempUserEntity1);
        tempUserEntity1.setLastName("Kowalski");
        testObject.updateUser("1", tempUserEntity1);
        assertEquals(tempUserEntity1, datastore.createQuery(UserEntity.class)
                .field("id").equal("1")
                .get());
    }

    @Test
    public void updateUserUserNotFound() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        testObject.updateUser("1", tempUserEntity1);
    }

    @Test
    public void updateUserWithNullArgument() {

        UserEntity tempUserEntity1 = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        boolean thrown = false;
        datastore.save(tempUserEntity1);
        try {
            testObject.updateUser("1", null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
