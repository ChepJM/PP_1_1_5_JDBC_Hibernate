package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Petr", "Petrov", (byte)43);
        userService.saveUser("Ivan", "Ivanov", (byte)25);
        userService.saveUser("Dmitrii", "Dmitriev", (byte)18);
        userService.saveUser("Sergei", "Sergeev", (byte)37);
        ArrayList<User> userList = (ArrayList<User>) userService.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
