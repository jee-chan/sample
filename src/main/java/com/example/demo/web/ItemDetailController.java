package com.example.demo.web;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;

/**
 * 商品詳細を表示するコントローラー.
 * 
 * @author rks
 *
 */
@Controller
@RequestMapping("/itemdetail")
public class ItemDetailController {

	@Autowired
	ItemRepository itemRepository;

	/**
	 * 詳細画面を表示するメソッド.
	 * 
	 * @param model
	 *            モデル
	 * @param id
	 *            id
	 * @return 詳細ページ
	 */
	@RequestMapping("/")
	public String view(Model model, Integer id) {
		System.out.println("詳細表示を行うID===> " + id);
		Item item = itemRepository.findById(id);
		if (item.getBigCategory() == null || "".equals(item.getBigCategory()) || item.getBigCategory().isEmpty()) {
			item.setCut(false);
		}

		Timestamp startDateTime = Timestamp.valueOf(item.getSaleStart());
		Timestamp endDateTime = Timestamp.valueOf(item.getSaleEnd());
		Timestamp nowDateTime = new Timestamp(System.currentTimeMillis());
//		Timestamp stubDateTime = Timestamp.valueOf("1970-01-01 00:30:00.0");

		if (startDateTime.before(nowDateTime) && endDateTime.after(nowDateTime)) {
			item.setSaleFlug(true);
		}

		model.addAttribute("item", item);

		return "jsp/itemDetail";
		// return "itemDetail";
	}

}
