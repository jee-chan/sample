package com.example.demo.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.ItemForm;
import com.example.demo.form.LoginUserForm;
import com.example.demo.logMsg.ItemMsg;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemAddService;

/**
 * 商品追加に関するコントローラ.
 * 
 * @author rks
 *
 */
@Controller
@RequestMapping("/addItem")
public class AddItemController {

	@Autowired
	private ItemMsg itemMsg;
	@Autowired
	private ItemDetailController itemDetailController;
	@Autowired
	private ItemAddService itemAddService;
	@Autowired
	private ItemRepository itemRepository;

	@ModelAttribute
	public LoginUserForm setUpLoginUserForm() {
		LoginUserForm form = new LoginUserForm();
		return form;
	};

	@ModelAttribute
	ItemForm setUpForm() {
		return new ItemForm();
	}

	private Logger logger = LoggerFactory.getLogger(AddItemController.class);

	/**
	 * 商品追加ページの表示を行うメソッド.
	 * 
	 * @param model
	 *            モデル
	 * @return 商品追加ページ
	 */
	@RequestMapping("/")
	public String view(Model model) {
		// ページ内のプルダウンメニュー表示のためにリストを送信
		List<String> bigCategoryList = itemRepository.getBigCategoryList();
		List<String> middleCategoryList = itemRepository.getMiddleCategoryList();
		List<String> smallCategoryList = itemRepository.getSmallCategoryList();

		model.addAttribute("bigCategoryList", bigCategoryList);
		model.addAttribute("middleCategoryList", middleCategoryList);
		model.addAttribute("smallCategoryList", smallCategoryList);
		return "jsp/itemAdd";
	}

	/**
	 * 商品追加を行うメソッド.
	 * 
	 * @param itemform
	 *            入力された情報を受け取るフォーム
	 * @param model
	 *            モデル
	 * @return viewメソッドを呼び出して再度追加ページに遷移
	 */
	@RequestMapping("/add")
	synchronized public String addItem(@Validated ItemForm itemform, BindingResult result, Model model, Exception e) {

		logger.info(itemMsg.start("addItem"));
		if (result.hasErrors()) {
			logger.warn(itemMsg.errorMsg("addItem"));
			logger.warn(itemMsg.errorCase() + e);
			logger.info("==============================");
			logger.info(itemMsg.end("addItem"));
			return view(model);
		}

		// 入力された情報をログ出力
		itemMsg.insertItemOutput(logger, itemform);
		// カテゴリテーブル登録処理
		Integer categoryId = itemAddService.AddCategory(itemform.getBigCategory(), itemform.getMiddleCategory(),
				itemform.getSmallCategory());
		// itemsテーブル登録処理
		Boolean res = itemAddService.ItemAdd(itemform.getName(), itemform.getCondition(), categoryId,
				itemform.getBrand(), itemform.getPrice(), itemform.getDescription(), itemform.getPicture());
		if (res) {
			model.addAttribute("message", "商品を追加しました");
			Integer id = itemAddService.findFinalItemId();
			logger.info(itemMsg.end("addItem"));
			//DBからidを取得する処理を記述
//			Integer itemId = null;
//			return "redirect/itemdetail/?id=";
			System.out.println("追加には成功しています。");
			return itemDetailController.view(model, id);
//			return "/itemdetail/?id="+id;
		} else {
			model.addAttribute("message", "追加に失敗しました!!!");
			return view(model);
		}
	}
}
