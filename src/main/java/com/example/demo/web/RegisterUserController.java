package com.example.demo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.form.LoginUserForm;
import com.example.demo.form.RegisterUserForm;
import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

/**
 * ユーザー登録関係のコントローラ.
 * 
 * @author rks
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterUserController {

	
	private final static Logger logger = LoggerFactory.getLogger(RegisterUserController.class);
	
	@Autowired
	private UserService userService;


	@ModelAttribute
	public LoginUserForm setUpLoginUserForm() {
		LoginUserForm form = new LoginUserForm();
		return form;
	};

	@ModelAttribute
	public UserForm setUpForm() {
		return new UserForm();
	}

	@RequestMapping("/")
	public String registerUser() {
		return "jsp/userRegister";
	}

	/**
	 * ユーザー登録を行うメソッド.
	 * 
	 * @param username
	 *            ユーザ名
	 * @param password
	 *            パスワード
	 * @param authority
	 *            権限
	 * @param mailAddress
	 *            メールアドレス
	 * @param model
	 *            モデル
	 * @return 登録結果を判定するメソッド
	 */
	@RequestMapping("/user")
	synchronized public String registerUser(@Validated @ModelAttribute RegisterUserForm userForm, BindingResult result, RedirectAttributes attributes) {
		// トライキャッチで失敗したときにメッセージをモデルアトリビュートして登録画面に戻す
		System.out.println(userForm.getUserName());
		System.out.println(userForm.getPassword());
		
		if(result.hasErrors()){
			System.out.println(result.toString());
			return "jsp/userRegister";
		}
		
		boolean registerStatus = false;
		try {
			userService.userRegister(userForm.getUserName(), userForm.getPassword(), userForm.getAuthority(), userForm.getMailAddress());
			registerStatus = true;
		} catch (Exception e) {
			System.err.println("Exception is" + e);
			registerStatus = false;
		}
		return registerResult(registerStatus, attributes);
	}

	/**
	 * 登録結果によって返すページを切り替えるメソッド.
	 * 
	 * @param registerStatus
	 *            登録状況
	 * @param model
	 *            モデル
	 * @return
	 */
	@RequestMapping("/result")
	public String registerResult(boolean registerStatus,RedirectAttributes attributes) {
		if (registerStatus == true) {
			System.out.println("ユーザ登録が完了しました");
			attributes.addFlashAttribute("registerMessage", "ユーザ登録が完了しました");
			return "redirect:/userLogin/";
		} else {
			System.out.println("ユーザ登録に失敗しました");
			attributes.addFlashAttribute("registerMessage", "ユーザ登録に失敗しました");
			return "redirect:/register/";
		}
		// return "jsp/userLoginForm";
	}
}
