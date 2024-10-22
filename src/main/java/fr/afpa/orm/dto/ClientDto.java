package fr.afpa.orm.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClientDto(UUID id, String firstName, String lastName, String email, LocalDate birthdate, List<Long> accountID, String insuranceType) {
    
}
