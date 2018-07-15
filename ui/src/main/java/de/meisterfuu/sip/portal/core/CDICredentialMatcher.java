package de.meisterfuu.sip.portal.core;

import de.meisterfuu.sip.portal.facades.LoginFacade;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import javax.enterprise.inject.spi.CDI;

/**
 * Created by meisterfuu.
 */
public class CDICredentialMatcher implements CredentialsMatcher {


    private LoginFacade loginFacade;

    public CDICredentialMatcher(LoginFacade loginFacade) {
        this.loginFacade = loginFacade;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        return loginFacade.checkPassword(usernamePasswordToken.getUsername(), usernamePasswordToken.getPassword());
    }
}
