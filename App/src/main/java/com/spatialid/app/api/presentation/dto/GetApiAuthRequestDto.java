// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto;

import lombok.Data;

/**
 * API利用権限参照にてクライアント側から受け取るrequest。
 * 
 * @author ukai jun
 * @version 1.1 2024/08/29
 */
@Data
public class GetApiAuthRequestDto {

    /** 利用者システムID */
    private String servicerId;

    /** 利用者グループID */
    private String servicerGroupId;

    /** API ID */
    private String apiId;

    /** URI */
    private String uri;

}
