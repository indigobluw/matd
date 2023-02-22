package com.thereselindquist.matd.user.authorities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.thereselindquist.matd.user.authorities.UserPermissions.*;

public enum UserRoles {
    ADMIN(Set.of(ADMIN_READ,
            ADMIN_DELETE,
            ADMIN_WRITE,
            ADMIN_POST)),
    USER(Set.of(USER_READ,
            USER_WRITE,
            USER_POST));

    private final Set<UserPermissions> permissionsList;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissionsList = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return permissionsList;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {

        Set<SimpleGrantedAuthority> permissionsSet = getPermissions().stream().map(
                index -> new SimpleGrantedAuthority(index.getUserPermission())
        ).collect(Collectors.toSet());

        permissionsSet.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissionsSet;
    }
}
