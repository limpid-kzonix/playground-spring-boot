package com.omniesoft.commerce.security;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SecurityApplication.class)
@ActiveProfiles("test")
@Ignore
public class OAuthMvcTest {

    private static final String CLIENT_ID = "web-client";
    private static final String CLIENT_SECRET = "secret";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void obtainToken_thenOauthErrorResponse() throws Exception {
        obtainAccessToken(RandomString.make(6), RandomString.make(6));
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", CLIENT_ID);
        params.add("username", username);
        params.add("password", password);

        // @formatter:off
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .params(params)
                .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                .accept(CONTENT_TYPE))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE));

        // @formatter:on

        return RandomString.make();
    }

    @Test
    public void givenNoToken_whenGetSecureRequestWithoutHeaders_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/social/connected")).andExpect(status().is4xxClientError());
    }

    @Test
    public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
        final String accessToken = obtainAccessToken(RandomString.make(6), RandomString.make(6));
        log.debug("token:" + accessToken);
        mockMvc.perform(MockMvcRequestBuilders.get("/social/connected").header("Authorization", "Bearer " + accessToken)).andExpect(status().is4xxClientError());
    }

    @Test
    public void givenToken_whenPostSecureRequest_thenUnauthorized() throws Exception {
        final String accessToken = obtainAccessToken(RandomString.make(6), RandomString.make(6));

        // @formatter:off

        mockMvc.perform(MockMvcRequestBuilders.post("/social/facebook/signin")
                .header("Authorization", "Bearer " + accessToken)
                .param("social_token", RandomString.make(20))
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().is4xxClientError());

        mockMvc.perform(MockMvcRequestBuilders.post("/social/facebook/signin")
                .param("social_token", RandomString.make(20))
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(CONTENT_TYPE));

        // @formatter:on
    }
}
