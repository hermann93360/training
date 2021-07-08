package com.java.training.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.java.training.dao.UserDao;
import com.java.training.dto.UserDto;
import com.java.training.model.User;
import org.keycloak.representations.JsonWebToken;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Value("${keycloak.auth-server-url}")
    private String keycloak;

    @Value("${keycloak.realm}")
    private String real;

    @Value("${keycloak.resource}")
    private String client;

    @Autowired
    private UserDao userDao;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDto newUser(UserDto userDto) throws Exception {
        System.out.println("zefzefze");

        try{
            if ( userDao.findByEmail(userDto.getEmail()) != null ) return null;

            LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("client_id", "admin-cli");
            map.add("username", "admin");
            map.add("password", "admin");
            map.add("grant_type", "password");

            System.out.println("etape 1");

            WebClient keycloakAccess = WebClient.builder().baseUrl(keycloak).build();

            JsonWebToken json = keycloakAccess.post()
                    .uri("/realms/training/protocol/openid-connect/token")
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                    .body(BodyInserters.fromFormData(map))
                    .retrieve()
                    .bodyToMono(JsonWebToken.class)
                    .block();

            System.out.println("token ( vzfnze ) : "  + json.getOtherClaims().get("access_token").toString());

            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setEmail(userDto.getEmail());
            userRepresentation.setUsername(userDto.getEmail());
            userRepresentation.setFirstName(userDto.getName());
            userRepresentation.setLastName(userDto.getName());
            userRepresentation.setEmailVerified(true);
            userRepresentation.setEnabled(true);
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType("password");
            credentialRepresentation.setValue(userDto.getPassword());
            credentialRepresentation.setTemporary(false);
            userRepresentation.setCredentials(List.of(credentialRepresentation));

            System.out.println("etape fini ");
            keycloakAccess.post()
                    .uri("/admin/realms/" + real + "/users")
                    .header("Authorization", "Bearer " + json.getOtherClaims().get("access_token").toString())
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                    .body(BodyInserters.fromValue(userRepresentation))
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            System.out.println("etape fini 2");

            User userSave = new User();
            userSave.setEmail(userDto.getEmail());
            userSave.setName(userDto.getName());
            userDao.save(userSave);

            return userDto;
        }
        catch(WebClientResponseException e){
            logger.info(e.getStatusCode().toString());
            logger.info(e.getResponseBodyAsString());
            logger.info(e.getStatusText());
        }

        return userDto;
    }
}
