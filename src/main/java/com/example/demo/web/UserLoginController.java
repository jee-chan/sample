package com.example.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.LoginUserForm;

/**
 * ログイン関係のコントローラ
 * @author rks
 *
 */
/**
 * @author rks
 *
 */
@Controller
@RequestMapping("/userLogin")
public class UserLoginController {

	//ログ生成用のオブジェクトを用意
	private final static Logger logger = LoggerFactory.getLogger(UserLoginController.class);

	@ModelAttribute
	public LoginUserForm setUpLoginUserForm() {
		LoginUserForm form = new LoginUserForm();
		System.out.println("いつ出るのこれ");
		return form;
	};

	/**
	 * ログイン画面を表示するメソッド.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String view() {
		return "jsp/userLoginForm";
	}

}
