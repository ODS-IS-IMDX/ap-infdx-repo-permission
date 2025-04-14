// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * API利用権限更新にてクライアント側から受け取るrequest。
 * 
 * @author kawashima naoya 
 * @version 1.1 2024/12/12
 */
@Data
public class UpdApiAuthRequestDto {

  /** API ID */
  @NotEmpty
  private List<@NotBlank String> apiIdList;

  /** 概要 */
  @Size(max = 1000)
  private String overview;
}
