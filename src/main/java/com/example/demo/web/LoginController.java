package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.LoginAdminDetails;
import com.example.demo.service.LoginUserDetails;

@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping("/")
	public void login(@AuthenticationPrincipal LoginUserDetails useraccount,@AuthenticationPrincipal LoginAdminDetails adminaccount, Model model, HttpSession session) {
		//TODO ここだと処理が動かないので場所は変えなければならない
		if (useraccount == null) {
//			model.addAttribute("useraccount", null);
			session.setAttribute("useraccount", null);
		} else {
//			model.addAttribute("useraccount", useraccount);
			session.setAttribute("useraccount", useraccount);
		}
		if (adminaccount == null) {
//			model.addAttribute("adminaccount", null);
			session.setAttribute("adminaccount", null);;
		} else {
//			model.addAttribute("adminaccount", adminaccount);
			session.setAttribute("adminaccount", adminaccount);;
		}
	}
}
