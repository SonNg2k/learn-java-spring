package com.sonng2k.accessingdatajpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// The Customer objects are annotated as a JPA entity (. Because no @Table annotation exists, it is assumed
// that this entity is mapped to a table named Customer.)

// The default constructor exists only for the sake of JPA. You do not use it directly, so it is designated
// as protected. The other constructor is the one you use to create instances of Customer to be saved to the
// database.

// The Customer object’s `id` property is annotated with @Id so that JPA recognizes it as the object’s ID.
// The `id` property is also annotated with @GeneratedValue to indicate that the ID should be generated
// automatically.

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
