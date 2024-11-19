package com.sonng2k.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    private CashCard findCashCard(Long requestedId, Principal principal) {
        return cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
        final CashCard cashCard = findCashCard(requestedId, principal);
        if (cashCard != null) {
            return ResponseEntity.ok(cashCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * If u don't remove the existing findAll() Controller method, we'll have two
     * methods mapped to the same
     * endpoint. Spring detects this error at runtime, during the Spring startup
     * process.
     *
     * @GetMapping()
     * public ResponseEntity<Iterable<CashCard>> findAll() {
     * return ResponseEntity.ok(cashCardRepository.findAll());
     * }
     */

    /*
     * Pageable is yet another object that Spring Web provides for us. Since we
     * specified the URI
     * parameters of page=0&size=1 in the test, pageable will contain the values we
     * need.
     *
     * The .getSort() method extracts the `sort` query parameter from the request
     * URI.
     *
     * The getSortOr() method provides default values for the `page`, `size`, and
     * `sort` parameters. The default
     * values come from two different sources:
     * 1. Spring provides the default `page` and `size` values (they are 0 and 20,
     * respectively). Again: we
     * didn't need to explicitly define these defaults. Spring provides them
     * "out of the box".
     * 2. We defined the default `sort` parameter in our own code, by passing a
     * `Sort` object to `getSortOr()`:
     * Sort.by(Sort.Direction.ASC, "amount")
     * The net result is that if any of the three required parameters are not passed
     * to the application, then
     * reasonable defaults will be provided.
     */
    @GetMapping
    public ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        final Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))));
        return ResponseEntity.ok(page.getContent());
    }

    @PostMapping
    public ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest,
            UriComponentsBuilder ucb, Principal principal) {
        final CashCard cashCardWithOwner = new CashCard(null, newCashCardRequest.amount(), principal.getName());
        final CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);
        final URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate,
            Principal principal) {
        final CashCard cashCard = findCashCard(requestedId, principal);
        if (cashCard != null) {
            CashCard updatedCashCard = new CashCard(cashCard.id(), cashCardUpdate.amount(), principal.getName());
            cashCardRepository.save(updatedCashCard);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCashCard(@PathVariable Long id, Principal principal) {
        if (cashCardRepository.existsByIdAndOwner(id, principal.getName())) {
            cashCardRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
