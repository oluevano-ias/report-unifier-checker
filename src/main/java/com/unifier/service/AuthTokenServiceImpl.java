package com.unifier.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifier.exception.AuthTokenException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private static Logger logger = (Logger) LoggerFactory.getLogger(AuthTokenServiceImpl.class);

    private CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();

    private AuthToken authToken;

    @Value("${sso.login.url}")
    private String logInUrl;

    @Value("${sso.username}")
    private String username;

    @Value("${sso.password}")
    private String password;

    @Value("${sso.client.id}")
    private String oauthClientId;

    @Value("${sso.client.secret}")
    private String oauthClientSecret;

    @Override
    public AuthToken getAuthToken() {

        if (!isAuthTokenValid())
            authToken = getNewAuthToken();
        return authToken;
    }

    private boolean isAuthTokenValid() {
        return (authToken != null && authToken.getExpires() != null && authToken.getExpires().isAfterNow());
    }

    private synchronized AuthToken getNewAuthToken() {

        HttpPost httpPost = new HttpPost(logInUrl);

        try {
            List<NameValuePair> requestParameters = new ArrayList<>();
            requestParameters.add(new BasicNameValuePair("grant_type", "password"));
            requestParameters.add(new BasicNameValuePair("username", username));
            requestParameters.add(new BasicNameValuePair("password", password));
            httpPost.setEntity(new UrlEncodedFormEntity(requestParameters));

            httpPost.setHeader("Authorization", "Basic " + encodeBase64String((oauthClientId + ":" + oauthClientSecret).getBytes()));
        } catch (UnsupportedEncodingException e) {
            throw new AuthTokenException(e);
        }

        try (CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost)) {

            if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
                httpPost.abort();
                throw new AuthTokenException("Failed to get sso oauth token from sso service: " + logInUrl + " got response: " + closeableHttpResponse.getStatusLine());
            }

            HttpEntity httpEntity = closeableHttpResponse.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(EntityUtils.toString(httpEntity));

            EntityUtils.consume(httpEntity);

            DateTime expires = new DateTime().plusSeconds(rootNode.get("expires_in").asInt() - 30);
            logger.info("Got new token with expiration: {}", expires);
            return new AuthToken(rootNode.get("access_token").asText(), expires);
        } catch (IOException e) {
            throw new AuthTokenException(e);
        }
    }
}
