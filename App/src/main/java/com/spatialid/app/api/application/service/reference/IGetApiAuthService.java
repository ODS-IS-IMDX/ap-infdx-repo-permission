// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.reference;

import com.spatialid.app.api.presentation.dto.GetApiAuthRequestDto;
import com.spatialid.app.api.presentation.dto.GetApiAuthResponseDto;

/**
 * API利用権限参照API用のServiceインターフェース。
 * 
 * @author satou keita
 * @version 1.1 2024/07/29
 */
public interface IGetApiAuthService {

    /**
     * リクエスト情報を元に、API利用権限参照API用のレスポンス情報を取得する。
     * 
     * @param requestDto リクエスト情報
     * 
     * @return レスポンスDto
     */
    GetApiAuthResponseDto getApiAuth(GetApiAuthRequestDto requestDto) throws Exception;

}
