package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private static final List<Flight> flights = new ArrayList<>();

    static {
        flights.add(new Flight(1L, "Kyiv", "London", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 200.0, "KL123", LocalDateTime.now()));
        flights.add(new Flight(2L, "Paris", "New York", LocalDateTime.now(), LocalDateTime.now().plusHours(8), 500.0, "PN456", LocalDateTime.now()));
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flights;
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flights.stream()
                .filter(flight -> flight.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Flight not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Flight createFlight(@RequestBody Flight flight) {
        flight.setId((long) (flights.size() + 1));
        flight.setCreationDate(LocalDateTime.now());
        flights.add(flight);
        return flight;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFlight(@PathVariable Long id) {
        flights.removeIf(flight -> flight.getId().equals(id));
    }
}
