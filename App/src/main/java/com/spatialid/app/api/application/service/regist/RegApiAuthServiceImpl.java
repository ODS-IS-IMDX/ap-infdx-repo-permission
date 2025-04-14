// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.regist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spatialid.app.api.domain.entity.RegApiAuthEntity;
import com.spatialid.app.api.domain.repository.ApiAuthRepository;
import com.spatialid.app.api.presentation.dto.RegApiAuthRequestDto;

/**
 * API利用権限登録にてビジネスロジックを担うServiceクラス。
 * 
 * @author satou keita
 * @version 1.1 2024/07/29
 */
@Service
public class RegApiAuthServiceImpl implements IRegApiAuthService {

    /**
     * API利用権限関連のクエリを扱うRepositoryインターフェース。
     */
    private final ApiAuthRepository apiAuthRepository;

    public RegApiAuthServiceImpl(ApiAuthRepository regapiAuthRepository) {

        this.apiAuthRepository = regapiAuthRepository;
    }

    @Override
    public boolean isMatchCount(RegApiAuthRequestDto regApiAuthRequestDto) {

        List<String> apiIdList = regApiAuthRequestDto.getApiIdList();

        int count = apiAuthRepository.countApiId(apiIdList);

        // リクエストに含まれるAPI IDをもとに、APIマスタ一致する件数を取得する.
        return count == apiIdList.size();

    }

    @Override
    public boolean isDataExist(String servicerGroupId) {

        // リクエストに含まれるAPI利用者グループデータをもとに、API利用者グループデータが存在するかを確認する。
        return apiAuthRepository.isCheckServicerGroupId(servicerGroupId);

    }

    @Override
    public List<RegApiAuthEntity> setEntity(RegApiAuthRequestDto regApiGroupsRequestDto, String servicerGroupId) {

        List<RegApiAuthEntity> entityList = new ArrayList<>();

        // API ID（配列）の要素毎に１セットのEntityを作成し、リストに追加する
        for (String apiId : regApiGroupsRequestDto.getApiIdList()) {

            RegApiAuthEntity regApiAuthEntity = RegApiAuthEntity.builder().servicerGroupId(servicerGroupId)
                    .apiIdList(apiId).overview(regApiGroupsRequestDto.getOverview()).build();

            entityList.add(regApiAuthEntity);

        }

        return entityList;
    }

    @Override
    public void regApiAuth(List<RegApiAuthEntity> entityList) {

        apiAuthRepository.regApiAuth(entityList);
    }
}
