package tk.omi.model;

import javax.persistence.Entity;

@Entity
public class User extends BaseEntityId {

    private String username;
    private String password;

}
