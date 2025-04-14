// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.common.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spatialid.app.common.property.DbProperty;
import com.spatialid.app.common.property.secrets.DbSecretsProperty;

/**
 * データベースの接続情報を設定するクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/10
 */
@Configuration
public class DbConfig {
    
    /**
     * DB関連のシークレット情報を保持するクラス．
     */
    private final DbSecretsProperty dbSecretsProperty;
    
    /**
     * 機密ではない接続情報を保持するクラス．
     */
    private final DbProperty dbProperty;
    
    public DbConfig(DbSecretsProperty dbSecretsProperty,
            DbProperty dbProperty) {
        
        this.dbSecretsProperty = dbSecretsProperty;
        this.dbProperty = dbProperty;
        
    }
    
    @Bean
    public DataSource dataSource() throws JsonProcessingException {
                
        final StringBuilder urlBuilder = new StringBuilder();
        
        final String url = urlBuilder.append(dbProperty.getUrlPrefix())
                .append(dbSecretsProperty.getYdbHost())
                .append(":")
                .append(dbSecretsProperty.getYdbPort())
                .append("/")
                .append(dbSecretsProperty.getYdbDbName())
                .append(dbProperty.getUrlOption())
                .toString();
        
        DataSource dataSource = new DataSource();
        
        dataSource.setDriverClassName(dbProperty.getDriverClassName());
        
        dataSource.setUrl(url);
        
        dataSource.setUsername(dbSecretsProperty.getYdbUser());
        
        dataSource.setPassword(dbSecretsProperty.getYdbPassword());
        
        dataSource.setMaxActive(dbProperty.getMaxActive());
        
        dataSource.setMaxWait(dbProperty.getMaxWait());
        
        dataSource.setMaxIdle(dbProperty.getMaxIdle());
        
        dataSource.setMinIdle(dbProperty.getMinIdle());
        
        dataSource.setInitialSize(dbProperty.getInitialSize());
        
        dataSource.setTestOnBorrow(dbProperty.isTestOnBorrow());
        
        dataSource.setTestWhileIdle(dbProperty.isTestWhileIdle());
        
        dataSource.setTestOnReturn(dbProperty.isTestOnReturn());
        
        dataSource.setValidationQuery(dbProperty.getValidationQuery());
        
        StringBuilder interceptorBuilder = new StringBuilder();
        
        final String interceptorName = interceptorBuilder.append("QueryTimeoutInterceptor(queryTimeout=")
                .append(dbProperty.getQueryTimeout())
                .append(")")
                .toString();
        
        dataSource.setJdbcInterceptors(interceptorName);
        
        return dataSource;
        
    }
    
}
