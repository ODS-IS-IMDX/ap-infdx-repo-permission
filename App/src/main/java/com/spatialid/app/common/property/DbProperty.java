// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * db.propertiesの値を保持するクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/09
 */
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "ydb")
@Getter
public class DbProperty {
    
    /**
     * JDBCのドライバ名．
     */
    private final String driverClassName;
    
    /**
     * 接続URLの接頭辞．
     */
    private final String urlPrefix;
    
    /**
     * 接続URLのオプション．
     */
    private final String urlOption;
    
    /**
     * スキーマ名．
     */
    private final String schemaName;
    
    /**
     * プールからの接続借用待機時間．
     */
    private final int maxWait;
    
    /**
     * プール内の最大接続数．
     */
    private final int maxActive;
    
    /**
     * プールに常時保持するアイドル接続数の最大値．
     */
    private final int maxIdle;
    
    /**
     * プールに常時保持するアイドル接続数の最小値．
     */
    private final int minIdle;
    
    /**
     * 初期接続数．
     */
    private final int initialSize;
    
    /**
     * 接続借用時における接続の有効性を検証有無．
     */
    private final boolean testOnBorrow;
    
    /**
     * アイドル状態の接続に対する有効性検証有無．
     */
    private final boolean testWhileIdle;
    
    /**
     * 接続返還時における有効性検証有無．
     */
    private final boolean testOnReturn;
    
    /**
     * 有効性検証時に発行するクエリ．
     */
    private final String validationQuery;
    
    /**
     * クエリ実行時の待機時間．
     */
    private final String queryTimeout;
    
}
