package com.sonng2k.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

// `extends CrudRepository` is where we tap into the magic of Spring Data and its data repository pattern.

// When we extend CrudRepository (or other sub-Interfaces of Spring Data's `Repository`), Spring Boot and Spring
// Data work together to automatically generate the CRUD methods that we need to interact with a database.

// For our application, the "domain type" of this repository will be the `CashCard`.

public interface CashCardRepository extends CrudRepository<CashCard, Long>, PagingAndSortingRepository<CashCard, Long> {
    // Spring Data is perfectly capable of generating the SQL for the queries we need, the CashCardRepository
    // now supports filtering CashCard data by owner.
    CashCard findByIdAndOwner(Long id, String owner);

    Page<CashCard> findByOwner(String owner, PageRequest amount);

    // This is another case where Spring Data will generate the implementation of the method below as long
    // as we add it to the Repository. So why not just use the findByIdAndOwner() method and check whether it
    // returns `null`? We could absolutely do that! But, such a call would return extra information (the content
    // of the Cash Card retrieved), so we'd like to avoid it as to not introduce extra complexity.
    boolean existsByIdAndOwner(Long id, String owner);
}
