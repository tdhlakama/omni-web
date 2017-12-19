package tk.omi.model;

import javax.persistence.Entity;

@Entity
public class Role extends BaseEntityId {

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
