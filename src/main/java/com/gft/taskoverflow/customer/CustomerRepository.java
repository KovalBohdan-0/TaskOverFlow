package com.gft.taskoverflow.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
    Optional<Customer> findByEmail(String email);

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM Customer c JOIN c.boards b WHERE b.id = :boardId AND c.id = :customerId) THEN true ELSE false END AS result FROM Customer c")
    Boolean containsBoardByIdAndCustomerId(@Param("boardId") Long boardId, @Param("customerId") Long customerId);
}
