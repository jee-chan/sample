package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.LoginAdminDetailsService;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {


	/**
	 * 管理者用の権限設定とページ制御クラス
	 * 
	 * @author rks
	 *
	 */
	@Configuration
	@Order(1)
	public static class AdminUserConfigurerAdapter extends WebSecurityConfigurerAdapter {

		// cssファイル等の権限スルー設定
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/fonts/**"); // 静的ファイルに対するアクセス権限を開放する
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			// 認可に関する設定
			http.authorizeRequests() // 認可設定
					.antMatchers("/editItem/", "/addItem/", "/userList/", "/userEdit/", "/userUpdate/").hasRole("ADMIN")
					.anyRequest().permitAll(); // それ以外のパスは認証不要

			// ログインに関する設定
//			http.formLogin().loginProcessingUrl("/AdminUserLogin/login?error=false") // ログインした際に認証を行うパス（認証処理はここで行われる）
			http.formLogin().loginProcessingUrl("/login/") // ログインした際に認証を行うパス（認証処理はここで行われる）
					.loginPage("/AdminUserLogin/") // ログイン画面のパス(認可なしでページに飛ぼうとするとここに飛ばされる)
					.failureUrl("/AdminUserLogin/?error=true") // ログイン失敗時に遷移するパス
					.defaultSuccessUrl("/main/", false) // ログイン成功時に遷移するパス
					.usernameParameter("username") // 認証時に使用するリクエストパラメータ
					.passwordParameter("password").and(); // 認証時に使用するリクエストパラメータ

			http.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウト処理を行うパス
					.logoutSuccessUrl("/AdminUserLogin/") // ログアウト後に遷移するパス
					.deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll();

			http.csrf().disable();

		}

		@Configuration
		static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

//			@Bean
//			public LoginAdminDetailsService loginAdminDetailsService() {
//				return new LoginAdminDetailsService();
//			}
			@Qualifier("LoginAdminDetailsService")
			@Autowired
			private LoginAdminDetailsService loginAdminDetailsService;

			@Bean
			public PasswordEncoder passwordEncoder() {
				return new BCryptPasswordEncoder();
			}

			@Override
			public void init(AuthenticationManagerBuilder auth) throws Exception {
				auth.userDetailsService(loginAdminDetailsService).passwordEncoder(passwordEncoder());
			}
		}
	}
}
