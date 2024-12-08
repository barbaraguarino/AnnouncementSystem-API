package com.system.announcement.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.announcement.auxiliary.enums.UserRole;
import com.system.announcement.auxiliary.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "TB_USER")
public class User implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String icon;

    @Column(nullable = false)
    private UserType type;

    @Column(nullable = false)
    private float score;

    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private boolean blocked;

    @Column(nullable = false)
    private boolean deleted;

    private Timestamp deletedDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Announcement> announcements = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Favorite> favorites = new HashSet<>();

    public User() {
        this.blocked = false;
        this.deleted = false;
    }

    public User(String email,
                String name,
                UserType type,
                UserRole role) {
        this.email = email;
        this.name = name;
        this.type = type;
        this.blocked = false;
        this.role = role;
        this.deleted = false;
        this.score = 0;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.getRole() == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_MODERATOR"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        else if(this.getRole() == UserRole.MODERATOR)
            return List.of(new SimpleGrantedAuthority("ROLE_MODERATOR"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isBlocked() && !this.isDeleted();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.isDeleted();
    }
}
