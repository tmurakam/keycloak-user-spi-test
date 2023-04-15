package org.tmurakam;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.LegacyUserCredentialManager;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.models.UserModel;
import org.keycloak.storage.ReadOnlyException;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapter;

@Slf4j
public class UserAdapter extends AbstractUserAdapter {
    private final User user;

    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, User user) {
        super(session, realm, model);
        this.storageId = new StorageId(storageProviderModel.getId(), user.getId());
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getFirstName() {
        return user.getFirstName();
    }

    @Override
    public String getLastName() {
        return user.getLastName();
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public SubjectCredentialManager credentialManager() {
        return new LegacyUserCredentialManager(session, realm, this);
    }

    @Override
    public Long getCreatedTimestamp() {
        return user.getCreated();
    }

    // This is required to implement password reset
    @Override
    public void removeRequiredAction(String action) {
        log.info("removeRequiredAction: {}", action);
    }

    // This is required to implement password reset
    @Override
    public void removeRequiredAction(UserModel.RequiredAction action) {
        log.info("removeRequiredAction: {}", action);
    }
}
