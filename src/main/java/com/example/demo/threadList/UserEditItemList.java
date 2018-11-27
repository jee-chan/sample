package com.example.demo.threadList;

import java.util.ArrayList;
import java.util.List;

/**
 * スレッド毎の編集中情報を堆積するリストを管理するメソッド
 * 
 * @author rks
 *
 */
public class UserEditItemList {
	private static List<String> userEditlist = new ArrayList<>();

	public static List<String> getUserEditlist() {
		return userEditlist;
	}

	public static void setUserEditlist(String userId) {
		UserEditItemList.userEditlist.add(userId);
	}

	public static void removeUserEditlist() {
		 userEditlist.clear();
	}

}
