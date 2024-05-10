package dit.hua.distributedSystems.project.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Application {

    //Δήλωση των attributes της οντότητας Application. Δήλωση των setters και getters και των αντίστοιχων constructor.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @NotNull
    @Column(name="production_amount")
    private double productionAmount;

    @NotBlank
    @Size(max=40)
    @Column(name="type_of_production")
    private String typeOfProduction;

    @NotBlank
    @Size(max=40)
    @Column(name="category_of_production")
    private String categoryOfProduction;

    @NotBlank
    @Size(max=40)
    @Column(name="production_location")
    private String productionLocation;

    @NotNull
    @Column(name="damaged_production_amount")
    private double damagedProductionAmount;

    @Column(name="decision")
    private String decision;

    @NotNull
    @Column(name="longitude")
    private double longitude;

    @NotNull
    @Column(name="latitude")
    private double latitude;

    @Column(name="compensationAmount")
    private double compensationAmount;

    //Ένας χρήστης(αγρότης) μπορεί να πραγματοποιήσει πολλές αιτήσεις και μια αίτηση ανήκει σε έναν αγρότη.
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MUser user;

    public Application(Integer id, double productionAmount, String typeOfProduction, String categoryOfProduction, String productionLocation, double damagedProductionAmount, String decision, double longitude, double latitude, double compensationAmount, MUser user) {
        this.id = id;
        this.productionAmount = productionAmount;
        this.typeOfProduction = typeOfProduction;
        this.categoryOfProduction = categoryOfProduction;
        this.productionLocation = productionLocation;
        this.damagedProductionAmount = damagedProductionAmount;
        this.decision = decision;
        this.longitude = longitude;
        this.latitude = latitude;
        this.compensationAmount = compensationAmount;
        this.user = user;
    }

    public Application(){
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", productionAmount=" + productionAmount +
                ", typeOfProduction='" + typeOfProduction + '\'' +
                ", categoryOfProduction='" + categoryOfProduction + '\'' +
                ", productionLocation='" + productionLocation + '\'' +
                ", damagedProductionAmount=" + damagedProductionAmount +
                ", decision='" + decision + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", compensationAmount=" + compensationAmount +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getProductionAmount() {
        return productionAmount;
    }

    public void setProductionAmount(double productionAmount) {
        this.productionAmount = productionAmount;
    }

    public String getTypeOfProduction() {
        return typeOfProduction;
    }

    public void setTypeOfProduction(String typeOfProduction) {
        this.typeOfProduction = typeOfProduction;
    }

    public String getCategoryOfProduction() {
        return categoryOfProduction;
    }

    public void setCategoryOfProduction(String categoryOfProduction) {
        this.categoryOfProduction = categoryOfProduction;
    }

    public String getProductionLocation() {
        return productionLocation;
    }

    public void setProductionLocation(String productionLocation) {
        this.productionLocation = productionLocation;
    }

    public double getDamagedProductionAmount() {
        return damagedProductionAmount;
    }

    public void setDamagedProductionAmount(double damagedProductionAmount) {
        this.damagedProductionAmount = damagedProductionAmount;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(double compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public MUser getUser() {
        return user;
    }

    public void setUser(MUser user) {
        this.user = user;
    }
}
