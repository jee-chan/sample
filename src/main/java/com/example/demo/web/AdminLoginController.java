package com.example.demo.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.LoginUserForm;

/**
 * 管理者ログイン関連の操作を行うクラス.
 * 
 * @author rks
 *
 */
@Controller
@RequestMapping("/AdminUserLogin")
public class AdminLoginController {

	// ログ生成用のオブジェクトを用意
	private final static Logger logger = LoggerFactory.getLogger(AdminLoginController.class);

	@ModelAttribute
	public LoginUserForm setUpLoginUserForm() {
		LoginUserForm form = new LoginUserForm();
		return form;
	};

	/**
	 * ログインページの表示を行うメソッド.
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String view() {
		return "jsp/AdminLoginForm";
	}

//	/**
//	 * ログイン時に行う処理を行うメソッド.
//	 */
//	@RequestMapping("/login")
//	public void loginProcces(@Valid @ModelAttribute LoginUserForm form, BindingResult result, Boolean error,
//			Model model) {
//		if (error == true) {
//		} else {
//		}
//	}
}
