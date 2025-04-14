// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spatialid.app.api.domain.entity.ApiAuthEntity;
import com.spatialid.app.api.domain.entity.ApiUserGroupEntity;
import com.spatialid.app.api.domain.entity.GetApiUserGroupEntity;
import com.spatialid.app.api.domain.entity.RegApiAuthEntity;

/**
 * API利用権限関連の処理を扱うRepositoryインターフェース。
 *
 * @author nishikawa hayato
 * @version 1.1 2024/12/02
 */

@Mapper
public interface ApiAuthRepository {

    /**
     * API IDをもとにAPIマスタの該当件数の確認
     */
    int countApiId(List<String> apiIdList);

    /**
     * 利用者グループIDをもとにAPI利用者グループテーブルの存在確認
     * 
     */
    boolean isCheckServicerGroupId(String servicerGroupId);
    
    /**
     * 利用者グループIDをもとにAPI利用権限テーブルの存在確認
     * 
     */
    public boolean isServicerGroupIdInApiAuth(String servicerGroupId);

    /**
     * API利用権限テーブルにデータを登録
     * 
     */
    void regApiAuth(List<RegApiAuthEntity> entityList);

    /**
     * API利用者グループ情報を取得
     * 
     * @param entity API利用者グループ参照用リクエストエンティティ
     * @return API利用者グループ情報リスト
     */
    List<ApiUserGroupEntity> selectApiUserGroup(GetApiUserGroupEntity entity);

    /**
     * 利用者グループIDをもとにAPI利用権限のデータを削除
     * 
     */
    public int delApiAuth(String servicerGroupId);
    
    /**
     * 利用者グループIDを元にAPI利用権限のデータを取得し、最新更新日時以前のデータを削除する
     * 
     */
    public int delApiAuthBeforeLatestUpdate(String servicerGroupId, LocalDateTime latestUpdateTime);

    /**
     * API利用権限のデータを更新
     * 
     */
    public void updRegApiAuth(List<ApiAuthEntity> apiAuthEntityList);

    /**
     * 利用者グループIDを元にAPI利用権限データの件数を取得
     * 
     */
    public int countByServicerGroupId(String servicerGroupId);

    /**
     * API利用権限の最新の更新日時を取得
     * 
     */
    public LocalDateTime getLatestUpdateTime(String servicerGroupId);

}
