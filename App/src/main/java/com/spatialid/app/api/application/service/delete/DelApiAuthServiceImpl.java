// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.delete;

import org.springframework.stereotype.Service;

import com.spatialid.app.api.domain.entity.DelApiAuthEntity;
import com.spatialid.app.api.domain.repository.ApiAuthRepository;

/**
 * API利権限削除にてビジネスロジックを担うServiceインターフェースの実装クラス。
 * 
 * @author kawashima naoya
 * @version 1.1 2024/12/12
 */
@Service
public class DelApiAuthServiceImpl implements IDelApiAuthService {

    /**
     * API利用権限関連のクエリを扱うRepositoryインターフェース。
     */
    private final ApiAuthRepository apiAuthRepository;

    /**
     * API利用権限削除のエンティティ。
     */
    private final DelApiAuthEntity delApiAuthEntity;

    /**
     * コンストラクタインジェクション
     */
    public DelApiAuthServiceImpl(ApiAuthRepository apiAuthRepository, DelApiAuthEntity delApiAuthEntity) {
        this.apiAuthRepository = apiAuthRepository;
        this.delApiAuthEntity = delApiAuthEntity;
    }

    @Override
    public int delApiAuth(String servicerGroupId) {

        delApiAuthEntity.setServicerGroupId(servicerGroupId);

        // 削除した件数を取得
        int deletedCount = apiAuthRepository.delApiAuth(delApiAuthEntity.getServicerGroupId());

        return deletedCount;
    }
}
