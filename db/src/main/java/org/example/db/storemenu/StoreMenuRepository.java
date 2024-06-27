package org.example.db.storemenu;

import org.example.db.storemenu.emuns.StoreMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreMenuRepository extends JpaRepository<StoreMenuEntity,Long> {
    // 유효한 매뉴 체크
    // select * from store_menu where id=? and status =? order by id desc limit 1;
    Optional<StoreMenuEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreMenuStatus status);

    // 특정 가게 매뉴 가졍괴
    // select * from store_menu where store_id =? and status =? order by sequence desc;
    List<StoreMenuEntity> findAllByStoreEntity_IdAndStatusOrderBySequenceDesc(Long storeId, StoreMenuStatus status);
}
