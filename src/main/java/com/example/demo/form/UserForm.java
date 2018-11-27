package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * ユーザ情報を持つフォームクラス
 * @author rks
 *
 */
public class UserForm {

	/**id */
	private Integer id;
	/**ユーザ名 */
	@NotEmpty(message="入力必須項目です")
	@Size(min = 1, max = 20, message="1文字から20文字の間で入力してください")
	private String userName;
	/**パスワード */
	@NotEmpty(message="入力必須項目です")
	@Pattern(regexp="^(?=.*?[a-zA-Z])(?=.*?\\d)[a-zA-Z\\d]{8,20}$" , message = "パスワードは8文字から20文字、半角英数字を使用し大文字、小文字、数字が入っている必要があります")
	private String password;
	/**権限 */
	@NotNull(message="権限を選択してください")
	private Integer authority;
	/**メールアドレス */
	@NotEmpty(message="入力必須項目です")
	@Email(message="正しい形式でメールアドレスを入力してください")
	private String mailAddress;
	/**ユーザ説明 */
	@NotEmpty(message="入力必須項目です")
	@Size(min = 1, max = 500, message="1文字から500文字の間で入力してください")
	private String description;
	
	public UserForm() {
	}
	public UserForm(Integer id, String name,String password,Integer authority,String mailAddress,String description) {
		this.id = id;
		this.userName = name;
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
