package org.example.api.domain.store.service;

import lombok.AllArgsConstructor;
import org.example.api.common.error.ErrorCode;
import org.example.api.common.exception.ApiException;
import org.example.db.store.StoreEntity;
import org.example.db.store.StoreRepository;
import org.example.db.store.enums.StoreCategory;
import org.example.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // 유효한 스토어 가져오기
    public StoreEntity getStoreWithThrow(Long id){

        Optional<StoreEntity> entity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreStatus.REGISTERED);
        return entity.orElseThrow(
                () -> new ApiException(ErrorCode.NULL_POINT,"id를 통해서 유효한 스토어를 찾지 못했습니다")
        );
    }
    // 스토어 등록
    public StoreEntity register(StoreEntity storeEntity){
        return  Optional.ofNullable(storeEntity)
                .map(it ->{
                    it.setStar(0);
                    it.setStatus(StoreStatus.REGISTERED);
                    // TODO 등록일시 추가하기
                    return storeRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "스토어 등록 중 스토어를 입력받지 못했습니다")
                );
    }

    // 카테고리로 스토어 검색

    public List<StoreEntity> searchByCategory(StoreCategory storeCategory){

        List<StoreEntity> list = storeRepository.findAllByStatusAndCategoryOrderByStarDesc(
                StoreStatus.REGISTERED,
                storeCategory
        );
        return list;
    }

    // 전체 스토어
    public List<StoreEntity> registerStore(){
        List<StoreEntity> list = storeRepository.findAllByStatusOrderByIdDesc(
                StoreStatus.REGISTERED
        );
        return list;
    }
}
