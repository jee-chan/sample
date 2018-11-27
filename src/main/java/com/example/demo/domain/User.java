package com.example.demo.domain;

/**
 * ユーザーを定義するクラス.
 * @author rks
 *
 */

public class User {

	/**id */
	private Integer id;
	/**名前 */
	private String userName;
	/**パスワード */
	private String password;
	/**権限 */
	private Integer authority;
	/**メールアドレス */
	private String mailAddress;
	/**ユーザ説明 */
	private String description;
	
	public User() {};
	public User(Integer id, String userName, String password, Integer authority, String mailAddress, String description) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.authority = authority;
		this.mailAddress = mailAddress;
		this.description = description;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAuthority() {
		return authority;
	}
	public void setAuthority(Integer authority) {
		this.authority = authority;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

	
}
