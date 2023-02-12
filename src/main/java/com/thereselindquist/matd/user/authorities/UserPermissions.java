package com.thereselindquist.matd.user.authorities;

public enum UserPermissions {
    ADMIN_CAN_READ("ADMIN:READ"),
    ADMIN_CAN_DELETE("ADMIN:DELETE"),
    ADMIN_CAN_WRITE("ADMIN:WRITE"),
    ADMIN_CAN_POST("ADMIN:POST"),
    USER_CAN_READ("USER:READ"),
    USER_CAN_WRITE("USER:WRITE"),
    USER_CAN_POST("USER:POST");

    private final String userPermission;

    UserPermissions(String userPermission) {
        this.userPermission = userPermission;
    }

    public String getUserPermission() {
        return userPermission;
    }
}
