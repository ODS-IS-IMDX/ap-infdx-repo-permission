// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.update;

import java.time.LocalDateTime;
import java.util.List;

import com.spatialid.app.api.domain.entity.ApiAuthEntity;
import com.spatialid.app.api.presentation.dto.UpdApiAuthRequestDto;

/**
 * API利用権限更新API用のServiceインターフェース。
 * 
 * @author kawashima naoya
 * @version 1.1 2024/12/12
 */
public interface IUpdApiAuthService {

    /**
     * リクエストされた利用者システムID・利用者グループIDがDB内に存在するかを確かめるメソッド.
     * 
     * @param requestDto      API利用権限が受け取ったリクエストDTO.
     * @param servicerGroupId 利用者グループID。
     * @return APIマスタのデータ存否
     */
    boolean isMatchCount(UpdApiAuthRequestDto requestDto, String servicerGroupId);

    /**
     * リクエストされた利用者システムID・利用者グループIDがDB内に存在するかを確かめるメソッド.
     * 
     * @param requestDto      API利用権限が受け取ったリクエストDTO.
     * @param servicerGroupId 利用者グループID。
     * @return API利用者グループのデータ存否
     */
    boolean isDataExist(UpdApiAuthRequestDto requestDto, String servicerGroupId);

    /**
     * リクエストされた利用者システムID・利用者グループIDがDB内に存在するかを確かめるメソッド.
     * 
     * @param servicerGroupId 利用者グループID。
     * @return API利用権限のデータ件数.
     */
    int countRegisteredData(String servicerGroupId);

    /**
     * 更新処理に使用するEntityクラスを設定するメソッド.
     * 
     * @param requestDto      API利用権限更新が受け取ったリクエストDTO.
     * @param servicerGroupId 利用者グループID。
     * @return API利用権限リスト.
     */
    List<ApiAuthEntity> setEntity(UpdApiAuthRequestDto requestDto, String servicerGroupId);

    /**
     * 利用者グループIDを元に、登録されている最新の更新日時を取得するメソッド.
     * 
     * @param servicerGroupId 利用者グループID.
     * @return 最新更新日時
     */
    public LocalDateTime getLatestUpdateTime(String servicerGroupId);

    /**
     * API利用権限を更新し、排他チェックを行うメソッド.
     * 
     * @param apiAuthEntityList API利用権限リスト.
     * @param servicerGroupId 利用者グループID.
     * @param dataCount 該当データ件数.
     * @param latestUpdateTime 最新更新日時.
     */
    void updApiUserAuth(List<ApiAuthEntity> apiAuthEntityList, String servicerGroupId, int dataCount,
            LocalDateTime latestUpdateTime);

}
