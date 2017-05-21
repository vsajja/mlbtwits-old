package org.mlbtwits.auth

import com.google.inject.Inject
import org.mindrot.jbcrypt.BCrypt
import org.mlbtwits.services.MLBTwitsService
import org.pac4j.core.exception.CredentialsException
import org.pac4j.core.profile.CommonProfile
import org.pac4j.core.util.CommonHelper
import org.pac4j.http.credentials.UsernamePasswordCredentials
import org.pac4j.http.credentials.authenticator.UsernamePasswordAuthenticator
import org.pac4j.http.profile.HttpProfile
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DatabaseUsernamePasswordAuthenticator implements UsernamePasswordAuthenticator {
    final Logger log = LoggerFactory.getLogger(this.class)

    MLBTwitsService mlbTwitsService

    @Inject
    public DatabaseUsernamePasswordAuthenticator(MLBTwitsService mlbTwitsService) {
        this.mlbTwitsService = mlbTwitsService
    }

    @Override
    void validate(UsernamePasswordCredentials credentials) {
        if (credentials == null) {
            throwsException('No credential');
        }

        String username = credentials.getUsername()
        String password = credentials.getPassword()

        if (CommonHelper.isBlank(username)) {
            throwsException('Username cannot be blank')
        }
        if (CommonHelper.isBlank(password)) {
            throwsException('Password cannot be blank')
        }

        def user = mlbTwitsService.getUser(username)
        if(!user) {
            throwsException('Invalid username or password')
        }

        def passwordHash = user.getPassword()
        if(!passwordHash || !BCrypt.checkpw(password, user.getPassword())) {
            throwsException('Invalid username or password')
        }

        final HttpProfile profile = new HttpProfile();
        profile.setId(username);
        profile.addAttribute('user', user)
        credentials.setUserProfile(profile);
    }

    protected void throwsException(final String message) {
        throw new CredentialsException(message);
    }
}
