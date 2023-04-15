FROM quay.io/keycloak/keycloak:21.0.2

COPY org.tmurakam.keycloak-user-spi-test.jar /opt/keycloak/providers/

RUN /opt/keycloak/bin/kc.sh build && /opt/keycloak/bin/kc.sh show-config
