package de.meisterfuu.sip.portal;

import de.meisterfuu.sip.portal.core.CDICredentialMatcher;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

public class LoginRealm extends AuthorizingRealm {

    public LoginRealm(){
        this.setCredentialsMatcher(new CDICredentialMatcher());
    }


    @Override
    public boolean supports(AuthenticationToken token) {
        return true;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo();
        PrincipalCollection principals = new SimplePrincipalCollection("dummy", "mockRealm");
        auth.setPrincipals(principals);
        return auth;
    } 

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addStringPermission("admin");
        return simpleAuthorizationInfo;
    }

}