package com.example.demo.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import com.example.demo.domain.UserAccount;

/**
 * ユーザ情報にユーザ権限を付与するクラス.
 * @author rks
 *
 */
public class LoginUserDetails extends User {

	private static final long serialVersionUID = 1L;
	private final UserAccount userAccount;
	
	/**
	 * 取得したユーザ情報とロールをフィールドのアカウントオブジェクトに付与するメソッド.
	 * @param userAccount 紐づけられるユーザ情報
	 */
	public LoginUserDetails(UserAccount userAccount) {
		//ユーザ情報の名前とパスワードを取得してフィールドのuserへ代入してユーザのロールを付与する
		super(userAccount.getUsername(), userAccount.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
		this.userAccount = userAccount;
	}
	
	/**
	 * アカウント情報を取得するためのメソッド.
	 * @return フィールドのアカウント
	 */
	public UserAccount getUserAccount() {
		return userAccount;
	}
}
