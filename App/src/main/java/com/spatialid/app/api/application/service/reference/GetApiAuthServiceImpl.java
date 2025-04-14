// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spatialid.app.api.domain.entity.ApiUserGroupEntity;
import com.spatialid.app.api.domain.entity.GetApiUserGroupEntity;
import com.spatialid.app.api.domain.repository.ApiAuthRepository;
import com.spatialid.app.api.presentation.dto.ApiAuthDto;
import com.spatialid.app.api.presentation.dto.ApiInfoDto;
import com.spatialid.app.api.presentation.dto.GetApiAuthRequestDto;
import com.spatialid.app.api.presentation.dto.GetApiAuthResponseDto;

/**
 * API利用権限参照にてビジネスロジックを担うServiceインターフェースの実装クラス。
 * 
 * @author kawashima naoya
 * @version 1.1 2024/08/29
 */
@Service
public class GetApiAuthServiceImpl implements IGetApiAuthService {

    /**
     * API利用者グループ関連のクエリを扱うRepositoryインターフェース。
     */
    @Autowired
    ApiAuthRepository repository;

    @Override
    public GetApiAuthResponseDto getApiAuth(GetApiAuthRequestDto requestDto) {

        GetApiUserGroupEntity requestEntity = new GetApiUserGroupEntity();

        // DtoからEntityに値を詰め替える
        BeanUtils.copyProperties(requestDto, requestEntity);

        // API利用権限テーブル、API利用者グループテーブル、利用者システムマスタ、APIマスタから対象のデータを取得
        List<ApiUserGroupEntity> responseEntity = repository.selectApiUserGroup(requestEntity);

        // API利用権限参照にてクライアント側に渡す情報を扱うResponse
        GetApiAuthResponseDto responseDto = new GetApiAuthResponseDto();
        // API利用権限情報リスト
        List<ApiAuthDto> apiAuthDtoList = new ArrayList<ApiAuthDto>();

        // 利用者グループIDリスト（判定用）
        List<String> servicerGroupIdList = new ArrayList<String>();
        // apiIdリスト(判定用)
        List<String> apiIdList = null;
        // uriリスト（判定用）
        List<String> uriList = null;

        // 利用者IDリスト
        List<String> servicerIdList = null;

        // API情報リスト
        List<ApiInfoDto> apiInfoDtoList = new ArrayList<ApiInfoDto>();

        // 取得分各DTOにセット
        for (ApiUserGroupEntity entity : responseEntity) {

            // 利用者グループIDリスト（判定用）に利用者グループIDが含まれていない場合
            if (!servicerGroupIdList.contains(entity.getServicerGroupId())) {
                servicerGroupIdList = new ArrayList<String>();
                servicerIdList = new ArrayList<String>();
                apiInfoDtoList = new ArrayList<ApiInfoDto>();
                apiIdList = new ArrayList<String>();
                uriList = new ArrayList<String>();
            }

            // 利用者IDリストに利用者IDが含まれていない場合
            if (!servicerIdList.contains(entity.getServicerId())) {
                // 利用者IDリストに追加
                servicerIdList.add(entity.getServicerId());
            }

            // API情報リスト設定
            if (!apiIdList.contains(entity.getApiId()) && !uriList.contains(entity.getUri())) {
                ApiInfoDto apiInfoDto = new ApiInfoDto();
                apiInfoDto.setApiId(entity.getApiId());
                apiInfoDto.setUri(entity.getUri());
                apiInfoDtoList.add(apiInfoDto);

                // apiとuriリスト（判定用）に追加
                apiIdList.add(entity.getApiId());
                uriList.add(entity.getUri());
            }

            // 利用者グループIDリスト（判定用）に利用者グループIDが含まれていない場合
            if (!servicerGroupIdList.contains(entity.getServicerGroupId())) {
                // API利用権限情報設定
                ApiAuthDto apiAuthDto = new ApiAuthDto();
                // 利用者グループID
                apiAuthDto.setServicerGroupId(entity.getServicerGroupId());
                // API情報リスト
                apiAuthDto.setApiInfoList(apiInfoDtoList);
                // 利用者IDリスト
                apiAuthDto.setServicerIdList(servicerIdList);

                // 利用者グループIDリスト（判定用）に追加
                servicerGroupIdList.add(entity.getServicerGroupId());
                apiAuthDtoList.add(apiAuthDto);
            }
        }

        // レスポンス情報設定
        responseDto.setApiAuthList(apiAuthDtoList);

        return responseDto;
    }

}
