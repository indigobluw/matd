package com.thereselindquist.matd.user.authorities;

public enum UserPermissions {
    ADMIN_READ("admin:read"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_WRITE("admin:write"),
    ADMIN_POST("admin:post"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_POST("user:post");

    private final String userPermission;

    UserPermissions(String userPermission) {
        this.userPermission = userPermission;
    }

    public String getUserPermission() {
        return userPermission;
    }
}
