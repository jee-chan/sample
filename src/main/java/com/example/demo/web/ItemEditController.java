package com.example.demo.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.domain.Item;
import com.example.demo.form.ItemForm;
import com.example.demo.form.LoginUserForm;
import com.example.demo.logMsg.ItemMsg;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemEditService;

/**
 * 商品編集関係のコントローラーです.
 * 
 * @author rks
 *
 */
@Transactional(rollbackFor=Exception.class)
@Controller
@RequestMapping("/editItem")
public class ItemEditController {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemEditService itemEditService;

	@Autowired
	private ItemDetailController itemDetailController;

	@Autowired
	private ItemMsg itemMsg;

	@ModelAttribute
	public LoginUserForm setUpLoginUserForm() {
		LoginUserForm form = new LoginUserForm();
		return form;
	};

	@ModelAttribute
	ItemForm setUpForm() {
		return new ItemForm();
	}

	// ログ出力のためのオブジェクト生成
	private static final Logger logger = LoggerFactory.getLogger(ItemEditController.class);

	/**
	 * 商品編集ページを表示するメソッド.
	 * 
	 * @param model
	 * @return 商品編集画面
	 */
	@RequestMapping("/")
	public String view(ItemForm form, Model model, Integer id) {
		//編集に必要なカテゴリリストの取得
		List<String> bigCategoryList = itemRepository.getBigCategoryList();
		List<String> middleCategoryList = itemRepository.getMiddleCategoryList();
		List<String> smallCategoryList = itemRepository.getSmallCategoryList();
		//パラメータで渡したidをもとに商品取得
		Item item = itemRepository.findById(id);

		form.setDescription(item.getDescription());
		model.addAttribute("bigCategoryList", bigCategoryList);
		model.addAttribute("middleCategoryList", middleCategoryList);
		model.addAttribute("smallCategoryList", smallCategoryList);
		model.addAttribute("item", item);
		int itemIntPrice = (int) item.getPrice();
		model.addAttribute("itemIntPrice", itemIntPrice);
		model.addAttribute("id", id);
		return "jsp/itemEdit";
	}

	/**
	 * 商品編集を行うメソッド.
	 * 
	 * @param itemform
	 *            アイテムフォーム
	 * @param res
	 *            バインディングリザルト
	 * @param id
	 *            id
	 * @param model
	 *            モデル
	 * @return 商品編集画面
	 */
	@RequestMapping("/edit")
	synchronized public String editItem(@Validated ItemForm itemform, BindingResult res, Integer id, Model model, Exception e) {
		logger.info(itemMsg.start("editItem"));
		
		//入力値チェックでエラーが見つかった場合元の画面に戻す
		if (res.hasErrors()) {
			//ログに書き出し
			logger.warn(itemMsg.errorMsg("editItem"));
			logger.warn(res.toString());
			logger.error(itemMsg.errorCase());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTrace = sw.toString();
			logger.error(stackTrace);
			logger.info("==============================");
			logger.info(itemMsg.end("editItem"));

			//直前に入力された説明文を返す
			model.addAttribute("descriptionValue", itemform.getDescription());
			return view(itemform, model, id);
		}

		// 入力されたフォームの中身をログに書き出し
		itemMsg.insertItemOutput(logger, itemform);

		//

		// テーブルに合わせるためpriceをdouble型に変換
		double doublePrice = itemform.getPrice();

		// 商品カテゴリの編集とカテゴリIDの取得
		Integer categoryId = itemEditService.categoryEdit(itemform.getBigCategory(), itemform.getMiddleCategory(),
				itemform.getSmallCategory());

		// 商品編集処理
		// 結果はboolean型で成否を判定する
		//TODO フォームごと送信したほうがコードがすっきりする
		Boolean result = itemEditService.itemEdit(id, itemform.getName(), itemform.getCondition(), categoryId,
				itemform.getBrand(), doublePrice, itemform.getDescription(), itemform.getPicture(),
				itemform.getSaleStartDate(), itemform.getSaleStartTime(), itemform.getSaleEndDate(),
				itemform.getSaleEndTime(),itemform.getSalePrice());
		model.addAttribute("id", id);

		//編集が成功したらその商品の編集画面に、失敗すれば元の画面に
		if (result) {
			// ログ出力
			logger.info(itemMsg.end("editItem"));
			return itemDetailController.view(model, id);
		} else {

			model.addAttribute("message", "商品追加に失敗しました。");
			return view(itemform, model, id);
		}
	}
	
	
	//TODO 例外ハンドリングはインターフェース化して継承したほうが良さげですかね
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public Map<String, Object> notFound(){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("errorMessage", "ページが存在しないか、URLが変更された可能性があります。");
		errorMap.put("errorStatus", HttpStatus.NOT_FOUND);
		
		return errorMap;
	}
	
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public Map<String, Object> notFobidden(){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("errorMessage", "このページを閲覧することができません。URLをも一度ご確認ください");
		errorMap.put("errorStatus", HttpStatus.FORBIDDEN);
		
		return errorMap;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public Map<String, Object> internalServerError(){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("errorMessage", "申し訳ございません。システム内障害が発生しております。このページが長い間表示される場合はお手数ですが管理者までお知らせください。");
		errorMap.put("errorStatus", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return errorMap;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Exception.class})
	public Map<String, Object> otherError(){
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("errorMessage", "原因不明のエラーです。管理者までお問い合わせください");
		errorMap.put("errorStatus", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return errorMap;
	}
}
