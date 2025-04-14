// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * API利用権限登録にてクライアント側から受け取るrequest。
 * 
 * @author ukai jun
 * @version 1.1 2024/08/29
 */
@Data
public class RegApiAuthRequestDto {

    /** 利用者グループID */
    private String servicerGroupId;

    /** API ID */
    @NotEmpty
    private List<String> apiIdList;

    /** 概要 */
    @Size(max = 1000)
    private String overview;
}
