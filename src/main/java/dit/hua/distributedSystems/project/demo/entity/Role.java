package dit.hua.distributedSystems.project.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
public class Role {

    //Δήλωση των attributes της οντότητας Role. Δήλωση των setters και getters και των αντίστοιχων constructor.
    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="role")
    private String role;

    //Ένας χρήστης έχει έναν ρόλο, ένας ρόλος ανήκει σε πολλούς χρήστες.
    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<MUser> users;


    public Role(Integer id, String role, List<MUser> users) {
        this.id = id;
        this.role = role;
        this.users = users;
    }

    public Role() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<MUser> getUsers() {
        return users;
    }

    public void setUsers(List<MUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
