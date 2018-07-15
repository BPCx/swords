package de.meisterfuu.sip.portal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by meisterfuu.
 */
@Entity
public class Permission implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "member")
    private Member member;

    @Column(name = "permission", length = 200)
    @Enumerated(EnumType.STRING)
    private PermissionValues permission;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public PermissionValues getPermission() {
        return permission;
    }

    public void setPermission(PermissionValues permission) {
        this.permission = permission;
    }

}
