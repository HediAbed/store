package org.store.domain.enums;

import java.io.Serializable;

public enum RoleType  implements Serializable {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_CLIENT("ROLE_CLIENT");

    String type;

    private RoleType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static RoleType getEnum(String value) {
        for(RoleType roleType : values())
            if(roleType.getType().equalsIgnoreCase(value)) return roleType;
        throw new IllegalArgumentException();
    }
}
