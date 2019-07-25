package resourcesTest;

import com.comarch.tomasz.kosacki.service.UserService;
import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import com.comarch.tomasz.kosacki.resources.UserResources;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserResourcesTest {

    @Mock
    private UserService userService = mock(UserService.class);
    @Mock
    private Mapper mapper = mock(Mapper.class);
    private UserResources testUserResources;

    @Before
    public void init() {

        testUserResources = new UserResources(mapper, userService);
    }

    @Test
    public void getUserByIdForValidArgumentUserServiceTest() {

        testUserResources.getUserById("1");
        verify(userService, times(1)).getUserById(any());
    }

    @Test
    public void getUserByIdForValidArgumentMapperTest() {

        testUserResources.getUserById("1");
        verify(mapper, times(1)).userEntityToUserDto(any());
    }

    @Test
    public void getUserByGetAllForValidArgumentUserServiceTest() {

        testUserResources.getUserBy(null, null, null, null, 0, 0, null);
        verify(userService, times(1)).getUserBy(any(), any(), any(), any(), anyInt(), anyInt(), any());
    }

    @Test
    public void getUserByGetAllForValidArgumentMapperTest() {

        testUserResources.getUserBy(null, null, null, null, 0, 0, null);
        verify(mapper, times(1)).userEntityListToUserDtoList(anyList());
    }

    @Test
    public void createUserForValidArgumentUserServiceTest() {

        UserDto tempUserDto = new UserDto("Jan", "Nowak", "nowak@mail.com", new Date());
        testUserResources.createUser(tempUserDto);
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void createUserForValidArgumentMapperTest() {

        UserDto tempUserDto = new UserDto("Jan", "Nowak", "nowak@mail.com", new Date());
        testUserResources.createUser(tempUserDto);
        verify(mapper, times(1)).userDtoToUserEntity(any());
    }

    @Test
    public void deleteUserForValidArgument() {

        testUserResources.deleteUser("1");
        verify(userService, times(1)).deleteUser("1");
    }

    @Test
    public void updateUserForValidArgumentUserServiceTest() {

        UserDto tempUserDto = new UserDto("Jan", "Nowak", "nowak@mail.com", new Date());
        testUserResources.updateUser("1", tempUserDto);
        verify(userService, times(1)).updateUser(any(), any());
    }

    @Test
    public void updateUserForValidArgumentMapperTest() {

        UserDto tempUserDto = new UserDto("Jan", "Nowak", "nowak@mail.com", new Date());
        testUserResources.updateUser("1", tempUserDto);
        verify(mapper, times(1)).userDtoToUserEntity(any());
    }
}
