package com.example.demo.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/userdetail")
public class UserDetailController {

	@Autowired
	private UserService userService;
	/**
	 * ユーザ場情報を取得してページを表示するメソッド
	 * @return ユーザ詳細画面
	 */
	@RequestMapping("/")
	public String view(Integer id, Model model) {
		User user = userService.userFindById(id);
		model.addAttribute("user", user);
		return "jsp/userDetail";
	}
}
