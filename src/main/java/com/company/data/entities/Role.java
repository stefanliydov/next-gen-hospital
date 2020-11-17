package com.company.data.entities;

import com.company.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    private RoleType roleType;

    @ToString.Exclude
    @ManyToMany(mappedBy = "roleTypes")
    private List<User> users;

    @Override
    public String getAuthority() {
        return this.roleType.name();
    }
}
