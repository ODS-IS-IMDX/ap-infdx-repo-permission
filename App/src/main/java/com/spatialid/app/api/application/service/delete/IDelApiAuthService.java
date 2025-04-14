// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.delete;

/**
 * API利用権限削除API用のServiceインターフェース。
 * 
 * @author kawashima naoya
 * @version 1.1 2024/07/29
 */
public interface IDelApiAuthService {

	/**
	 * リクエスト情報を元に、API利用権限情報を削除する。
	 * 
	 * @param servicerGroupId 利用者グループID
	 * @return 削除件数
	 */
	int delApiAuth(String servicerGroupId);

}
