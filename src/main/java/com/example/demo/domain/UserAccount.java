package com.example.demo.domain;

/**
 * ユーザーログイン用のドメインクラス.
 * @author rks
 *
 */
public class UserAccount {

	/**ユーザ名 */
	private String username;
	/**パスワード */
	private String password;
	
	public UserAccount() {}
	public UserAccount(String name, String password) {
		this.username = name;
		this.password = password;
		
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
