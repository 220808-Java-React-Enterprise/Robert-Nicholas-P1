package com.revature.ers.services;

import com.revature.ers.daos.UserDAO;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.models.User;
import com.revature.ers.utils.custom_exceptions.custom_exceptions.InvalidRequestException;
import com.revature.ers.utils.custom_exceptions.custom_exceptions.ResourceConflictException;

import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Pre:
    // Post:
    // Purpose:
    public User register(NewUserRequest request){
        User user = null;
        if (isValidUsername(request.getUsername()))
            if (!isDuplicateUsername(request.getUsername()))
                if (isValidEmail(request.getEmail()))
                    if (!isDuplicateEmail(request.getEmail()))
                        if (isValidPassword(request.getPassword1()))
                            if (isSamePassword(request.getPassword1(), request.getPassword2()))
                                if (isValidName(request.getGivenName()) && isValidName(request.getSurName())){
                                    user = new User(UUID.randomUUID().toString(), request.getUsername(), request.getEmail(),
                                        request.getPassword1(), request.getGivenName(), request.getSurName());
                                    userDAO.save(user);
                                }
        return user;
    }

    // Pre: A customer is signing up or updating their account
    // Post: The username provided is checked if it is valid
    // Purpose: To validate a username provided by the user
    public boolean isValidUsername(String username) {
        if (!username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$"))
            throw new InvalidRequestException("\nInvalid username! username is 8-20 characters long. no _ or . at the beginning. no __ or _. or ._ or .. inside");
        return true;
    }

    // Pre: A user is trying to sign up
    // Post: Returns true if the username provided already exists in the db
    // Purpose: To check if the username provided is taken
    public boolean isDuplicateUsername(String username){
        if (userDAO.getUsername(username) != null)
            throw new ResourceConflictException("\nSorry, " + username + " is already taken");
        return false;
    }

    // Pre: A customer is signing up or updating their account
    // Post: The email provided is checked if it is valid
    // Purpose: To validate an email provided by the user
    public boolean isValidEmail(String email){
        if (!email.matches("[A-Za-z0-9][A-Za-z0-9\\!\\#\\$\\%\\&\\'\\*\\+\\-\\/\\=\\?\\^\\_\\`\\{\\}\\|]{0,63}@[A-Za-z0-9.-]{1,253}.[A-Za-z]{2,24}"))
            throw new InvalidRequestException("\nInvalid email format!");
        return true;
    }

    // Pre: A user is trying to sign up
    // Post: Returns true if the email provided already exists in the db
    // Purpose: To check if the email provided is taken
    public boolean isDuplicateEmail(String email){
        if (userDAO.getEmail(email) != null)
            throw new ResourceConflictException("\nSorry, " + email + " is already taken");
        return false;
    }

    // Pre: A customer is signing up or updating their account
    // Post: The password provided is checked if it is valid
    // Purpose: To validate a password provided by the user
    public boolean isValidPassword(String pass){
        if (!pass.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"))
            throw new InvalidRequestException("\nInvalid password! Minimum eight characters, at least one letter and one number");
        return true;
    }

    // Pre: Users name and email is validated
    // Post: Returns true if password1 and password2 match, false otherwise
    // Purpose: To check if password1 and password2 match
    public boolean isSamePassword(String password1, String password2){
       if (!password1.equals(password2))
           throw new InvalidRequestException("\nPasswords do not match");
       return true;
    }

    // Pre: A customer is signing up or updating their account
    // Post: The name provided is checked if it is valid
    // Purpose: To validate a name provided by the user
    public boolean isValidName(String name){
        if (!name.matches("^[\\p{L} .'-]+$"))
            throw new InvalidRequestException("\nInvalid format!");
        return true;
    }
}
