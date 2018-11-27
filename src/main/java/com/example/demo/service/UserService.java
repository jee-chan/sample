package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザ情報すべてのリストを取得するメソッド.
	 * 
	 * @return ユーザ情報リスト
	 */
	public List<User> getByUserList(Integer pageNum, String name, String mailAddress) {

		// リストを初期化
		List<User> userList = new ArrayList<>();

		// どちらも空文字ないしnullだった場合指定なしの全件検索を行う
		if ((name == "" || name == null) && (mailAddress == "" || mailAddress == null)) {
//			System.out.println("両方に条件が入力されていない場合");
			userList = userRepository.getByAllUser(pageNum);
		}
		// 名前のみ空だった場合にはメールアドレスで検索を行う
		else if (name == "" || name == null) {
//			System.out.println("名前に条件が入力されていない場合");
			userList = userRepository.usersearchByMailAddress(mailAddress,pageNum);
		}
		// メールアドレスのみ空だった場合には名前で検索を行う
		else if (mailAddress == "" || mailAddress == null) {
//			System.out.println("メールアドレスに条件が入力されていない場合");
			userList = userRepository.usersearchByName(name,pageNum);
		}
		else {
//			System.out.println("全て入力されたパターン");
			userList = userRepository.usersearchByNameAndMailAddress(name, mailAddress, pageNum);
		}

		return userList;
	}

	/**
	 * すべてのユーザリストを取得するメソッド.
	 * @return ユーザ情報を格納したリスト
	 */
	public List<User> getByAllUser(Integer pageNum) {
		List<User> userList = userRepository.getByAllUser(pageNum);
		return userList;
	}

	/**
	 * ユーザ登録を行うメソッド.
	 * 
	 * @param name
	 *            名前
	 * @param password
	 *            パスワード
	 * @param authority
	 *            権限
	 */
	public void userRegister(String name, String password, Integer authority, String mailAddress) {
		String hiddenPassword = passwordEncoder.encode(password);
		userRepository.userRegister(name, hiddenPassword, authority, mailAddress);
	}

	
	/**
	 * IDをもとにユーザ情報を取得するメソッド.
	 * @param id
	 * @return
	 */
	public User userFindById(Integer id) {
		User user = userRepository.getUserDetailById(id);
		return user;
	}
	/**
	 * 登録されたユーザーを検索するメソッドです.
	 * 
	 * @param name
	 *            名前
	 * @param password
	 *            パスワード
	 */
	public void userAuthentication(String name, String password) {
		System.out.println(password);
		String hiddenPassword = passwordEncoder.encode(password);
		System.out.println(hiddenPassword);

	}
	
	/**
	 * ユーザ情報を編集するためのメソッド.
	 * @param id id
	 * @param name 名前
	 * @param authority 権限
	 * @param mailAddress メールアドレス
	 * @param description ユーザ情報
	 * @return 編集したユーザの情報を格納したオブジェクト
	 */
	public void userEdit(Integer id, String name, Integer authority, String mailAddress, String description) {
		// TODO: パスワードを変更する機能も追加するとしたらここにハッシュ化するエンコーダ置かないといけないです
		userRepository.userInfomationEdit(id, name, authority, mailAddress, description);
	}
}
