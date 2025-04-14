// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spatialid.app.common.aws.AwsSecretManager;
import com.spatialid.app.common.property.secrets.DbSecretsProperty;

/**
 * シークレット情報を保持するオブジェクトをBean登録する設定クラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/18
 */
@Configuration
public class SecretsPropertyConfig {
    
    private final String secretName;
    
    public SecretsPropertyConfig(@Value("${cloud.aws.secretmanager.secretname}") String secretName) {
        
        this.secretName = secretName;
        
    }
    
    @Bean
    public DbSecretsProperty dbSecretsProperty() throws Exception {
        
        final DbSecretsProperty dbSecretsProperty = AwsSecretManager.getSecretsValue(secretName, DbSecretsProperty.class);
        
        return dbSecretsProperty;
        
    }
    
}
