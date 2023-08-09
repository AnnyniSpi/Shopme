package com.example.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userNamHM = new User("nam@codejava.net", "nam2023","Nam", "Ha Minh");
        userNamHM.addRole(roleAdmin);

        User savedUser = repo.save(userNamHM);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRole(){
        User userRavi = new User("ravi@codejava.net", "ravi2023", "Ravi", "Kumar");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userRavi.addRole(roleEditor);
        userRavi.addRole(roleAssistant);

        User savedUser = repo.save(userRavi);

        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAllUsers(){
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
        User userNam = repo.findById(1).get();
        System.out.println(userNam);
        assertThat(userNam).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User userNam = repo.findById(1).get();
        userNam.setEnabled(true);
        userNam.setEmail("namjavaprogrammer@gmail.com");

        repo.save(userNam);
    }

    @Test
    public void testUpdateUserRoles(){
        User userRavi = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        userRavi.getRoles().remove(roleEditor);
        userRavi.addRole(roleSalesperson);

        repo.save(userRavi);
    }

    @Test
    public void testDeleteUser(){
        Integer userId = 2;
        repo.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail(){
        String email = "aspirido4@gmail.com";
        User user = repo.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById(){
        Integer id = 1;
        Long countById = repo.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisableUser(){
        Integer id = 1;
        repo.updateEnableStatus(id, false);
    }
}
