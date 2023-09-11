package com.sonng2k.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

// Spring Data JPA focuses on using JPA to store data in a relational database. Its most compelling feature
// is the ability to create repository implementations automatically, at runtime, from a repository interface.

// By extending CrudRepository, CustomerRepository inherits several methods for working with Customer
// persistence, including methods for saving, deleting, and finding Customer entities.

// Spring Data JPA also lets you define other query methods by declaring their method signature. For example,
// CustomerRepository includes the findByLastName() method.

// In a typical Java application, you might expect to write a class that implements CustomerRepository.
// However, that is what makes Spring Data JPA so powerful: You need not write an implementation of the
// repository interface. Spring Data JPA creates an implementation when you run the application. Saving
// objects to and fetching them from a database, all without writing a concrete repository implementation.

// By default, Spring Boot enables JPA repository support and looks in the package (and its subpackages)
// where @SpringBootApplication is located. If your configuration has JPA repository interface definitions
// located in a package that is not visible, you can point out alternate packages by using
// @EnableJpaRepositories and its type-safe basePackageClasses=MyRepository.class parameter.

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
}