// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.domain.entity;

import lombok.Data;

/**
 * API利用者グループ参照リクエスト情報を扱うエンティティ。
 * 
 * @author kawashima naoya
 * @version 1.1 2024/08/29
 */
@Data
public class GetApiUserGroupEntity {

    /**
     * 利用者システムID. <br>
     */
    private String servicerId;

    /**
     * 利用者グループID. <br>
     */
    private String servicerGroupId;

    /**
     * API ID. <br>
     */
    private String apiId;

    /**
     * URI. <br>
     */
    private String uri;

}
