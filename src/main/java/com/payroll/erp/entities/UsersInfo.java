package com.payroll.erp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USERS_INFO")
public class UsersInfo implements UserDetails {


    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "EMP_CODE")
    private String empCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "OLD_PASSWORD")
    private String oldPassword;

    @Column(name = "CUR_PASSWORD")
    private String curPassword;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "TEMP_OTP")
    private String otp;

    @Column(name = "ISACTIVE")
    private Boolean isActive;

    @Column(name = "CREATED_ON", nullable = false)
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON", nullable = false)
    private Timestamp updatedOn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getPassword() {
        return this.curPassword;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
