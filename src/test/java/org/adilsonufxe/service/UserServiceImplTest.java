package org.adilsonufxe.service;

import org.adilsonufxe.io.UsersDatabase;
import org.adilsonufxe.io.UsersDatabaseImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    UsersDatabase usersDatabase;
    UserService userService;
    String createdUserId;

    @BeforeAll
    void setup() {
        // Create & initialize database
        usersDatabase = new UsersDatabaseImpl();
        usersDatabase.init();
        userService = new UserServiceImpl(usersDatabase);
    }

    @AfterAll
    void cleanup() {
        // Close connection
        // Delete database
        usersDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "John");
        user.put("lastName", "Wick");

        createdUserId = userService.createUser(user);

        Assertions.assertNotNull(createdUserId);
    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {
        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName", "Michael");
        newUserDetails.put("lastName", "Jackson");

        Map updatedUserDetails = userService.updateUser(createdUserId, newUserDetails);
        Assertions.assertEquals(newUserDetails.get("firstName"), updatedUserDetails.get("firstName"));
        Assertions.assertEquals(newUserDetails.get("lastName"), updatedUserDetails.get("lastName"));
    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {
        Map userDetails = userService.getUserDetails(createdUserId);
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(createdUserId, userDetails.get("userId"));
    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {
        userService.deleteUser(createdUserId);
        Assertions.assertNull(userService.getUserDetails(createdUserId));
    }
}
