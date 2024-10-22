package fr.afpa.orm.web.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.afpa.orm.dto.ClientDto;
import fr.afpa.orm.entities.Account;
import fr.afpa.orm.entities.Client;
import fr.afpa.orm.entities.Insurance;
import fr.afpa.orm.repositories.ClientRepository;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    private final ClientRepository clientRepository;

    public ClientRestController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("")
    public Iterable<ClientDto> getAll() {
        Iterable<Client> clients = clientRepository.findAll();
        List<ClientDto> clientDtos = new ArrayList<>();
        for (Client client : clients) {
            List<Account> accounts = client.getAccounts();
            Set<Insurance> insurances = client.getInsurances();
            List<Long> accountID = accounts.stream()
                    .map(Account::getId)
                    .collect(Collectors.toList());
            String insuranceType = insurances.stream()
                    .map(insurance -> insurance.getName().name())
                    .collect(Collectors.joining(", "));
            ClientDto clientDto = new ClientDto(client.getId(), client.getFirstName(), client.getLastName(),
                    client.getEmail(), client.getBirthdate(), accountID, insuranceType);
            clientDtos.add(clientDto);
        }
        return clientDtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getOne(@PathVariable UUID id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            List<Account> accounts = client.getAccounts();
            Set<Insurance> insurances = client.getInsurances();
            List<Long> accountID = accounts.stream()
                    .map(Account::getId)
                    .collect(Collectors.toList());
            String insuranceType = insurances.stream()
                    .map(insurance -> insurance.getName().name())
                    .collect(Collectors.joining(", "));
            ClientDto clientDto = new ClientDto(client.getId(), client.getFirstName(), client.getLastName(),
                    client.getEmail(), client.getBirthdate(), accountID, insuranceType);
            return new ResponseEntity<>(clientDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable UUID id, @RequestBody Client updatedClient) {
        Optional<Client> existingClientOpt = clientRepository.findById(id);

        if (existingClientOpt.isPresent()) {
            Client existingClient = existingClientOpt.get();

            // Mettez à jour les champs de l'client
            existingClient.setFirstName(updatedClient.getFirstName());
            existingClient.setLastName(updatedClient.getLastName());
            existingClient.setEmail(updatedClient.getEmail());
            existingClient.setBirthdate(updatedClient.getBirthdate());
            // Ajoutez d'autres mises à jour nécessaires ici.

            // Sauvegarde des modifications
            clientRepository.save(existingClient);

            return ResponseEntity.ok(existingClient);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable UUID id, HttpServletResponse response) {
        Optional<Client> client = clientRepository.findById(id);

        if (client.isPresent()) {
            clientRepository.deleteById(id);
            response.setStatus(HttpStatus.NO_CONTENT.value()); // Statut 204 (No Content)
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value()); // Statut 404 si le compte n'est pas trouvé
        }
    }
}
