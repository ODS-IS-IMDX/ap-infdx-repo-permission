// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * API利用権限関連のリクエスト情報を扱うエンティティ
 *
 * @author nishikawa hayato
 * @version 1.1 2024/12/02
 */

@Data
@Builder
@AllArgsConstructor
public class RegApiAuthEntity {

    /**
     * 利用者グループID. <br>
     */
    private String servicerGroupId;

    /**
     * API ID. <br>
     */
    private String apiIdList;

    /**
     * 概要. <br>
     */
    private String overview;
}
