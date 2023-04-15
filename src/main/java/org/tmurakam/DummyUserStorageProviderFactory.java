package org.tmurakam;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

import java.util.List;

public class DummyUserStorageProviderFactory implements UserStorageProviderFactory<DummyUserStorageProvider> {

    @Override
    public DummyUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new DummyUserStorageProvider(session, model);
    }

    @Override
    public String getId() {
        return "dummy-user-provider";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create()
                .build();
    }
}
