package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;
import com.example.demo.domain.UserAccount;

@Repository
public class UserRepository {

	public static final RowMapper<UserAccount> LOGIN_USER_ROW_MAPPER = (rs, i) -> {
		String username = rs.getString("name");
		String password = rs.getString("password");

		return new UserAccount(username, password);
	};

	public static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		Integer id = rs.getInt("id");
		String userName = rs.getString("name");
		String password = rs.getString("password");
		Integer authority = rs.getInt("authority");
		String mailAddress = rs.getString("mailaddress");
		String description = rs.getString("description");

		return new User(id, userName, password, authority, mailAddress, description);
	};

	@Autowired
	NamedParameterJdbcTemplate template;

	/**
	 * ユーザ情報すべてをリストで取得するメソッド
	 * 
	 * @return ユーザ情報リスト
	 */
	public List<User> getByAllUser(Integer page) {
		System.out.println(page+"件目のデータから表示します");
		String sql = "SELECT id,name,password,authority,mailaddress,description FROM users ORDER BY authority DESC, name OFFSET :page ROWS FETCH FIRST 15 ROWS ONLY";
		// String sql = "SELECT id,name,password,authority,mailaddress,description FROM users ORDER BY authority DESC, name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("page", page);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);

		return userList;
	}

	/**
	 * 名前をもとにユーザ検索を行うメソッド.
	 * 
	 * @param name
	 *            名前
	 * @return 検索結果を格納したリスト
	 */
	public List<User> usersearchByName(String name, Integer page) {
		String sql = "SELECT id,name,password,authority,mailaddress,description FROM users WHERE name LIKE '%"+name+"%' ORDER BY authority DESC, name OFFSET :page ROWS FETCH FIRST 15 ROWS ONLY";
		SqlParameterSource param = new MapSqlParameterSource().addValue("page", page);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);

		return userList;
	}

	/**
	 * メールアドレスをもとにユーザ検索を行うメソッド.
	 * 
	 * @param mailAddress
	 *            メールアドレス
	 * @return 検索結果を格納したリスト
	 */
	public List<User> usersearchByMailAddress(String mailAddress, Integer page) {
		String sql = "SELECT id,name,password,authority,mailaddress,description FROM users WHERE mailaddress LIKE '%"+mailAddress+"%' ORDER BY authority DESC, name OFFSET :page ROWS FETCH FIRST 15 ROWS ONLY";
		SqlParameterSource param = new MapSqlParameterSource().addValue("page", page);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);

		return userList;
	}
	
	/**
	 * 名前とメールアドレスを元にユーザーを検索するメソッド.
	 * @param name 名前
	 * @param mailAddress メールアドレス
	 * @param page ページ
	 * @return 検索したユーザのリスト
	 */
	public List<User> usersearchByNameAndMailAddress(String name, String mailAddress, Integer page) {
		System.out.println("ページ番号=====>"+page);
		String sql = "SELECT id,name,password,authority,mailaddress,description FROM users WHERE name LIKE '%"+name+"%' AND mailaddress LIKE '%"+mailAddress+"%' ORDER BY authority DESC, name OFFSET :page ROWS FETCH FIRST 15 ROWS ONLY";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name).addValue("mailAddress", mailAddress).addValue("page", page);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);

		return userList;
	}

	/**
	 * ユーザ情報をDBに登録するメソッド.
	 * 
	 * @param name名前
	 * @param hiddenPassword
	 *            serviceでハッシュ化したパスワード
	 * @param authority
	 *            権限
	 * @param mailAddress
	 *            パスワード
	 */
	public void userRegister(String name, String hiddenPassword, Integer authority, String mailAddress) {
		String sql = "INSERT INTO users (name, password, authority, mailAddress) VALUES(:name, :hiddenPassword,:authority,:mailAddress)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name)
				.addValue("hiddenPassword", hiddenPassword).addValue("authority", authority)
				.addValue("mailAddress", mailAddress);
		template.update(sql, param);
	}

	// 同じ名前をはじくためのSQL
	// INSERT INTO users ( name,password ) SELECT 'jun',114514 FROM users WHERE NOT
	// EXISTS ( SELECT id FROM users WHERE name = 'jun') LIMIT 1;
	// authorityも考慮したやつ
	// INSERT INTO users ( name, password, authority) SELECT '名前', 'パスワード', 0 FROM
	// users WHERE NOT EXISTS ( SELECT id FROM users WHERE name = 'かぶらせたくない名前')
	// LIMIT 1;
	/**
	 * 名前からユーザー情報を取得するメソッド.
	 * 
	 * @param name
	 *            検索に用いる名前
	 * @return 検索結果のユーザ情報
	 */
	public User searchUserByNameAndPassword(String name) {
		String sql = "SELECT id,name,password,authority,mailaddress,description FROM users WHERE name = :name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		User user = template.queryForObject(sql, param, USER_ROW_MAPPER);

		return user;
	}

	/**
	 * 名前からユーザー情報を取得するメソッド(spring securityが使用)
	 * 
	 * @param name
	 * @return
	 */
	public UserAccount searchUserByName(String name) {
		String sql = "SELECT name,password FROM users WHERE name = :name AND authority = 0";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		UserAccount userAccount = template.queryForObject(sql, param, LOGIN_USER_ROW_MAPPER);
		return userAccount;
	}
	
	/**
	 * 名前から管理者情報を取得するメソッド(spring securityが使用)
	 * @param name 名前
	 * @return nameとpasswordを設定したUserAccountオブジェクト
	 */
	public UserAccount searchAdminByName(String name) {
		String sql = "SELECT name,password FROM users WHERE name = :name AND authority = 1";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		UserAccount userAccount = template.queryForObject(sql, param, LOGIN_USER_ROW_MAPPER);
		return userAccount;
		
	}

	/**
	 * idをもとにユーザ情報を取得するメソッド.
	 * 
	 * @param id
	 *            id
	 * @return ユーザ情報を格納したオブジェクト
	 */
	public User getUserDetailById(Integer id) {
		String sql = "SELECT id, name, password, authority, mailaddress, description FROM users WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		User userAccount = template.queryForObject(sql, param, USER_ROW_MAPPER);

		return userAccount;
	}

	/**
	 * ユーザ情報を編集するメソッド.
	 * 
	 * @param id
	 *            id
	 * @param name
	 *            名前
	 * @param authority
	 *            権限
	 * @param mailAddress
	 *            メールアドレス
	 * @param description
	 *            ユーザ説明
	 */
	public void userInfomationEdit(Integer id, String name, Integer authority, String mailAddress, String description) {
		String sql = "UPDATE users SET name=:name, authority=:authority, mailaddress=:mailAddress, description=:description WHERE id=:id ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id).addValue("name", name)
				.addValue("authority", authority).addValue("mailAddress", mailAddress)
				.addValue("description", description);
		template.update(sql, param);
		System.out.println("ユーザ情報編集完了");
	}
}
