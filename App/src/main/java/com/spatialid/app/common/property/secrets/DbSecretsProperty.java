// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.common.property.secrets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spatialid.app.common.aws.BaseSecretsValue;
import com.spatialid.app.common.constants.SecretsKeyConstants;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * データベースのシークレット情報を保持するクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/17
 */
@Value
@EqualsAndHashCode(callSuper=true)
@Builder
@Jacksonized
public class DbSecretsProperty extends BaseSecretsValue {
    
    /**
     * dbのホスト名．
     */
    @JsonProperty(SecretsKeyConstants.SECRETS_KEY_YDB_HOST)
    private final String ydbHost;
    
    /**
     * dbの接続先ポート．
     */
    @JsonProperty(SecretsKeyConstants.SECRETS_KEY_YDB_PORT)
    private final String ydbPort;
    
    /**
     * dbの接続先DB名．
     */
    @JsonProperty(SecretsKeyConstants.SECRETS_KEY_YDB_NAME)
    private final String ydbDbName;
    
    /**
     * dbのユーザー名．
     */
    @JsonProperty(SecretsKeyConstants.SECRETS_KEY_YDB_USER)
    private final String ydbUser;
    
    /**
     * dbのパスワード．
     */
    @JsonProperty(SecretsKeyConstants.SECRETS_KEY_YDB_PASSWORD)
    private final String ydbPassword;
    
}
