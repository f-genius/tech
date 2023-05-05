package ru.shev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shev.models.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}
