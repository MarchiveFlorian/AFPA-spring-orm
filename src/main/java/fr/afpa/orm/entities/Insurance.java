package fr.afpa.orm.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public enum Name {
        HOME_INSURANCE, // Assurance habitation
        HEALTH_INSURANCE, // Assurance santé
        LIFE_INSURANCE, // Assurance vie
        CAR_INSURANCE, // Assurance automobile
        SCHOOL_INSURANCE, // Assurance scolaire
        PERSONAL_LIABILITY_INSURANCE, // Responsabilité civile personnelle
        PROFESSIONAL_LIABILITY_INSURANCE // Responsabilité civile professionnelle
    }

    @Enumerated(EnumType.STRING)
    private Name name;

    @ManyToMany(mappedBy = "insurances")
    private Set<Client> clients;

    public Insurance() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "Insurance [id=" + id + ", name=" + name + ", clients=" + clients + "]";
    }

}
