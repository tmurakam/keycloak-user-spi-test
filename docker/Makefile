NAME = keycloak-user-spi-test

PORT_OPTS = -p 8080:8080
VOLUME_OPTS = -v $(PWD)/data:/opt/keycloak/data
ENV=-e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin

JAR = org.tmurakam.keycloak-user-spi-test.jar

all: image

$(JAR):
	(cd .. && mvn package && cp target/$(JAR) docker/)

image: $(JAR)
	docker build -t $(NAME) .

bash:
	docker run -it --rm --entrypoint /bin/bash $(NAME)

start:
	docker run -d $(PORT_OPTS) $(VOLUME_OPTS) $(ENV) $(NAME) start-dev
