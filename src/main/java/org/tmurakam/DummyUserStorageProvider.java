package org.tmurakam;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.models.*;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class DummyUserStorageProvider implements UserStorageProvider, UserQueryProvider, UserLookupProvider, CredentialInputUpdater {
    private static final String REALM = "myrealm";

    private final KeycloakSession session;
    private final ComponentModel model;

    private final UserRepository repository = new UserRepository();

    public DummyUserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;
    }

    // UserLookupProvider
    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        log.info("getUserById: {}", id);
        String externalId = StorageId.externalId(id);
        return toUserModel(realm, repository.findUserById(externalId));
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        log.info("getUserByUsername: {}", username);
        return null;
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        log.info("getUserByEmail: {}", email);
        if (!realm.getName().equals(REALM)) return null;
        return toUserModel(realm, repository.findUserByEmail(email));
    }

    private UserModel toUserModel(RealmModel realm, User user) {
        if (user == null) {
            return null;
        }
        return new UserAdapter(session, realm, model, user);
    }

    // CredentialInputUpdater

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
        log.info("updateCredential: email={}", user.getEmail());
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
            return false;
        }
        UserCredentialModel cred = (UserCredentialModel) input;

        User u = repository.findUserByEmail(user.getEmail());
        if (u == null) {
            return false;
        }
        return repository.updateCredentials(u, cred.getChallengeResponse());
    }

    @Override
    public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
    }

    @Override
    public Stream<String> getDisableableCredentialTypesStream(RealmModel realm, UserModel user) {
        return Stream.empty();
    }

    @Override
    public void close() {
    }

    // UserQueryProvider

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String query) {
        log.info("searchForUserStream: {}", query);
        return repository.getAllUsers().stream()
                .filter(user -> query.equals("*") || user.getUsername().contains(query) || user.getEmail().contains(query))
                .map(user -> toUserModel(realm, user));
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search, Integer firstResult, Integer maxResults) {
        return searchForUserStream(realm, search);
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {
        log.info("searchForUserStream");
        return repository.getAllUsers().stream()
                .map(user -> toUserModel(realm, user));
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realmModel, GroupModel groupModel, Integer integer, Integer integer1) {
        return Stream.empty();
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realmModel, String s, String s1) {
        return Stream.empty();
    }
}
