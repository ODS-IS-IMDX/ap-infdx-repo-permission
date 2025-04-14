// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spatialid.app.api.constants.ApiAuthConstants;
import com.spatialid.app.api.domain.entity.ApiAuthEntity;
import com.spatialid.app.api.domain.repository.ApiAuthRepository;
import com.spatialid.app.api.presentation.dto.UpdApiAuthRequestDto;
import com.spatialid.app.common.exception.subexception.ExclusiveErrorException;

/**
 * API利用権限更新にてビジネスロジックを担うServiceインターフェースの実装クラス。
 * 
 * @author kawashima naoya
 * @version 1.1 2024/12/12
 */
@Service
public class UpdApiAuthServiceImpl implements IUpdApiAuthService {

    /**
     * API利用者権限APIのデータ操作を管理するRepositoryインターフェース.
     */
    private final ApiAuthRepository updApiAuthRepository;

    /**
     * コンストラクタインジェクション.
     * 
     * @param updApiAuthRepository
     */
    public UpdApiAuthServiceImpl(ApiAuthRepository updApiAuthRepository) {
        this.updApiAuthRepository = updApiAuthRepository;
    }

    @Override
    public boolean isMatchCount(UpdApiAuthRequestDto requestDto, String servicerGroupId) {

        // 取得したデータ件数を格納
        int count = updApiAuthRepository.countApiId(requestDto.getApiIdList());

        List<String> apiIdList = requestDto.getApiIdList();

        // リクエストされた API ID の数と取得したデータの件数が一致するか確認
        return count == apiIdList.size();
    }

    @Override
    public boolean isDataExist(UpdApiAuthRequestDto requestDto, String servicerGroupId) {

        // API利用者グループに該当データがあるか確認
        boolean isDataExistInApiUserGroup = updApiAuthRepository.isCheckServicerGroupId(servicerGroupId);

        return isDataExistInApiUserGroup;

    }

    @Override
    public int countRegisteredData(String servicerGroupId) {

        // API利用権限にある該当データの件数を取得
        int dataCount = updApiAuthRepository.countByServicerGroupId(servicerGroupId);

        return dataCount;

    }

    @Override
    public List<ApiAuthEntity> setEntity(UpdApiAuthRequestDto requestDto, String servicerGroupId) {
        List<ApiAuthEntity> apiAuthEntityList = new ArrayList<>();

        // 現在の日時を指定されたフォーマットで取得
        String updataTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(ApiAuthConstants.UPDATE_TIME_FORMAT));

        // ApiIdListの件数分、Entityオブジェクトを作成しリストに追加
        for (String apiId : requestDto.getApiIdList()) {
            ApiAuthEntity apiAuthEntity = ApiAuthEntity.builder()
                    .servicerGroupId(servicerGroupId)
                    .apiId(apiId)
                    .overview(requestDto.getOverview())
                    .updateTime(updataTime)
                    .build();
            apiAuthEntityList.add(apiAuthEntity);
        }

        return apiAuthEntityList;
    }

    @Override
    public LocalDateTime getLatestUpdateTime(String servicerGroupId) {

        // 登録されている最新の更新日時を取得する.
        LocalDateTime latestUpdateTime = updApiAuthRepository.getLatestUpdateTime(servicerGroupId);

        return latestUpdateTime;
    }

    @Override
    @Transactional
    public void updApiUserAuth(List<ApiAuthEntity> apiAuthEntityList, String servicerGroupId, int dataCount,
            LocalDateTime latestUpdateTime) {

        // 利用者グループIDと最新更新日時を元に、API利用権限テーブルから該当データを削除する.
        int delResult = updApiAuthRepository.delApiAuthBeforeLatestUpdate(servicerGroupId, latestUpdateTime);

        // 削除件数と事前に取得した該当データ件数が異なる場合、排他例外を送出する.
        if (delResult != dataCount) {

            throw new ExclusiveErrorException();

            // 削除件数と事前に取得した該当データ件数が等しい場合、エンティティ情報を登録する.
        } else {
            
            updApiAuthRepository.updRegApiAuth(apiAuthEntityList);

        }

    }

}
