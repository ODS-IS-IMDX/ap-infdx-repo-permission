// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.regist;

import java.util.List;

import com.spatialid.app.api.domain.entity.RegApiAuthEntity;
import com.spatialid.app.api.presentation.dto.RegApiAuthRequestDto;

/**
 * API利用権限登録関連のDB操作用のServiceインターフェース。
 * 
 * @author satou keita
 * @version 1.1 2024/07/29
 */
public interface IRegApiAuthService {

    /**
     * リクエスト情報を元に、API利用権限情報を登録する。
     * 
     * @param regApiGroupsRequestDto リクエスト情報
     * @param servicerGroupId        利用者グループID
     */
    void regApiAuth(List<RegApiAuthEntity> RegApiAuthEntity);

    /**
     * リクエストに含まれるAPI IDを基にDBのAPIマスタからを検索し、 両者の件数が同数である事を確認するメソッド.
     * 
     * @return
     * 
     */

    boolean isMatchCount(RegApiAuthRequestDto regApiAuthRequestDto);

    /**
     * リクエストに含まれる利用者システムIDをもとに、DBに同値の利用者システムIDの存在を確認するメソッド。
     * 
     */
    boolean isDataExist(String servicerGroupId);

    /**
     * リクエスト項目をAPI利用権限エンティティ情報に設定するメソッド
     * 
     */
    List<RegApiAuthEntity> setEntity(RegApiAuthRequestDto regApiGroupsRequestDto, String servicerGroupId);

}
