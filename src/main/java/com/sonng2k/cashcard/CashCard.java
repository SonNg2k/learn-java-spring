package com.sonng2k.cashcard;

import org.springframework.data.annotation.Id;

// When we configure the repository as CrudRepository<CashCard, Long> we indicate that the CashCard's ID is
// Long. However, we still need to tell Spring Data which field is the ID.

// Configure the id as the @Id for the CashCardRepository.
public record CashCard(@Id Long id, Double amount, String owner) {
}
