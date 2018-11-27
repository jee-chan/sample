package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;

/**
 * 商品検索を行うためのサービスクラスです.
 * 
 * @author rks
 *
 */
@Service
public class ItemSearchService {

	@Autowired
	ItemRepository itemrepository;

	/**
	 * 親と子カテゴリのみ入力された場合の検索メソッド.
	 * 
	 * @param name
	 *            名前
	 * @param bigCategory
	 *            親カテゴリ
	 * @param middleCategory
	 *            子カテゴリ
	 * @param smallCategory
	 *            孫カテゴリ
	 * @param brand
	 *            ブランド
	 * @param model
	 *            モデル
	 * @return 商品を格納したリスト
	 */
	public List<Item> searchByParentAndChild(Integer findItemNum, String name, String bigCategory,
			String middleCategory, String smallCategory, String brand, Model model) {
		System.out.println("=========親子検索=========");
		String category = "b.name_all ILIKE '" + bigCategory + "/" + middleCategory + "/%' ";

		// ここにリポジトリのメソッドを記述してリターンでリストを返す
		List<Item> itemList = itemSearch(findItemNum, name, category, brand, model);
		return itemList;
	}

	/**
	 * 子と孫カテゴリのみ入力された場合の検索メソッド.
	 * 
	 * @param name
	 *            名前
	 * @param bigCategory
	 *            親カテゴリ
	 * @param middleCategory
	 *            子カテゴリ
	 * @param smallCategory
	 *            孫カテゴリ
	 * @param brand
	 *            ブランド
	 * @param model
	 *            モデル
	 * @return 商品を格納したリスト
	 */
	public List<Item> searchByChildAndGrandchild(Integer findItemNum, String name, String bigCategory,
			String middleCategory, String smallCategory, String brand, Model model) {
		System.out.println("=========これは子孫=========");
		String category = "b.name_all ILIKE '%/" + middleCategory + "/" + smallCategory + "'";
		// ここにリポジトリのメソッドを記述してリターンでリストを返す
		List<Item> itemList = itemSearch(findItemNum, name, category, brand, model);
		return itemList;
	}

	/**
	 * 親と孫カテゴリのみ入力された場合の検索メソッド. 検索は行わずに全件の結果を返してエラーメッセージをmodelAttributeする
	 * 
	 * @param name
	 *            名前
	 * @param bigCategory
	 *            親カテゴリ
	 * @param middleCategory
	 *            子カテゴリ
	 * @param smallCategory
	 *            孫カテゴリ
	 * @param brand
	 *            ブランド
	 * @param model
	 *            モデル
	 * @return 商品を格納したリスト
	 */
	public List<Item> searchByParentAndGrandchild(Integer findItemNum, String name, String bigCategory,
			String middleCategory, String smallCategory, String brand, Model model) {

		System.out.println("親と孫のみ");
		model.addAttribute("errorMessage", "親カテゴリと孫カテゴリの組み合わせでは検索できません");

		// ここにリポジトリのメソッドを記述してリターンでリストを返す
		List<Item> itemList = itemrepository.findItem(1);
		return itemList;
	}

	/**
	 * 親カテゴリのみ入力された場合の検索メソッド.
	 * 
	 * @param name
	 *            名前
	 * @param bigCategory
	 *            親カテゴリ
	 * @param middleCategory
	 *            子カテゴリ
	 * @param smallCategory
	 *            孫カテゴリ
	 * @param brand
	 *            ブランド
	 * @param model
	 *            モデル
	 * @return 商品を格納したリスト
	 */
	public List<Item> searchByParent(Integer findItemNum, String name, String bigCategory, String middleCategory,
			String smallCategory, String brand, Model model) {
		System.out.println("=========これは親=========");
		String category = "b.name_all ILIKE '" + bigCategory + "/%' ";
		// ここにリポジトリのメソッドを記述してリターンでリストを返す
		List<Item> itemList = itemSearch(findItemNum, name, category, brand, model);
		return itemList;
	}

	/**
	 * 子カテゴリのみ入力された場合の検索メソッド.
	 * 
	 * @param name
	 *            名前
	 * @param bigCategory
	 *            親カテゴリ
	 * @param middleCategory
	 *            子カテゴリ
	 * @param smallCategory
	 *            孫カテゴリ
	 * @param brand
	 *            ブランド
	 * @param model
	 *            モデル
	 * @return 商品を格納したリスト
	 */
	public List<Item> searchByChild(Integer findItemNum, String name, String bigCategory, String middleCategory,
			String smallCategory, String brand, Model model) {
		System.out.println("=========これは子=========");
		String category = "b.name_all ILIKE '%/" + middleCategory + "/%' ";
		// ここにリポジトリのメソッドを記述してリターンでリストを返す
		List<Item> itemList = itemSearch(findItemNum, name, category, brand, model);
		return itemList;
	}

	/**
	 * 孫カテゴリのみ入力された場合の検索メソッド.
	 * 
	 * @param name
	 *            名前
	 * @param bigCategory
	 *            親カテゴリ
	 * @param middleCategory
	 *            子カテゴリ
	 * @param smallCategory
	 *            孫カテゴリ
	 * @param brand
	 *            ブランド
	 * @param model
	 *            モデル
	 * @return 商品を格納したリスト
	 */
	public List<Item> searchByGrandchild(Integer findItemNum, String name, String bigCategory, String middleCategory,
			String smallCategory, String brand, Model model) {
		System.out.println("=========これは孫=========");
		String category = "b.name_all ILIKE '%/" + smallCategory + "/' ";
		// ここにリポジトリのメソッドを記述してリターンでリストを返す
		List<Item> itemList = itemSearch(findItemNum, name, category, brand, model);
		return itemList;
	}

	/**
	 * カテゴリすべてが入力された場合の検索メソッド.
	 * 
	 * @param name
	 *            名前
	 * @param bigCategory
	 *            親カテゴリ
	 * @param middleCategory
	 *            子カテゴリ
	 * @param smallCategory
	 *            孫カテゴリ
	 * @param brand
	 *            ブランド
	 * @param model
	 *            モデル
	 * @return 商品を格納したリスト
	 */
	public List<Item> searchByAllCategory(Integer findItemNum, String name, String bigCategory, String middleCategory,
			String smallCategory, String brand, Model model) {
		System.out.println("=========これは全て=========");
		String category = "b.name_all = '" + bigCategory + "/" + middleCategory + "/" + smallCategory + "' ";
		// ここにリポジトリのメソッドを記述してリターンでリストを返す
		List<Item> itemList = itemSearch(findItemNum, name, category, brand, model);
		return itemList;
	}

	/**
	 * カテゴリすべて入力されなかった場合の検索メソッド.
	 * 
	 * @param name
	 *            名前
	 * @param bigCategory
	 *            親カテゴリ
	 * @param middleCategory
	 *            子カテゴリ
	 * @param smallCategory
	 *            孫カテゴリ
	 * @param brand
	 *            ブランド
	 * @param model
	 *            モデル
	 * @return 商品を格納したリスト
	 */
	public List<Item> searchByNonCategory(Integer findItemNum, String name, String bigCategory, String middleCategory,
			String smallCategory, String brand, Model model) {
		System.out.println("=========これは何も入力されていない=========");
		List<Item> itemList = null;
		// 名前とブランドが入力されていなかったら検索は実行せずにエラーメッセージを送信する
		if ((name == null ||StringUtils.isEmpty(name)) && (brand == null || StringUtils.isEmpty(brand))) {
			System.out.println("検索条件が入力されていない(controller)");
			model.addAttribute("errorMessage", "検索条件を入れてください");
			// 名前とブランドが入力されていたら
			itemList = itemrepository.findItem(1);
			return itemList;
			}
		String category = "";
		// ここにリポジトリのメソッドを記述してリターンでリストを返す
		itemList = itemSearch(findItemNum, name, category, brand, model);
		return itemList;
	}

	/**
	 * 商品検索を行うメソッド.
	 *
	 * @param name
	 *            商品名
	 * @param category
	 *            カテゴリ
	 * @param brand
	 *            ブランド
	 * @param model
	 *            モデル
	 * @return 商品リスト
	 */
	public List<Item> itemSearch(Integer findItemNum, String name, String category, String brand, Model model) {
		// 検索語句をリストに格納
		List<String> paramList = new ArrayList<>();
		paramList.add(name);
		paramList.add(brand);

		// カラム名のリストを作成
		List<String> calamList = new ArrayList<>();
		calamList.add("a.name");
		calamList.add("a.brand");

		// 検索語句が空かnullの場合空白を、値がある場合はSQL文に整形
		for (int i = 0; i < paramList.size(); i++) {
			if (paramList.get(i).equals(null) || StringUtils.isEmpty(paramList.get(i)) || paramList.get(i).equals("")) {
				paramList.set(i, "");
				System.out.println(i+"番目の検索語句がないです！！！！！！！！！！！！！");
			} else {
				System.out.println(calamList.get(i) + "変換前(service)");
				System.out.println(paramList.get(i) + "変換前(service)");
				paramList.set(i, calamList.get(i) + " ILIKE '%" + paramList.get(i) + "%' AND ");
				System.out.println(calamList.get(i) + "変換後(service)");
				System.out.println(paramList.get(i) + "変換後(service)");
			}
		}
		// リストにカテゴリーの項目も追加
		paramList.add(category);

		// 検索ワードを結合してSQL文に整形
		String whereWord = "";
		for (String param : paramList) {
			whereWord = whereWord + param;
		}
		System.out.println("検索を行うWHERE句がこれ（service）==========>" + whereWord);

		// 商品を検索してリストに格納
		System.out.println("検索実行(service)");
		List<Item> itemList = itemrepository.searchItemByWord(whereWord, findItemNum);
		System.out.println("検索完了(service)");

		return itemList;
	}

}
