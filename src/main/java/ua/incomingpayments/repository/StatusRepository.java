package ua.incomingpayments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.incomingpayments.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Short> {
    Status findStatusByValue(String value);
}
