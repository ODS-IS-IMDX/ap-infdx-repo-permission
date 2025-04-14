// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.domain.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * API利用権限関連のリクエスト情報を扱うエンティティ
 *
 * @author kawahima naoya
 * @version 1.1 2024/12/12
 */

@Data
@Component
public class DelApiAuthEntity {

    /**
     * 利用者グループID. <br>
     */
    private String servicerGroupId;
}
