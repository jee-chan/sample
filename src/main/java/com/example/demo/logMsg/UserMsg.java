package com.example.demo.logMsg;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.example.demo.form.UserForm;

@Component
public class UserMsg {


	/**
	 * 処理の開始時に記述するメッセージ.
	 * @param methodName 使用されたメソッド
	 * @return メッセージ
	 */
	public String start(String methodName) {
		String start = "START - " + methodName + "の処理を実行します";
		return start;
	}
	
	/**
	 * 処理の終了時に記述するメッセージ
	 * @param methodName 使用されたメソッド
	 * @return メッセージ
	 */
	public String end(String methodName) {
		String end = "END - " + methodName + "の処理を終了します \r\n";
		return end;
	}
	
	/**
	 * リダイレクト先の記述
	 * @param path リダイレクト先のパス
	 * @return メッセージ
	 */
	public String redirect(String path) {
		String redirect = "END - RedirectPath ====> " + path;
		return redirect;
	}
	
	/**
	 * 入力された項目を記述
	 * @param column 入力された項目名
	 * @param value 入力値
	 * @return メッセージ
	 */
	public String input(String column, String value) {
		String input = "INPUT - " + column + " ====> " + value;
		return input;
	}
	
	/**
	 * 出力する、または返ってきた項目を記述
	 * @param column 入力された項目名
	 * @param value 入力値
	 * @return メッセージ
	 */
	public String output(String column, String value) {
		String output = "INPUT - " + column + " ====> " + value;
		return output;
	}
	
	/**
	 * エラーメッセージの記述
	 * @param method 使用したメソッド名
	 * @return メッセージ
	 */
	public String errorMsg(String method) {
		String msg = "EXECUTE FAILURE! - " + method;
		return msg;
	}
	
	/**
	 * エラーの原因を記述
	 * @return メッセージ
	 */
	public String errorCase() {
		String errCase = "Cause by: ";
		return errCase;
	}
	
	/**
	 * 入力された情報を書き出す
	 * @param logger ロガー
	 * @param form フォーム
	 */
	public void insertUserOutput(Logger logger, UserForm form) {
		logger.info(this.input("ID", String.valueOf(form.getId())));
		logger.info(this.input("userName", form.getUserName()));
		logger.info(this.input("authority", String.valueOf(form.getAuthority())));
		logger.info(this.input("mailAddress", form.getMailAddress()));
		logger.info(this.input("description", form.getDescription()));
	}
}
