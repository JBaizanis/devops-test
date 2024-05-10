package dit.hua.distributedSystems.project.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class MUser {

    //Δήλωση των attributes της οντότητας User. Δήλωση των setters και getters και των αντίστοιχων constructor. Το όνομα της κλάσης είναι MUser και όχι User καθώς
    //στην postgres η λέξη User είναι δεσμευμένη.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @NotBlank
    @Size(max=20)
    @Column(name="username")
    private String username;

    @NotBlank
    @Size(max=120)
    @Column(name="password")
    private String password;

    @NotBlank
    @Size(max=40)
    @Column(name="firstName")
    private String firstName;

    @NotBlank
    @Size(max=40)
    @Column(name="lastName")
    private String lastName;

    @NotBlank
    @Size(max=20)
    @Column(name="phone")
    private String phone;

    @NotBlank
    @Size(max=50)
    @Email
    @Column(name="email")
    private String email;

    @NotBlank
    @Size(max=40)
    @Column(name="address")
    private String address;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Application> applications;


    public MUser(Long id, String username, String password, String firstName, String lastName, String phone, String email, String address, Role role, List<Application> applications) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.applications = applications;
    }

    public MUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
