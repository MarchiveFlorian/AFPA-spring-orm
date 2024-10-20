package fr.afpa.orm.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import fr.afpa.orm.entities.Client;

public interface ClientRepository extends CrudRepository<Client, UUID>{
    
}
