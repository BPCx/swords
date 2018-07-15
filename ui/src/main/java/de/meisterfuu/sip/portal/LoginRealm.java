package de.meisterfuu.sip.portal;

import de.meisterfuu.sip.portal.core.CDICredentialMatcher;
import de.meisterfuu.sip.portal.facades.LoginFacade;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import javax.enterprise.inject.spi.CDI;

public class LoginRealm extends AuthorizingRealm {

    LoginFacade loginFacade;

    public LoginRealm(){
        loginFacade = CDI.current().select(LoginFacade.class).get();
        this.setCredentialsMatcher(new CDICredentialMatcher(loginFacade));
//        this.setPermissionResolver(new CDIPermissionResolver());
//        this.setRolePermissionResolver(new CDIPermissionResolver());
        this.setCacheManager(new MemoryConstrainedCacheManager());

    }


    @Override
    public boolean supports(AuthenticationToken token) {
        return (token instanceof UsernamePasswordToken);
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo();
        PrincipalCollection principals = new SimplePrincipalCollection(usernamePasswordToken.getUsername(), "sipRealm");
        auth.setPrincipals(principals);
        return auth;
    } 

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String usernamePrincipal = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

//        loginFacade.getPermissions()

        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addStringPermission("admin");
        return simpleAuthorizationInfo;
    }

}