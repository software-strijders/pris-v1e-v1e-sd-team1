package models.user;

import enums.UserType;
import javafx.print.Collation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class User {

    private static ArrayList<User> registeredUsers = new ArrayList<>();

    protected String email;
    protected String password;
    protected String firstname;
    protected String surname;
    protected UUID userId;

    public User(String email, String password, String firstname, String surname, UUID userId){

        // NEED TO MAKE CHECKS FOR REGISTERING
        setEmail(email);
        setPassword(password);
        setFirstname(firstname);
        setSurname(surname);
        setUserId(userId);
        //registeredUsers.add(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public static List<User> getRegisteredUsers() {
        return Collections.unmodifiableList(registeredUsers);
    }

    public static List<User> getRegisteredUsersByType(UserType userType) {
        return registeredUsers.stream().filter(user ->
                user.getClass() == userType.typeClass()).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return email.equals(user.email);
    }
}
