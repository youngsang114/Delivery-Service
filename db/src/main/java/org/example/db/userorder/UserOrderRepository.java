package org.example.db.userorder;

import org.example.db.userorder.UserOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity,Long> {
}
