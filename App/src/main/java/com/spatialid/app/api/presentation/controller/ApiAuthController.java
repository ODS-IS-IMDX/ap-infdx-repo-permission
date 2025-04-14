// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spatialid.app.api.application.service.delete.IDelApiAuthService;
import com.spatialid.app.api.application.service.reference.IGetApiAuthService;
import com.spatialid.app.api.application.service.regist.IRegApiAuthService;
import com.spatialid.app.api.application.service.update.IUpdApiAuthService;
import com.spatialid.app.api.constants.ApiAuthConstants;
import com.spatialid.app.api.domain.entity.ApiAuthEntity;
import com.spatialid.app.api.domain.entity.RegApiAuthEntity;
import com.spatialid.app.api.presentation.dto.GetApiAuthRequestDto;
import com.spatialid.app.api.presentation.dto.GetApiAuthResponseDto;
import com.spatialid.app.api.presentation.dto.RegApiAuthRequestDto;
import com.spatialid.app.api.presentation.dto.UpdApiAuthRequestDto;
import com.spatialid.app.common.exception.subexception.ExclusiveErrorException;
import com.spatialid.app.common.exception.subexception.NotFoundException;
import com.spatialid.app.common.exception.subexception.ParamErrorException;

import io.micrometer.common.util.StringUtils;

/**
 * API利用権限関連のAPIを管理するControllerクラス。
 * 
 * @author kawashima naoya
 * @version 1.1 2024/12/12
 */
@RestController
@RequestMapping(ApiAuthConstants.BASE_URI)

public class ApiAuthController {

    /**
     * API利用権限の取得メソッドを管理するServiceインターフェース。
     */
    private final IGetApiAuthService iGetApiAuthService;

    /**
     * API利用権限の登録メソッドを管理するServiceインターフェース。
     */
    private final IRegApiAuthService iRegApiAuthService;

    /**
     * API利用権限の更新メソッドを管理するServiceインターフェース。
     */
    private final IUpdApiAuthService iUpdApiAuthService;

    /**
     * API利用権限の削除メソッドを管理するServiceインターフェース。
     */
    private final IDelApiAuthService iDelApiAuthService;

    /*
     * * コンストラクタインジェクション
     * 
     */
    public ApiAuthController(IGetApiAuthService iGetApiAuthService, IRegApiAuthService iRegApiAuthService,
            IUpdApiAuthService iUpdApiAuthService, IDelApiAuthService iDelApiAuthService) {

        this.iGetApiAuthService = iGetApiAuthService;
        this.iRegApiAuthService = iRegApiAuthService;
        this.iUpdApiAuthService = iUpdApiAuthService;
        this.iDelApiAuthService = iDelApiAuthService;
    }

    /**
     * API利用権限参照API
     * 
     * @param requestDto API利用権限参照用リクエストDto
     * @return DBから取得したレスポンス
     */
    @GetMapping(ApiAuthConstants.GET_URI)
    public ResponseEntity<GetApiAuthResponseDto> getApiAuth(@ModelAttribute GetApiAuthRequestDto requestDto)
            throws Exception {

        boolean isServicerId = StringUtils.isEmpty(requestDto.getServicerId());
        boolean isUri = StringUtils.isEmpty(requestDto.getUri());

        // リクエストパラメータチェック
        if ((isServicerId && isUri) || (!isServicerId && !isUri)) {
            Map<String, String> msgInfo = new HashMap<String, String>();
            msgInfo.put("servicerId", requestDto.getServicerId());
            msgInfo.put("uri", requestDto.getUri());

            throw new ParamErrorException(msgInfo);
        }

        // Responseデータを取得
        GetApiAuthResponseDto responseDto = iGetApiAuthService.getApiAuth(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * API利用権限登録API
     * 
     * @param requestDto API利用権限登録用リクエストDto
     * @return DBから取得したレスポンス
     */
    @PostMapping({ ApiAuthConstants.REG_URI, ApiAuthConstants.GET_URI + "/" })
    public ResponseEntity<Void> regApiAuth(@PathVariable(required = false) String servicerGroupId,
            @Validated @RequestBody RegApiAuthRequestDto requestDto, BindingResult br) {

        // パラメータチェック
        if (StringUtils.isEmpty(servicerGroupId) || servicerGroupId.length() > 5) {

            Map<String, String> checkServicerGroupId = new HashMap<String, String>();

            checkServicerGroupId.put("servicerGroupId", servicerGroupId);

            throw new ParamErrorException(checkServicerGroupId);

        } else if (br.hasErrors()) {

            throw new ParamErrorException(br);
        }

        requestDto.setServicerGroupId(servicerGroupId);

        // 2.データ存在確認
        // APIマスタデータ件数確認
        boolean isMatchCount = iRegApiAuthService.isMatchCount(requestDto);

        // リクエストに含まれるAPI IDの件数とAPIマスタの件数が一致しない場合パラメータエラーを返す。
        if (isMatchCount == false) {

            Map<String, String> inValidValue = Map.of("apiIdList", String.valueOf(requestDto.getApiIdList()));

            throw new ParamErrorException(inValidValue);
        }

        // 存在確認
        boolean isDataExists = iRegApiAuthService.isDataExist(servicerGroupId);

        // リクエストに含まれるAPI利用者グループデータが存在しない場合パラメータエラーを返す。
        if (isDataExists == false) {

            Map<String, String> inValidValue = Map.of("servicerGroupId", String.valueOf(servicerGroupId));

            throw new NotFoundException(inValidValue);

        }

        // 3.エンティティ設定
        List<RegApiAuthEntity> regEntity = iRegApiAuthService.setEntity(requestDto, servicerGroupId);

        // 4.API利用権限登録
        iRegApiAuthService.regApiAuth(regEntity);

        // 5.レスポンスデータ項目設定
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * API利用権限更新API
     * 
     * @param requestDto      API利用権限更新APIが受け取るリクエストDTO.
     * @param servicerGroupId 利用者グループID.
     * @param br              リクエストをバインドした結果のエラー情報を格納するオブジェクト.
     * @return ResponseEntity<Void> HTTPステータスコード（204 No Content）.
     */
    @PutMapping({ ApiAuthConstants.UPD_URI, ApiAuthConstants.GET_URI + "/" })
    public ResponseEntity<Void> updApiAuth(@PathVariable(required = false) String servicerGroupId,
            @Validated @RequestBody UpdApiAuthRequestDto requestDto, BindingResult br) {

        // パラメータチェック
        if (StringUtils.isEmpty(servicerGroupId)) {

            Map<String, String> item = new HashMap<>();

            item.put("servicerGroupId", servicerGroupId);

            throw new ParamErrorException(item);

        } else if (br.hasErrors()) {
            throw new ParamErrorException(br);
        }

        // APIマスタ：データの存在確認
        boolean isDataExistInApiMst = iUpdApiAuthService.isMatchCount(requestDto, servicerGroupId);
        if (!isDataExistInApiMst) {
            Map<String, String> hasNoData = Map.of("apiIdList", String.valueOf(requestDto.getApiIdList()));
            throw new ParamErrorException(hasNoData);
        }

        // API利用者グループ：データの存在確認
        boolean isDataExistInApiUserGroup = iUpdApiAuthService.isDataExist(requestDto, servicerGroupId);
        if (!isDataExistInApiUserGroup) {
            Map<String, String> hasNoData = Map.of("servicerGroupId", String.valueOf(servicerGroupId));
            throw new NotFoundException(hasNoData);
        }

        // API利用権限：データの存在確認
        int dataCount = iUpdApiAuthService.countRegisteredData(servicerGroupId);

        if (dataCount == 0) {

            Map<String, String> hasNoData = Map.of("servicerGroupId", String.valueOf(servicerGroupId));

            throw new NotFoundException(hasNoData);
        
        }

        // リクエストデータをエンティティに設定
        List<ApiAuthEntity> setApiAuthEntityList = iUpdApiAuthService.setEntity(requestDto, servicerGroupId);

        // 最新更新日時を取得
        LocalDateTime latestUpdateTime = iUpdApiAuthService.getLatestUpdateTime(servicerGroupId);

        // API利用権限のデータの更新と排他チェック
        try {

            iUpdApiAuthService.updApiUserAuth(setApiAuthEntityList, servicerGroupId, dataCount, latestUpdateTime);

        } catch (Exception e) {

            if (e.getMessage() != null && e.getMessage()
                    .contains(ApiAuthConstants.CONFLICT_ERROR_MESSAGE)) {

                throw new ExclusiveErrorException();

            } else {

                throw e;
            }
        }
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    /**
     * API利用権限削除API
     * 
     * @param servicerGroupId 利用者グループID
     * @return ResponseEntity<Void> HTTPステータスコード（204 No Content）.
     */
    @DeleteMapping({ ApiAuthConstants.DEL_URI, ApiAuthConstants.GET_URI + "/" })
    public ResponseEntity<Void> delApiAuth(@PathVariable(required = false) String servicerGroupId) {

        // パラメータチェック
        if (StringUtils.isEmpty(servicerGroupId)) {
            Map<String, String> item = new HashMap<>();

            item.put("servicerGroupId", servicerGroupId);

            throw new ParamErrorException(item);
        }

        // 利用者グループIDをもとに一致データを削除
        int deletedCount = iDelApiAuthService.delApiAuth(servicerGroupId);

        if (deletedCount == 0) {
            Map<String, String> noDeletedData = Map.of("servicerGroupId", servicerGroupId);
            throw new NotFoundException(noDeletedData);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();

    }
}
