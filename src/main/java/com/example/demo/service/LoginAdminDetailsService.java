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
 * 管理者認証処理を実際に行うサービスクラス.
 * @author rks
 *
 */
@Service
@Component("LoginAdminDetailsService")
public class LoginAdminDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 入力された管理者情報を照合するメソッド.
	 * @param username 管理者名
	 * @return 
	 * @throws UsernameNotFoundException ユーザが見つからなかった際のエラー
	 */
	@Transactional(readOnly = true)
//	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			System.out.println("認証用メソッド処理");
			UserAccount userAccount = userRepository.searchAdminByName(username);
			return new LoginAdminDetails(userAccount);
		} catch (Exception e) {
			System.err.println("管理者ユーザの確認ができませんでした");
			throw new UsernameNotFoundException("The requested user is not found.");
		}
	}
}
