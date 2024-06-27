package org.example.db.userorder;

import org.example.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity,Long> {

    // 특정 유저의 모든 주문
    // select * from user_order where user_id =? and status =? order by id desc;
    List<UserOrderEntity> findAllByUserEntity_IdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

    // 특정 유저의 모든 주문
    // select * from user_order where user_id =? and status in (?,? ..) order by id desc;
    List<UserOrderEntity> findAllByUserEntity_IdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);


    // 특정 유저의 특정 주문
    // select * from user_order where id =? and status =? and user_id =?;
    Optional<UserOrderEntity> findAllByIdAndStatusAndUserEntity_Id(Long id, UserOrderStatus status, Long userId);

    Optional<UserOrderEntity> findAllByIdAndUserEntity_Id(Long id, Long userId);

}
