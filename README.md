# KeyCloak User Storage SPI test

This is a sample user storage SPI of keycloak to test password reset feature.

## Build

You need JDK 17, maven 3, and docker.

Add your test account into `UserRepository.USERS`. The values are initialized in the constructor.

Then build SPI jar and docker image.

    $ cd docker
    $ make

## Start keycloak

Then start keycloak in docker

    $ cd docker
    $ make start

Access the keycloak from your browser at http://localhost:8080

Login as administrator using 'admin:admin'.

## Configure keycloak

In administrator console:

- Create 'myrealm' realm.
- Check that you can find users in the realm. Click `Users` and try to search user.
- Configure the realm on `Realm settings`.
  - On `Login` tab, enable `Forgot password`.
  - On `email` tab, set 'From' address, and SMTP server configurations. Then `Test connections`.

## Test password reset

On administrator console, click `Clients`. You can find the account URL on the `account` client.

Now you can access the URL, click `Sign in`, then `Forgot Passowrd?`.
The feature should work.
