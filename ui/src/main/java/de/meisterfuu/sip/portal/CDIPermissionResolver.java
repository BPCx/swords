package de.meisterfuu.sip.portal;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolver;

import java.util.Collection;

public class CDIPermissionResolver implements PermissionResolver, RolePermissionResolver {
    @Override
    public Permission resolvePermission(String s) {

        return null;
    }

    @Override
    public Collection<Permission> resolvePermissionsInRole(String s) {
        return null;
    }
}
