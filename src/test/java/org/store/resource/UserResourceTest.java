package org.store.resource;


import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.store.domain.User;
import org.store.dto.request.CreateUserRequest;
import org.store.mapper.UserMapper;
import org.store.repository.UserRepository;
import org.store.service.MailConstructor;
import org.store.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.*;



@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(UserResource.class)
public class UserResourceTest {



    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private MailConstructor mailConstructor;

    @MockBean
    private JavaMailSender javaMailSender;

    @InjectMocks
    private UserResource userResource;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUserAndReturnsAnExistUserName() throws Exception{

        User mockUser = new User();
        mockUser.setUsername("hedi");

        when(userService.findByUsername("hedi"))
                .thenReturn(java.util.Optional.of(mockUser));

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("hedi");

        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(createUserRequest)))
                .andExpect(status().isNotAcceptable());

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
