package tk.omi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends BaseEntityId {

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Long numberOfCustomers;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Role> roles = new HashSet<>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(Long numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
