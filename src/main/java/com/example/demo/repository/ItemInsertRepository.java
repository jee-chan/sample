package com.example.demo.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.example.demo.domain.ItemCategory;

/**
 * DBに挿入する商品関連の操作を行うリポジトリクラス.
 * @author rks
 *
 */
@Repository
public class ItemInsertRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	@Autowired
	private JdbcTemplate temp;
	
	// インデックスの上限を引き上げる処理
	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//	    binder.setAutoGrowCollectionLimit(1500000);
//	}
	
	/**
	 * csvファイルから文字列を切り出してリスト化するメソッド.
	 * @return
	 * @throws IOException
	 */
	public List<ItemCategory> categoryExtraction() throws IOException {
		List<ItemCategory> categoryList = new ArrayList<>();
		
		//カテゴリ情報を持ったファイルの読み込み
		Path path = Paths.get("C:\\env\\app\\postgresql\\importTsv\\mercari_category.csv");
		List<String> lines = Files.readAllLines(path);
		for(String line : lines) {
			
			//for文の中でnewしないと同じ値がリストに延々と格納されてしまう
			ItemCategory item = new ItemCategory();
			
			//シングルクォーテーションはSQL文で式として認識されてしまうので変換.
			if(line.contains("'")) {
				line=line.replaceAll(".'", "''");
			}
			
			//スラッシュ区切りで文章を分割してItemCategory型に格納する
			String[] data = line.split("/", 3);
			item.setBigCategory(data[0]);
			item.setMiddleCategory(data[1]);
			item.setSmallCategory(data[2]);
			categoryList.add(item);
		}
		System.out.println("リスト化完了");
		return categoryList;
	}

	
	/**
	 * DBの商品情報リストをもとにcategoryテーブルへインサートするメソッド.
	 * @throws IOException
	 */
	public void categoryInsert() throws IOException {
		List<ItemCategory> list = categoryExtraction();

		for(int i=0;i<list.size();i++) {
			//進行度合いを確認するカウント
			if(0==i%10000) {
				System.out.println(i);
				}
			String bigCategoryName = list.get(i).getBigCategory();
			String middleCategoryName = list.get(i).getMiddleCategory();
			String smallCategoryName = list.get(i).getSmallCategory();
			String bigCategorySql = "INSERT INTO category (parent, name) SELECT null, '" + bigCategoryName + "' WHERE NOT EXISTS ( SELECT name FROM category WHERE name = '" + bigCategoryName + "' AND parent IS NULL)";
			String middleCategorySql = "INSERT INTO category (parent, name) SELECT (SELECT id FROM category WHERE name = '" + bigCategoryName + "' ORDER BY id LIMIT 1), '" + middleCategoryName + "' WHERE NOT EXISTS ( SELECT name, parent FROM category WHERE (name,parent) IN (('" + middleCategoryName + "',(SELECT id FROM category WHERE name = '" + bigCategoryName + "' AND parent IS NULL ORDER BY id LIMIT 1))))";
			String smallCategorySql = "INSERT INTO category (parent, name, name_all) SELECT (SELECT id FROM category WHERE name = '" + middleCategoryName + "' ORDER BY id LIMIT 1), '" + smallCategoryName + "', '" + bigCategoryName + "/" + middleCategoryName + "/" + smallCategoryName + "' WHERE NOT EXISTS (SELECT * FROM category WHERE name_all = '" + bigCategoryName + "/" + middleCategoryName + "/" + smallCategoryName + "')";

			temp.update(bigCategorySql);
			temp.update(middleCategorySql);
			temp.update(smallCategorySql);
		}
		System.out.println("SQL処理完了");
	}
	
	
	/**
	 * originalテーブルの総件数を取得するメソッド.
	 * @return
	 */
	public Integer countOriginalTable() {
		String sql = "SELECT max(id) FROM original";
		Integer countResult = 0;
		countResult = temp.queryForObject(sql, Integer.class);
		return countResult;
	}
	
	/**
	 * itemsテーブルに挿入するためのメソッド.
	 */
	public void itemsTableInsert() {
		Integer originalTableNum = countOriginalTable();
		for(int i=0; i<originalTableNum; i++) {
			//進行度合いのカウント
			if(0==i%10000) {
				System.out.println(i);
				}
//			String sql = "INSERT INTO items(id, name, condition, category, brand, price, shipping, description)"
//					+ " SELECT a.id, a.name, a.condition_id, b.id, a.brand, a.price, a.shipping, a.description FROM original AS a INNER JOIN category AS b a.category_name = b.name_all WHERE a.id = " + (i+1) + " ORDER BY id";
			String sql = "INSERT INTO items(id, name, condition, category, brand, price, shipping, description) SELECT a.id, a.name, a.condition_id, b.id, a.brand, a.price, a.shipping, a.description FROM original AS a LEFT OUTER JOIN category AS b ON a.category_name = b.name_all WHERE a.id = " + i + "";
			
			temp.update(sql);
		}
		System.out.println("インサート完了");
	}
	
	/**
	 * brandテーブルに挿入するためのメソッド.
	 */
	public void brandTableInsert() {
		Integer originalTableNum = countOriginalTable();
		for(int i=0; i<originalTableNum; i++) {
			//進行度合いのカウント
			if(0==i%10000) {
				System.out.println(i);
				}
			String sql = "INSERT INTO brand(id, name) SELECT id, brand FROM items WHERE id = " + i + "";
			
			temp.update(sql);
		}
		System.out.println("インサート完了");
	}
}
