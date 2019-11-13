package org.store.service;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.store.StoreApplication;
//import org.store.config.H2JpaConfig;
import org.store.domain.Role;
import org.store.domain.User;
import org.store.domain.enums.RoleType;
import org.store.repository.UserRepository;
import org.store.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    UserServiceImpl userService;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = RuntimeException.class)
    public void testSaveUserThrowsException() {

        when(userRepositoryMock.save(any(User.class))).thenThrow(RuntimeException.class);

        User user = new User();

        userService.saveUser(user);//

    }

    @Test
    public void testSaveUserAndReturnsNewUser() {
        when(userRepositoryMock.save(any(User.class))).thenReturn(new User());

        User user = new User();

        assertThat(userService.saveUser(user), is(notNullValue()));
    }

    @Test
    public void testSaveUserAndReturnsFirstName() {
        // Create a user
        User aMockUser = new User();
        aMockUser.setFirstName("hedi");
        aMockUser.setLastName("abed");

        when(userRepositoryMock.save(any(User.class))).thenReturn(aMockUser);

        // Save the user
        User newUser = userService.saveUser(null);

        // Verify the save
        assertEquals("hedi", newUser.getFirstName());
    }

    @Test
    public void testSaveUserAndReturnsNewUserWithId() {

        when(userRepositoryMock.save(any(User.class))).thenAnswer(new Answer<Object>() {

            @Override
            public User answer(InvocationOnMock invocation) throws Throwable {

                Object[] arguments = invocation.getArguments();

                if (arguments != null && arguments.length > 0 && arguments[0] != null){

                    User user = (User) arguments[0];
                    user.setId(Long.valueOf(1));

                    return user;
                }

                return null;
            }
        });

        User user = new User();

        assertThat(userService.saveUser(user), is(notNullValue()));
        assertEquals( Long.valueOf(1), user.getId());

    }

    @Test
    public void testGetAllUsers()
    {
        List<User> list = new ArrayList<User>();

        list.add(new User());
        list.add(new User());
        list.add(new User());

        when(userRepositoryMock.findAll()).thenReturn(list);

        List<User> users = userService.getAllUsers();

        assertEquals(3, users.size());
        verify(userRepositoryMock, times(1)).findAll();
    }



    @Test
    public void testGetUserById()
    {
        User aMockUser = new User();
        aMockUser.setId(Long.valueOf(1));
        aMockUser.setUsername("HediAbed");
        aMockUser.setFirstName("hedi");
        aMockUser.setLastName("abed");

        when(userRepositoryMock.findOne(Long.valueOf(1))).thenReturn(aMockUser);

        User user = userService.findById((long)1).get();

        assertEquals("hedi", user.getFirstName());
        assertEquals("abed", user.getLastName());
        assertEquals("HediAbed", user.getUsername());
    }



}
