// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * API利用権限関連を扱うエンティティ
 *
 * @author nishikawa hayato
 * @version 1.1 2024/12/02
 */

@Data
@Builder
@AllArgsConstructor
public class ApiAuthEntity {

    /**
     * 利用者グループID. <br>
     */
    private String servicerGroupId;

    /**
     * API ID. <br>
     */
    private String apiId;

    /**
     * 概要. <br>
     */
    private String overview;

    /**
     * 更新日時. <br>
     */
    private String updateTime;

}
