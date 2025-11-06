package org.example.cyberapp.repository;

import org.example.cyberapp.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    // Example custom query for search/filter
    Page<Student> findByLastNameContainingAndEnrollmentDateBetween(
            String lastName, LocalDate start, LocalDate end, Pageable pageable
    );
}