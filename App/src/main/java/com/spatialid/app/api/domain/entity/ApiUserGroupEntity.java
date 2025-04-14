// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.domain.entity;

import lombok.Data;

/**
 * API利用者グループ情報を扱うEntity。
 * 
 * @author satou keita
 * @version 1.1 2024/07/26
 */
@Data
public class ApiUserGroupEntity {

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
