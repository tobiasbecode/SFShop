package de.tobiasbecode.sfshop.order.data.repository;

import de.tobiasbecode.sfshop.order.data.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Address, Long> {
}
