import com.comarch.tomasz.kosacki.UserService;
import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.servisExceptions.AppException;
import com.comarch.tomasz.kosacki.servisExceptions.DuplicateKeyExceptionEmail;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDB userDB = mock(UserDB.class);
    private UserService testUserService;

    @Before
    public void init() {
        testUserService = new UserService(userDB);
    }

    @Test
    public void getUserByIdForFilledDbForValidArgument() {

        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        when(userDB.getUserById(any())).thenReturn(tempUserEntity);
        UserEntity userEntity = testUserService.getUserById("1");
        assertEquals(tempUserEntity, userEntity);
    }

    @Test(expected = AppException.class)
    public void getUserByIdForFilledDbThrowNullArgumentException() {

        testUserService.getUserById(null);
    }

    @Test(expected = AppException.class)
    public void getUserByIdForEmptyDbThrowUserEntityNotFoundException() {

        testUserService.getUserById("1");
    }

    @Test
    public void getUserByGetAllForFilledDbForValidArgument() {

        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        List<UserEntity> tempUserEntityList = new ArrayList<>();
        tempUserEntityList.add(tempUserEntity);
        when(userDB.getUserBy(any(), any(), any(), any(), anyInt(), anyInt(), any())).thenReturn(tempUserEntityList);
        List<UserEntity> userEntityList = testUserService.getUserBy(null, null, null, null, 0, 0, null);
        assertEquals(tempUserEntityList, userEntityList);
    }

    @Test(expected = AppException.class)
    public void getUserByGetAllForEmptyDbThrowUserNotFoundException() {

        testUserService.getUserBy(null, null, null, null, 0, 0, null);
    }

    @Test
    public void getUserByFirstName() {

        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        List<UserEntity> tempUserEntityList = new ArrayList<>();
        tempUserEntityList.add(tempUserEntity);
        when(userDB.getUserBy(any(), any(), any(), any(), anyInt(), anyInt(), any())).thenReturn(tempUserEntityList);
        List<UserEntity> userEntityList = testUserService.getUserBy(null, "Jan", null, null, 0, 0, null);
        assertEquals(tempUserEntityList, userEntityList);
    }

    @Test
    public void createUserForValidArgument() {

        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        testUserService.createUser(tempUserEntity);
        verify(userDB, times(1)).createUser(any());
    }

    @Test(expected = AppException.class)
    public void createUserForNullArgument() {

        testUserService.createUser(null);
    }

//    @Test(expected = AppException.class)
//    public void createUserThrowDuplicateKeyExceptionEmail() {
//
//        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
//        doThrow(DuplicateKeyExceptionEmail.class).when(userDB).createUser(any());
//        testUserService.createUser(tempUserEntity);
//    }

    @Test
    public void deleteUserForValidArgument() {
        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        when(userDB.getUserById(any())).thenReturn(tempUserEntity);
        testUserService.deleteUser("1");
        verify(userDB, times(1)).deleteUser(any());
    }

    @Test(expected = AppException.class)
    public void deleteUserWhenUserNotFoundThrowUserEntityNotFoundException() {

        testUserService.deleteUser("1");
    }

    @Test(expected = AppException.class)
    public void deleteUserForNullArgument() {

        testUserService.deleteUser(null);
    }

    @Test
    public void updateUserForValidArgument() {

        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        when(userDB.getUserById(any())).thenReturn(tempUserEntity);
        testUserService.updateUser("1", tempUserEntity);
        verify(userDB, times(1)).updateUser("1", tempUserEntity);
    }

    @Test(expected = AppException.class)
    public void updateUserForBothNullArgumentsThrowNullArgumentException() {

        testUserService.updateUser(null, null);
    }

    @Test(expected = AppException.class)
    public void updateUserForUserIdNullThrowNullArgumentException() {

        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        when(userDB.getUserById(any())).thenReturn(tempUserEntity);
        testUserService.updateUser(null, tempUserEntity);
    }

    @Test(expected = AppException.class)
    public void updateUserForNewUserValueNullThrowNullArgumentException() {

        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        when(userDB.getUserById(any())).thenReturn(tempUserEntity);
        testUserService.updateUser("1", null);
    }

    @Test(expected = AppException.class)
    public void updateUserWhenUserNotFoundThrowUserEntityNotFoundException() {

        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        testUserService.updateUser("1", tempUserEntity);
    }

    @Test//(expected = AppException.class)
    public void updateUserThrowDuplicateKeyExceptionEmail() {

        UserEntity tempUserEntity = new UserEntity("1", "Jan", "Nowak", "nowak@mail.com", new Date());
        doThrow(DuplicateKeyExceptionEmail.class).when(userDB).updateUser(any(), any());
        testUserService.updateUser("1", tempUserEntity);
    }
}
