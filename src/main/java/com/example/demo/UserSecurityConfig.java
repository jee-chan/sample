package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.LoginAdminDetailsService;

/**
 * ログイン設定用のクラス
 * 
 * @author rks
 *
 */
@Configuration
@EnableWebSecurity
public class UserSecurityConfig {

	/**
	 * 利用者のログイン設定クラス.
	 * 
	 * @author rks
	 *
	 */
	@Configuration
	@Order(2)
	public static class UserConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/fonts/**"); // 静的ファイルに対するアクセス権限を開放する
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.authorizeRequests() // 認可に関する設定
//					.antMatchers("/editItem/", "/addItem/").hasRole("USER")// 利用者のみに許可
					.antMatchers().hasRole("USER")// 利用者のみに許可
					.anyRequest().permitAll();// それ以外のパスは認証が不要

			http.formLogin() // ログインに関する設定
					.loginProcessingUrl("/login/") // ログインボタンを押した際に遷移させるパス(ここに遷移させればログイン処理が働き自動的にログインが行われる)
					.loginPage("/userLogin/") // ログイン画面に遷移させるパス(ログイン認証が必要なパスを指定してかつログインされていないければこのパスに遷移される)
					.failureUrl("/userLogin/?error=true") // ログイン失敗に遷移させるパス
					.defaultSuccessUrl("/main/", false) // デフォルトでログイン成功時に遷移させるパス
					.usernameParameter("username") // 認証時に使用するリクエストパラメータ
					.passwordParameter("password").and(); // 認証時に使用するリクエストパラメータ

			http.logout() // ログアウトに関する設定
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウトさせる際に遷移させるパス
					.logoutSuccessUrl("/userLogin/") // ログアウト後に遷移させるパス
					.deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll();

			// postでデータを送信するための設定.これがないとpostでデータのやり取りができません.
			// http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
			http.csrf().disable();

		}

		@Configuration
		static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

			@Qualifier("LoginUserDetailsService")
			@Autowired
			UserDetailsService userDetailsService;

			@Bean
			public PasswordEncoder passwordEncoder() {
				return new BCryptPasswordEncoder();
			}

			@Override
			public void init(AuthenticationManagerBuilder auth) throws Exception {
				auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
			}
		}

	}
}
