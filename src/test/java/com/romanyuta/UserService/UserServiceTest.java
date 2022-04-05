package com.romanyuta.UserService;

import com.romanyuta.UserService.model.user.User;
import com.romanyuta.UserService.model.user.dto.UserMapper;
import com.romanyuta.UserService.model.user.dto.UserRequest;
import com.romanyuta.UserService.repos.UserRepo;
import com.romanyuta.UserService.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final long ID = 1L;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    public void findUser_shouldCallRepo(){
        final User user = mock(User.class);
        when(userRepo.getById(ID)).thenReturn(user);

        final User actual = userService.getUserById(ID);

        assertNotNull(actual);
        assertEquals(user,actual);
        verify(userRepo).getById(ID);
    }

    @Test
    public void saveUser_shouldCallRepo(){
        final UserRequest userRequest = mock(UserRequest.class);

        userService.addNewUser(userRequest);

        verify(userRepo).save(UserMapper.INSTANSE.mapUser(userRequest));
    }
}
