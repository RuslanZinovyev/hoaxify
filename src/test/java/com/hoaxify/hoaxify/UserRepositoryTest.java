package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.repository.UserRepository;
import com.hoaxify.hoaxify.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.hoaxify.hoaxify.TestUtils.createValidUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUserName_whenUserExists_returnsUser() {
        testEntityManager.persist(createValidUser());

        User inDB = userRepository.findByUserName("test-user");
        assertThat(inDB).isNotNull();
    }

    @Test
    public void findByUserName_whenUserDoesNotExist_returnNull() {
        User inDb = userRepository.findByUserName("non-existing-user");
        assertThat(inDb).isNull();
    }

}
