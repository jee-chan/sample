package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.UserAccount;
import com.example.demo.repository.UserRepository;

/**
 * ユーザ認証処理を実際に行うサービスクラス.
 * @author rks
 *
 */
@Service
@Component("LoginUserDetailsService")
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 入力されたユーザ情報を照合するメソッド.
	 * @param username ユーザ名
	 * @return 
	 * @throws UsernameNotFoundException ユーザが見つからなかった際のエラー
	 */
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			System.out.println("認証用メソッド処理");
			UserAccount userAccount = userRepository.searchUserByName(username);
			return new LoginUserDetails(userAccount);
		} catch (Exception e) {
			System.out.println("ユーザの確認ができませんでした");
			throw new UsernameNotFoundException("The requested user is not found.");
		}
	}
}
