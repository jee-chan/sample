package com.example.demo.web;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemSearchService;
import com.example.demo.service.LoginAdminDetails;
import com.example.demo.service.LoginUserDetails;

/**
 * 商品リスト関連のコントローラです.
 * 
 * @author rks
 *
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/main")
public class ItemListController {
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ItemSearchService itemSearchService;

	/**
	 * 商品リストを表するメソッド.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String itemList(@AuthenticationPrincipal LoginUserDetails useraccount,
			@AuthenticationPrincipal LoginAdminDetails adminaccount, Model model, HttpSession session) {
		if (useraccount == null) {
			session.setAttribute("useraccount", null);
		} else {
			session.setAttribute("useraccount", useraccount);
		}
		if (adminaccount == null) {
			session.setAttribute("adminaccount", null);
			;
		} else {
			session.setAttribute("adminaccount", adminaccount);
			;
		}
		// 商品リストを取得
		// List<Item> itemList = itemRepository.findItem();
		//
		// // 商品リストの中にカテゴリーがない商品のフラグ判定
		// for (Item item : itemList) {
		// if ("".equals(item.getBigCategory()) || item.getBigCategory().isEmpty()) {
		// item.setCut(false);
		// }
		// }
		// model.addAttribute("itemList", itemList);

		// カテゴリーリストを送信
		categoryCall(model);

		return "jsp/itemList";
	}


	/**
	 * それぞれのカテゴリーリストを取得してJSPにリストを送信するメソッド.
	 * 
	 * @param model
	 */
	@RequestMapping("/categoryList")
	public void categoryCall(Model model) {
		// ページ内のプルダウンメニュー表示のためにリストを送信
		List<String> bigCategoryList = itemRepository.getBigCategoryList();
		List<String> middleCategoryList = itemRepository.getMiddleCategoryList();
		List<String> smallCategoryList = itemRepository.getSmallCategoryList();

		model.addAttribute("bigCategoryList", bigCategoryList);
		model.addAttribute("middleCategoryList", middleCategoryList);
		model.addAttribute("smallCategoryList", smallCategoryList);
	}

	/**
	 * 検索条件に則った、もしくはすべての商品を取得して送信するメソッド.
	 * 
	 * @param page
	 *            ページ
	 * @param name
	 *            検索語句
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
	 * @return 検索した商品を格納した配列
	 */
	@RequestMapping("/getItemList")
	@ResponseBody
	public Item[] itemList(Integer page, String name, String bigCategory, String middleCategory, String smallCategory,
			String brand, Model model) {


		// リストを初期化
		List<Item> itemList = null;
		// 検索に用いる数字を用意
		Integer findItemNum = (page - 1) * 30;

		// 検索条件が指定されていない場合は全件検索を行う
		if (StringUtils.isEmpty(name) && StringUtils.isEmpty(bigCategory) && StringUtils.isEmpty(middleCategory)
				&& StringUtils.isEmpty(smallCategory) && StringUtils.isEmpty(brand)) {
			System.out.println("何も入っていない場合(controller)");
			// itemList = itemRepository.findPagingItem(findItemNum);
			itemList = itemRepository.findItem(findItemNum);
		}

		// 全て入力された場合
		else if (!StringUtils.isEmpty(bigCategory) && !StringUtils.isEmpty(middleCategory)
				&& !StringUtils.isEmpty(smallCategory)) {
			System.out.println("すべて入っている場合(controller)");
			itemList = itemSearchService.searchByAllCategory(findItemNum, name, bigCategory, middleCategory,
					smallCategory, brand, model);
		}

		// 親カテゴリのみ入力された場合
		else if (!StringUtils.isEmpty(bigCategory) && StringUtils.isEmpty(middleCategory)
				&& StringUtils.isEmpty(smallCategory)) {
			System.out.println("親の場合(controller)");
			itemList = itemSearchService.searchByParent(findItemNum, name, bigCategory, middleCategory, smallCategory,
					brand, model);
		}

		// 子カテゴリのみ入力された場合
		else if (StringUtils.isEmpty(bigCategory) && !StringUtils.isEmpty(middleCategory)
				&& StringUtils.isEmpty(smallCategory)) {
			System.out.println("子の場合(controller)");
			itemList = itemSearchService.searchByChild(findItemNum, name, bigCategory, middleCategory, smallCategory,
					brand, model);
		}

		// 孫カテゴリのみ入力された場合
		else if (StringUtils.isEmpty(bigCategory) && StringUtils.isEmpty(middleCategory)
				&& !StringUtils.isEmpty(smallCategory)) {
			System.out.println("孫の場合(controller)");
			itemList = itemSearchService.searchByGrandchild(findItemNum, name, bigCategory, middleCategory,
					smallCategory, brand, model);
		}

		// 親と子カテゴリのみ入力された場合
		else if (!StringUtils.isEmpty(bigCategory) && !StringUtils.isEmpty(middleCategory)
				&& StringUtils.isEmpty(smallCategory)) {
			System.out.println("親子の場合(controller)");
			itemList = itemSearchService.searchByParentAndChild(findItemNum, name, bigCategory, middleCategory,
					smallCategory, brand, model);
		}

		// 親と孫カテゴリのみ入力された場合
		else if (!StringUtils.isEmpty(bigCategory) && StringUtils.isEmpty(middleCategory)
				&& !StringUtils.isEmpty(smallCategory)) {
			System.out.println("親孫の場合(controller)");
			// 検索は行わない
			itemList = itemSearchService.searchByParentAndGrandchild(findItemNum, name, bigCategory, middleCategory,
					smallCategory, brand, model);
		}

		// 子と孫カテゴリのみ入力された場合
		else if (StringUtils.isEmpty(bigCategory) && !StringUtils.isEmpty(middleCategory)
				&& !StringUtils.isEmpty(smallCategory)) {
			System.out.println("子孫の場合(controller)");
			itemList = itemSearchService.searchByChildAndGrandchild(findItemNum, name, bigCategory, middleCategory,
					smallCategory, brand, model);
		} else {
			System.out.println("カテゴリのみ何も入っていない場合(controller,else)");
			// itemList = itemRepository.findPagingItem(findItemNum);
			itemList = itemSearchService.searchByNonCategory(findItemNum, name, bigCategory, middleCategory,
					smallCategory, brand, model);
		}

		/*
		 * リストに情報が入っていなかった時の処理 全件検索を行い追加でエラーを返す
		 */
		if (itemList.isEmpty() || itemList.size() == 0) {
			System.out.println("検索結果なし(repository)");
			System.out.println("申し訳ございません。検索結果に一致する結果がありませんでした");
			model.addAttribute("errorMessage", "申し訳ございません。検索結果に一致する結果がありませんでした");
			itemList = itemRepository.findItem(findItemNum);
		}

		Timestamp startDateTime = null;
		Timestamp endDateTime = null;
		Timestamp nowDateTime = new Timestamp(System.currentTimeMillis());
		// リストのカテゴリを整理
		for (Item item : itemList) {
			if (item.getBigCategory() == null) {
				item.setCut(false);
			}

			startDateTime = Timestamp.valueOf(item.getSaleStart());
			endDateTime = Timestamp.valueOf(item.getSaleEnd());
			// Timestamp stubDateTime = Timestamp.valueOf("1970-01-01 00:30:00.0");

			if (startDateTime.before(nowDateTime) && endDateTime.after(nowDateTime)) {
				item.setSaleFlug(true);
			}
		}

		// ここでリストを配列に変換
		Item[] itemsList = itemList.toArray(new Item[itemList.size()]);
		System.err.println("検索処理おわり");
		return itemsList;
	}

}
