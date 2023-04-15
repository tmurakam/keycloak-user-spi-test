package org.tmurakam;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;

import java.util.stream.Stream;

public class DummyUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputUpdater {
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
        //String externalId = StorageId.externalId(id);
        return null;
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        return null;
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
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
}
