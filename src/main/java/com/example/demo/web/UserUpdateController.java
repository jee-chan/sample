package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.UserRepository;

/**
 * ユーザー情報編集に関わるコントローラーです
 * @author rks
 *
 */
@RequestMapping("/userUpdate")
@Controller
public class UserUpdateController {
	
	@Autowired
	private UserRepository userRepository;
	/**
	 * 編集ページを表示するメソッド
	 * @return 商品編集ページ
	 */
	@RequestMapping("/")
	public String view(String name, Model model) {
		userRepository.searchUserByName(name);
		
		return "jsp/registrationInformationEdit";
	}
	
	
	/**
	 * ユーザ情報編集を行うメソッド
	 * @return 今のところ元のページに戻す形にしてるけどどうしよう
	 */
	@RequestMapping("edit")
	public String edit() {
		return "jsp/registrationInformationEdit";
	}

}
