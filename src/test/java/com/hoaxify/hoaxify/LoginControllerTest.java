package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.exception.ApiError;
import com.hoaxify.hoaxify.repository.UserRepository;
import com.hoaxify.hoaxify.service.UserService;
import com.hoaxify.hoaxify.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static com.hoaxify.hoaxify.TestUtil.createValidUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginControllerTest {

    private static final String API_1_0_LOGIN = "/api/1.0/login";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Before
    public void cleanUp() {
        userRepository.deleteAll();
        testRestTemplate.getRestTemplate().getInterceptors().clear();
    }

    /**
     * Method name conventions is methodName_condition_expectedBehaviour
     */

    @Test
    public void postLogin_withoutUserCredentials_receiveUnauthorized() {
        ResponseEntity<Object> response = login(Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postLogin_withIncorrectCredentials_receiveUnauthorized() {
        authenticate();
        ResponseEntity<Object> response = login(Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postLogin_withoutUserCredentials_receiveApiError() {
        ResponseEntity<ApiError> response = login(ApiError.class);

        assertThat(response.getBody().getUrl()).isEqualTo(API_1_0_LOGIN);
    }

    @Test
    public void postLogin_withoutUserCredentials_receiveApiErrorWithoutValidationErrors() {
        ResponseEntity<String> response = login(String.class);

        assertThat(response.getBody().contains("validationErrors")).isFalse();
    }

    @Test
    public void postLogin_withIncorrectCredentials_receiveUnauthorizedWithoutWWWAuthenticationHeader() {
        authenticate();
        ResponseEntity<Object> response = login(Object.class);

        assertThat(response.getHeaders().containsKey("WWW-Authenticate")).isFalse();
    }

    @Test
    public void postLogin_withValidCredentials_receiveOk() {
        userService.save(createValidUser());
        authenticate();
        ResponseEntity<Object> response = login(Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postLogin_withValidCredentials_receiveLoggedInUserId() {
        User inDB = userService.save(createValidUser());
        authenticate();
        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<>() {});
        Map<String, Object> body = response.getBody();
        Integer id = (Integer) body.get("id");
        assertThat(id).isEqualTo(inDB.getId());
    }

    @Test
    public void postLogin_withValidCredentials_receiveLoggedInUserImage() {
        User inDB = userService.save(createValidUser());
        authenticate();
        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<>() {});
        Map<String, Object> body = response.getBody();
        String image = (String) body.get("image");
        assertThat(image).isEqualTo(inDB.getImage());
    }

    @Test
    public void postLogin_withValidCredentials_receiveLoggedInUserDisplayName() {
        User inDB = userService.save(createValidUser());
        authenticate();
        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<>() {});
        Map<String, Object> body = response.getBody();
        String displayName = (String) body.get("displayName");
        assertThat(displayName).isEqualTo(inDB.getDisplayName());
    }

    @Test
    public void postLogin_withValidCredentials_receiveLoggedInUserUserName() {
        User inDB = userService.save(createValidUser());
        authenticate();
        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<>() {});
        Map<String, Object> body = response.getBody();
        String userName = (String) body.get("username");
        assertThat(userName).isEqualTo(inDB.getUsername());
    }

    @Test
    public void postLogin_withValidCredentials_notReceiveLoggedInUserPassword() {
        userService.save(createValidUser());
        authenticate();
        ResponseEntity<Map<String, Object>> response = login(new ParameterizedTypeReference<>() {});
        Map<String, Object> body = response.getBody();
        assertThat(body.containsKey("password")).isFalse();
    }

    /**
     * Auxiliary methods
     */
    private void authenticate() {
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthenticationInterceptor("test-user", "P4ssword"));
    }

    private  <T> ResponseEntity<T> login(Class<T> responseType) {
        return testRestTemplate.postForEntity(API_1_0_LOGIN, null, responseType);
    }

    private  <T> ResponseEntity<T> login(ParameterizedTypeReference<T> responseType) {
        return testRestTemplate.exchange(API_1_0_LOGIN, HttpMethod.POST, null, responseType);
    }

}
