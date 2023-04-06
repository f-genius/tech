package ru.shev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shev.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    @Query("select o from Owner o where o.id = :id")
    Owner getById(@Param("owner_id") Integer id);
}
