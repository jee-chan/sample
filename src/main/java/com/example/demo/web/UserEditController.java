package com.example.demo.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.User;
import com.example.demo.form.UserForm;
import com.example.demo.logMsg.UserMsg;
import com.example.demo.service.UserService;

/**
 * ユーザ情報を編集するメソッドです.
 * @author rks
 *
 */
@Controller
@RequestMapping("/userEdit")
public class UserEditController {

	@Autowired
	private UserDetailController userDetailController;
	@Autowired
	private UserService userService;

	@ModelAttribute
	public UserForm setUpUserForm() {
		UserForm form = new UserForm();
		return form;
	};
	
	private static final Logger logger = LoggerFactory.getLogger(UserEditController.class); 
	private UserMsg userMsg = new UserMsg();
	
	
	/**
	 * ユーザ情報変更画面を呼び出すメソッド.
	 * @param id id
	 * @param model モデル
	 * @return ユーザ編集画面
	 */
	@RequestMapping("/")
	public String view(Integer id, Model model) {
		User user = userService.userFindById(id);
		model.addAttribute("user", user);
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("mailAddress", user.getMailAddress());
		model.addAttribute("authority", user.getAuthority());
		model.addAttribute("description", user.getDescription());
		return "jsp/userEdit";
	}

	/**
	 * ユーザ情報編集を行うメソッド.
	 * @param userForm ユーザーフォーム
	 * @param res BindingResult
	 * @param model モデル
	 * @return ユーザ詳細画面
	 */
	@RequestMapping("/edit")
	synchronized public String editUser(@Validated UserForm userForm, BindingResult res, Model model, HttpServletRequest req, Exception e) {
		logger.info(userMsg.start("editUser"));
		Integer id = userForm.getId();
		String name =userForm.getUserName();
		Integer authority =userForm.getAuthority();
		String mailAddress =userForm.getMailAddress();
		String description =userForm.getDescription();
		
//		try {
//		if(editingUserList.contains(userForm.getId())) {
//		}
//		}catch (NullPointerException npe) {
//			// TODO: handle exception
//			npe.printStackTrace();
//			System.err.println();
//		}

		
		//入力値チェックに引っかかった場合ユーザ編集画面へ飛ばす
		if(res.hasErrors()) {
			System.out.println("入力チェックエラー");
			System.out.println(res.toString());
			logger.warn(userMsg.errorMsg("editItem"));
			logger.warn(res.toString());
			logger.error(userMsg.errorCase());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTrace = sw.toString();
			logger.error(stackTrace);
			logger.info("==============================");
			logger.info(userMsg.end("editUser"));
			return view(id, model);
		}
		
		//フォームに入力された内容をログファイルに書き出し
		userMsg.insertUserOutput(logger, userForm);
		
		userService.userEdit(id, name, authority, mailAddress, description);
		// 最終的にユーザー詳細画面に返してあげる
		req.setAttribute("editMessage", "編集に成功しました!");
		return userDetailController.view(id, model);
	}
}
