package org.example.cyberapp.repository;

import org.example.cyberapp.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    // Paginated search by username and enrollment date
    Page<Student> findByUsernameContainingIgnoreCaseAndEnrollmentDateBetween(
            String username,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}
