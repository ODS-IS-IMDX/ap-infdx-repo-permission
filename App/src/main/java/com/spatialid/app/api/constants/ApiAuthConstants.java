// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.constants;

/**
 * API利用権限関連のAPIのURIを管理する定数クラス。
 * 
 * @author ukai jun
 * @version 1.1 2024/08/29
 * 
 */
public class ApiAuthConstants {

    /** ApiAuthControllerクラスのBase URI */
    public static final String BASE_URI = "/gen/api/generic/v1";

    /** API利用権限参照APIの URI */
    public static final String GET_URI = "/api-auth";

    /** API利用権限登録APIの URI */
    public static final String REG_URI = "/api-auth/{servicerGroupId}";

    /** API利用権限更新APIの URI */
    public static final String UPD_URI = "/api-auth/{servicerGroupId}";

    /** API利用権限削除APIの URI */
    public static final String DEL_URI = "/api-auth/{servicerGroupId}";

    /** 日付形式チェックで用いる日付フォーマット */
    public static final String UPDATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";
    
    /** トランザクション競合エラーを捕捉する為に使用する文字列. */
    public static final String CONFLICT_ERROR_MESSAGE = "expired or aborted by a conflict: 40001";
}
