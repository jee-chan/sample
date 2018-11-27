package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

/**
 * ユーザ一覧画面に関わるコントローラークラスです.
 * 
 * @author rks
 *
 */
@Controller
@RequestMapping("/userList")
public class UserListController {

	@Autowired
	private UserService userService;

	/**
	 * ユーザ一覧画面を表示するメソッド.
	 * 
	 * @return ユーザ一覧画面
	 */
	@RequestMapping("/")
	public String view() {
		return "jsp/userList";
	}

	/**
	 * ユーザ情報を取得して配列を返すメソッド.
	 * 
	 * @return
	 */
	@RequestMapping("/listShow")
	@ResponseBody
	public User[] listShow(String page, String name, String mailAddress) {
		
		// ページ数に応じた取得位置を算出
		Integer pageNum = Integer.parseInt(page);
		pageNum = (pageNum - 1) * 15;
		List<User> userList = userService.getByUserList(pageNum, name, mailAddress);

		// リストが空だった場合全件検索をする
		if (userList.isEmpty()) {
			userList = userService.getByAllUser(pageNum);
		}
		// Ajax用にリストから配列に変換
		User[] userArrayList = userList.toArray(new User[userList.size()]);

		return userArrayList;
	}
}
