package com.revature.ers.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.revature.ers.daos.UserDAO;
import com.revature.ers.daos.UserRoleDAO;
import com.revature.ers.dtos.requests.LoginRequest;
import com.revature.ers.dtos.requests.UserRequest;
import com.revature.ers.dtos.responses.UserResponse;
import com.revature.ers.models.User;
import com.revature.ers.utils.custom_exceptions.InvalidRequestException;
import com.revature.ers.utils.custom_exceptions.ResourceConflictException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest{
    private UserService sut;
    private final UserDAO mockUserDAO = mock(UserDAO.class);
    private final UserRoleDAO mockUserRoleDAO = mock(UserRoleDAO.class);

    @Before
    public void setUp(){
        sut = new UserService(mockUserDAO, mockUserRoleDAO);
    }

    @Test
    public void isValidUsernameTest_valid() {
        String username = "username913";

        boolean flag = sut.isValidUsername(username);

        Assert.assertTrue(flag);
    }

    @Test (expected = ResourceConflictException.class)
    public void isValidUsernameTest_invalid() {
        String username = "user3";

        sut.isValidUsername(username);
    }

    @Test (expected = ResourceConflictException.class)
    public void isValidUsernameTest_invalidEmpty() {
        String username = "";

        sut.isValidUsername(username);
    }

    @Test
    public void isDuplicateUsername_false(){
        String validUsername = "RJamesRJ";

        boolean flag = sut.isDuplicateUsername(validUsername);

        Assert.assertFalse(flag);
    }

    @Test (expected = ResourceConflictException.class)
    public void isDuplicateUsername_true(){
        String validUsername = "RJamesRJ";

        when(mockUserDAO.getUsername(validUsername)).thenReturn("RJamesRJ");

        sut.isDuplicateUsername(validUsername);
    }

    @Test
    public void isValidEmail_valid(){
        String validEmail = "email@domain.com";

        boolean flag = sut.isValidEmail(validEmail);

        Assert.assertTrue(flag);
    }

    @Test
    public void isValidEmail_validWithNumbers(){
        String validEmail = "email921@domain.net";

        boolean flag = sut.isValidEmail(validEmail);

        Assert.assertTrue(flag);
    }

    @Test (expected = ResourceConflictException.class)
    public void isValidEmail_invalid(){
        String validEmail = ".@domain.com";

        sut.isValidEmail(validEmail);
    }

    @Test (expected = ResourceConflictException.class)
    public void isValidEmail_invalid_missingDomainExtension(){
        String validEmail = "email@domain.";

        sut.isValidEmail(validEmail);
    }

    @Test (expected = ResourceConflictException.class)
    public void isValidEmail_invalid_empty(){
        String validEmail = "";

        sut.isValidEmail(validEmail);
    }

    @Test
    public void isDuplicateEmail_false(){
        String email = "robertjames@gmail.com";

        boolean flag = sut.isDuplicateEmail(email);

        Assert.assertFalse(flag);
    }

    @Test (expected = ResourceConflictException.class)
    public void isDuplicateEmail_true(){
        String email = "robertjames@gmail.com";

        when(mockUserDAO.getEmail(email)).thenReturn("true");

        sut.isDuplicateEmail(email);
    }

    @Test
    public void isValidPassword_valid(){
        String password = "passw0rd";

        boolean flag = sut.isValidPassword(password);

        Assert.assertTrue(flag);
    }

    @Test
    public void isValidPassword_validNumbersAndCharacters(){
        String password = "K9ghk5Uo0";

        boolean flag = sut.isValidPassword(password);

        Assert.assertTrue(flag);
    }

    @Test (expected = ResourceConflictException.class)
    public void isValidPassword_invalidToShort(){
        String password = "pass";

        sut.isValidPassword(password);
    }

    @Test (expected = ResourceConflictException.class)
    public void isValidPassword_invalidNoNumber(){
        String password = "password";

        sut.isValidPassword(password);
    }

    @Test (expected = ResourceConflictException.class)
    public void isValidPassword_invalidEmpty(){
        String password = "";

        sut.isValidPassword(password);
    }

    @Test
    public void isSamePassword_valid(){
        String p1 = "password1";
        String p2 = "password1";

        boolean flag = sut.isSamePassword(p1, p2);

        Assert.assertTrue(flag);
    }

    @Test(expected = InvalidRequestException.class)
    public void isSamePassword_invalid(){
        String p1 = "password1";
        String p2 = "password2";

        sut.isSamePassword(p1, p2);
    }

    @Test(expected = ResourceConflictException.class)
    public void registerTest(){
        UserRequest request = new UserRequest("RJamesRJ", "robertjames@gmail.com", "password1", "password1", "Robert", "James", "employee");
        when(mockUserDAO.getRoleIdByRole(request.getRoleId())).thenReturn("MANAGER");

        User user = sut.register(request);
    }

    @Test
    public void loginTest(){
        String username = "RJamesRJ";
        String password = "password1";

        when(mockUserDAO.getUserByUsernameAndPassword(username, password)).thenReturn(new User());

        User user = mockUserDAO.getUserByUsernameAndPassword(username, password);

        Assert.assertNotNull(user);
    }
}