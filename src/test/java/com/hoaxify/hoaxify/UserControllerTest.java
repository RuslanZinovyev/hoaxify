package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.repository.UserRepository;
import com.hoaxify.hoaxify.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void cleanUp() {
        userRepository.deleteAll();
    }

    /**
     * Method name conventions is methodName_condition_expectedBehaviour
     */

    @Test
    public void postUser_whenUserIsValid_receiveOk() {
        User user = createValidUser();
        ResponseEntity<Object> response = testRestTemplate.postForEntity("/api/1.0/users", user, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase() {
        User user = createValidUser();
        testRestTemplate.postForEntity("/api/1.0/users", user, Object.class);

        assertThat(userRepository.count()).isEqualTo(1);
    }

    /**
     * Auxiliary methods
     */
     private User createValidUser() {
         User user = new User();
         user.setUserName("test-user");
         user.setDisplayName("test-display");
         user.setPassword("P4ssword");

         return user;
     }

}
