package com.example.sehw1.presentation.mock;

import com.example.sehw1.persistence.User;

import java.util.LinkedList;
import java.util.List;

public class UserRepositoryMockWithUsersData extends UserRepositoryMock {
    @Override
    public Iterable<User> findAll() {
        List<User> list = new LinkedList<>();

        User user1 = new User();
        user1.setFirstName("First name 1");
        user1.setLastName("Last name 1");
        user1.setEmail("Email 1");
        user1.setPassword("Password 1");

        User user2 = new User();
        user2.setFirstName("First name 2");
        user2.setLastName("Last name 2");
        user2.setEmail("Email 2");
        user2.setPassword("Password 2");

        list.add(user1);
        list.add(user2);

        return list;
    }

}
