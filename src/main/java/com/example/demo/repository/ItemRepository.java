package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;

/**
 * 商品情報のテーブルを操作するリポジトリ.
 * 
 * @author rks
 *
 */
@Repository
public class ItemRepository {
	// private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
	//
	// Integer id = rs.getInt("id");
	// String name = rs.getString("name");
	// Integer condition = rs.getInt("condition");
	// Integer category = rs.getInt("category");
	// String brand = rs.getString("brand");
	// double price = rs.getDouble("price");
	// String description = rs.getString("description");
	// String bigCategory = rs.getString("bigcategory");
	// String middleCategory = rs.getString("middlecategory");
	// String smallCategory = rs.getString("smallcategory");
	// String picture = rs.getString("picture");
	//
	// return new Item(id, name, condition, category, brand, price, description,
	// bigCategory, middleCategory,
	// smallCategory, picture);
	// };

	private static final RowMapper<Item> ITEM_ROW_MAPPER2 = (rs, i) -> {

		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		Integer condition = rs.getInt("condition");
		Integer category = rs.getInt("category");
		String brand = rs.getString("brand");
		double price = rs.getDouble("price");
		String description = rs.getString("description");
		String bigCategory = rs.getString("bigcategory");
		String middleCategory = rs.getString("middlecategory");
		String smallCategory = rs.getString("smallcategory");
		String picture = rs.getString("picture");
		String saleStart = rs.getString("startdate");
		String saleEnd = rs.getString("enddate");
		Integer salePrice = rs.getInt("saleprice");

		return new Item(id, name, condition, category, brand, price, description, bigCategory, middleCategory,
				smallCategory, picture, saleStart, saleEnd, salePrice);
	};

	@Autowired
	private NamedParameterJdbcTemplate template;
	@Autowired
	private JdbcTemplate temp;

	/**
	 * 全商品からはじめの30件を取得するメソッド.
	 * 
	 * @return すべての商品を格納したリスト
	 */
	public List<Item> findItem(Integer pageNum) {
		String sql = "SELECT a.id, a.name, a.condition, a.category, a.brand, a.price, a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS bigcategory, SPLIT_PART(b.name_all,'/',2) AS middlecategory, SPLIT_PART(b.name_all,'/',3) AS smallcategory, c.startdate AS startdate, c.enddate AS enddate, c.saleprice AS saleprice FROM (items AS a LEFT OUTER JOIN category AS b ON a.category = b.id) LEFT OUTER JOIN sale c on a.saleid = c.id ORDER BY a.id LIMIT 30 OFFSET "
				+ pageNum;
		// String sql = "SELECT a.id, a.name, a.condition, a.category, a.brand, a.price,
		// a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS bigcategory,
		// SPLIT_PART(b.name_all,'/',2) AS middlecategory, SPLIT_PART(b.name_all,'/',3)
		// AS smallcategory FROM items AS a LEFT OUTER JOIN category AS b ON a.category
		// = b.id ORDER BY a.id LIMIT 30 OFFSET "
		// + pageNum;
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER2);

		return itemList;
	}

	// /**
	// * 全商品から指定数以降30件ごとに検索を行うメソッド.
	// *
	// * @param pageNum
	// * ページ指定におけるid
	// * @return 商品情報が格納されたリスト
	// */
	// public List<Item> findPagingItem(Integer pageNum) {
	// String sql = "SELECT a.id, a.name, a.condition, a.category, a.brand, a.price,
	// a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS bigcategory,
	// SPLIT_PART(b.name_all,'/',2) AS middlecategory, SPLIT_PART(b.name_all,'/',3)
	// AS smallcategory FROM items AS a LEFT OUTER JOIN category AS b ON a.category
	// = b.id ORDER BY a.id LIMIT 30 OFFSET :pageNum";
	// SqlParameterSource param = new MapSqlParameterSource().addValue("pageNum",
	// pageNum);
	// List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
	//
	// return itemList;
	// }

	// /**
	// * すべての商品情報を取得してリストで返すメソッド.
	// *
	// * @return
	// */
	// public List<Item> getAllItemList() {
	// String sql = "SELECT a.id, a.name, a.condition, a.category, a.brand, a.price,
	// a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS bigcategory,
	// SPLIT_PART(b.name_all,'/',2) AS middlecategory, SPLIT_PART(b.name_all,'/',3)
	// AS smallcategory FROM items AS a LEFT OUTER JOIN category AS b ON a.category
	// = b.id ORDER BY a.id";
	// SqlParameterSource param = new MapSqlParameterSource();
	// List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
	//
	// return itemList;
	// }

	/**
	 * 商品情報を1件取得するメソッド
	 * 
	 * @return
	 */
	public Item getItem(Integer i) {
		String sql = "SELECT a.id, a.name, a.condition, a.category, a.brand, a.price, a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS bigcategory, SPLIT_PART(b.name_all,'/',2) AS middlecategory, SPLIT_PART(b.name_all,'/',3) AS smallcategory, c.startdate AS startdate, c.enddate AS enddate, c.saleprice AS saleprice FROM (items AS a LEFT OUTER JOIN category AS b ON a.category = b.id) LEFT OUTER JOIN sale c on a.saleid = c.id ORDER BY a.id BETWEEN :i AND :i ORDER BY a.id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("i", i);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER2);

		return item;
	}

	/**
	 * 商品リストのcsvファイルを作成するメソッド.
	 * 
	 * @param filename
	 *            生成するファイル名
	 */
	public void createItemListCsv(String filename) {
		// TODO:
		// このメソッドでParameterSourseを渡さずに直接変数を埋め込んでいるのはCOPYコマンドを打ち込む際にParameterSourseで値を送信するとpostgresが認識できずにエラーを返してしまうためです.
		String sql = "COPY (SELECT a.id, a.name, a.condition, a.category, a.brand, a.price, a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS bigcategory, SPLIT_PART(b.name_all,'/',2) AS middlecategory, SPLIT_PART(b.name_all,'/',3) AS smallcategory, c.startdate AS startdate, c.enddate AS enddate, c.saleprice AS saleprice FROM (items AS a LEFT OUTER JOIN category AS b ON a.category = b.id) LEFT OUTER JOIN sale c on a.saleid = c.id ORDER BY a.id) TO '"
				+ filename + "' WITH NULL AS '' CSV DELIMITER ','  encoding 'UTF-8' HEADER FORCE QUOTE *;";
		// String sql = "COPY (SELECT a.id, a.name, a.condition, a.category, a.brand,
		// a.price, a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS
		// bigcategory, SPLIT_PART(b.name_all,'/',2) AS middlecategory,
		// SPLIT_PART(b.name_all,'/',3) AS smallcategory FROM items AS a LEFT OUTER JOIN
		// category AS b ON a.category = b.id ORDER BY a.id) TO '"
		// + filename + "' WITH NULL AS '' CSV DELIMITER ',' encoding 'UTF-8' HEADER
		// FORCE QUOTE *;";
		SqlParameterSource param = new MapSqlParameterSource();
		template.update(sql, param);
	}

	/**
	 * 商品の総件数を取得するメソッド.
	 * 
	 * @return 商品総件数
	 */
	public Integer getItemQuantity() {
		String sql = "SELECT count(*) FROM items";
		Integer itemQuantity = temp.queryForObject(sql, Integer.class);
		return itemQuantity;
	}

	/**
	 * IDをもとに商品を検索するメソッド.
	 * 
	 * @return 検索した商品
	 */
	public Item findById(Integer id) {
		String sql = "SELECT a.id, a.name, a.condition, a.category, a.brand, a.price, a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS bigcategory, SPLIT_PART(b.name_all,'/',2) AS middlecategory, SPLIT_PART(b.name_all,'/',3) AS smallcategory, c.startdate AS startdate, c.enddate AS enddate, c.saleprice AS saleprice FROM (items AS a LEFT OUTER JOIN category AS b ON a.category = b.id) LEFT OUTER JOIN sale c on a.saleid = c.id WHERE a.id= :id ORDER BY a.id";
		// String sql = "SELECT a.id, a.name, a.condition, a.category, a.brand, a.price,
		// a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS bigcategory,
		// SPLIT_PART(b.name_all,'/',2) AS middlecategory, SPLIT_PART(b.name_all,'/',3)
		// AS smallcategory FROM items AS a LEFT OUTER JOIN category AS b ON a.category
		// = b.id WHERE a.id= :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER2);

		return item;
	}

	/**
	 * 条件に応じた検索を行うメソッド
	 * 
	 * @param searchParamList
	 *            検索を行う条件を格納したリスト
	 * @return 検索結果を格納したリスト
	 */
	public List<Item> searchItemByWord(String whereWord, Integer pageNum) {

		String sql = "SELECT a.id, a.name, a.condition, a.category, a.brand, a.price, a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS bigcategory, SPLIT_PART(b.name_all,'/',2) AS middlecategory, SPLIT_PART(b.name_all,'/',3) AS smallcategory, c.startdate AS startdate, c.enddate AS enddate, c.saleprice AS saleprice FROM (items AS a LEFT OUTER JOIN category AS b ON a.category = b.id) LEFT OUTER JOIN sale c on a.saleid = c.id ORDER BY a.id WHERE "
				// String sql = "SELECT a.id, a.name, a.condition, a.category, a.brand, a.price,
				// a.description, a.picture, SPLIT_PART(b.name_all,'/',1) AS bigcategory,
				// SPLIT_PART(b.name_all,'/',2) AS middlecategory, SPLIT_PART(b.name_all,'/',3)
				// AS smallcategory FROM items AS a LEFT OUTER JOIN category AS b ON a.category
				// = b.id WHERE "
				+ whereWord + " ORDER BY a.id LIMIT 30 OFFSET " + pageNum;

		System.out.println("SQL文はこれです -> " + sql);

		if (sql.matches(".*AND  .*")) {
			// sql = whereWord.substring(0, whereWord.length()-5);
			sql = sql.replaceAll("AND  ", "");
			System.out.println("切りとったSQLです　-> " + sql);
		}

		// SqlParameterSource param = new MapSqlParameterSource().addValue("whereWord",
		// whereWord);
		// List<Item> itemList = template.queryForList(sql, param, Item.class);
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER2);

		return itemList;
	}

	/**
	 * 親カテゴリすべてをリストで取得するメソッド.
	 * 
	 * @return 親カテゴリのリスト
	 */
	public List<String> getBigCategoryList() {
		// parentが存在しない行のnameを取得
		String sql = "SELECT DISTINCT name FROM category WHERE parent IS NULL ORDER BY name";
		List<String> bigCategoryList = temp.queryForList(sql, String.class);
		return bigCategoryList;
	}

	/**
	 * 子カテゴリすべてをリストで取得するメソッド.
	 * 
	 * @return 子カテゴリのリスト
	 */
	public List<String> getMiddleCategoryList() {
		// name_allが存在しなくかつparentがある行のnameを取得
		String sql = "SELECT DISTINCT name FROM category WHERE name_all IS NULL AND parent IS NOT NULL ORDER BY name";
		List<String> middleCategoryList = temp.queryForList(sql, String.class);
		return middleCategoryList;
	}

	/**
	 * 孫カテゴリすべてをリストで取得するメソッド.
	 * 
	 * @return 孫カテゴリのリスト
	 */
	public List<String> getSmallCategoryList() {
		// nama_allの存在する行のnameを取得
		String sql = "SELECT DISTINCT name FROM category WHERE name_all IS NOT NULL ORDER BY name";
		List<String> smallCategoryList = temp.queryForList(sql, String.class);
		return smallCategoryList;
	}

	/**
	 * 追加された商品をカテゴリテーブルに挿入するメソッド.
	 * 
	 * @param bigCategoryName
	 *            親カテゴリ
	 * @param middleCategoryName
	 *            子カテゴリ
	 * @param smallCategoryName
	 *            孫カテゴリ
	 */
	public void itemAddCategory(String bigCategoryName, String middleCategoryName, String smallCategoryName) {
		// sqlparamのSQLインジェクション対策を行う.
		String bigCategorySql = "INSERT INTO category (parent, name) SELECT null, '" + bigCategoryName
				+ "' WHERE NOT EXISTS ( SELECT name FROM category WHERE name = '" + bigCategoryName
				+ "' AND parent IS NULL)";
		String middleCategorySql = "INSERT INTO category (parent, name) SELECT (SELECT id FROM category WHERE name = '"
				+ bigCategoryName + "' ORDER BY id LIMIT 1), '" + middleCategoryName
				+ "' WHERE NOT EXISTS ( SELECT name, parent FROM category WHERE (name,parent) IN (('"
				+ middleCategoryName + "',(SELECT id FROM category WHERE name = '" + bigCategoryName
				+ "' AND parent IS NULL ORDER BY id LIMIT 1))))";
		String smallCategorySql = "INSERT INTO category (parent, name, name_all) SELECT (SELECT id FROM category WHERE name = '"
				+ middleCategoryName + "' ORDER BY id LIMIT 1), '" + smallCategoryName + "', '" + bigCategoryName + "/"
				+ middleCategoryName + "/" + smallCategoryName
				+ "' WHERE NOT EXISTS (SELECT * FROM category WHERE name_all = '" + bigCategoryName + "/"
				+ middleCategoryName + "/" + smallCategoryName + "')";

		temp.update(bigCategorySql);
		temp.update(middleCategorySql);
		temp.update(smallCategorySql);
	}

	/**
	 * カテゴリーを検索してIDを取得するメソッドです.
	 * 
	 * @param nameAll
	 *            総カテゴリ
	 * @return 検索したカテゴリのID
	 */
	public Integer findCategoryId(String nameAll) {
		String sql = "SELECT id FROM category WHERE name_all = :nameAll";
		SqlParameterSource param = new MapSqlParameterSource().addValue("nameAll", nameAll);
		// id0は存在しないのでそのまま判定に用います
		Integer categoryId = 0;
		try {
			categoryId = template.queryForObject(sql, param, Integer.class);
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("検索したカテゴリに合致するものがありませんでした。または何らかのエラーの可能性があります");
		}

		return categoryId;
	}

	/**
	 * itemsテーブルに商品を登録するメソッド
	 * 
	 * @param name
	 *            名前
	 * @param condition
	 *            コンディション
	 * @param category
	 *            カテゴリテーブルから取得したID
	 * @param brand
	 *            ブランド
	 * @param price
	 *            価格
	 * @param description
	 *            商品説明文
	 * @param imagePath
	 *            画像の保存パス
	 */
	public void itemAdd(String name, Integer condition, Integer category, String brand, double price,
			String description, String imagePath) {
		// shippingはフォームに入力欄がないのでとりあえず初期値として0を挿入

		String sql = "INSERT INTO items (name,condition,category,brand,price,shipping,description,picture) VALUES(:name,:condition,:category,:brand,:price,0,:description,:imagePath)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name).addValue("condition", condition)
				.addValue("category", category).addValue("brand", brand).addValue("price", price)
				.addValue("description", description).addValue("imagePath", imagePath);
		template.update(sql, param);
	}

	/**
	 * 画像なしで商品を登録するメソッド.
	 * 
	 * @param name
	 *            名前
	 * @param condition
	 *            コンディション
	 * @param category
	 *            カテゴリテーブルから取得したID
	 * @param brand
	 *            ブランド
	 * @param price
	 *            価格
	 * @param description
	 *            商品説明文
	 */
	public void noPicItemAdd(String name, Integer condition, Integer category, String brand, double price,
			String description) {
		// shippingはフォームに入力欄がないのでとりあえず初期値として0を挿入

		String sql = "INSERT INTO items (name,condition,category,brand,price,shipping,description) VALUES(:name,:condition,:category,:brand,:price,0,:description)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name).addValue("condition", condition)
				.addValue("category", category).addValue("brand", brand).addValue("price", price)
				.addValue("description", description);
		template.update(sql, param);
	}

	/**
	 * テーブルの最後のカラムIDを取得するメソッド.
	 * 
	 * @param tableName
	 *            テーブルの名前
	 * @return カラムID
	 */
	public Integer findFinalId(String tableName) {
		String sql = "SELECT id FROM " + tableName + " WHERE id=(SELECT max(id) FROM " + tableName + ")";
		SqlParameterSource param = new MapSqlParameterSource();
		Integer columId = template.queryForObject(sql, param, Integer.class);
		return columId;
	}

	/**
	 * 商品情報を更新するメソッド.
	 * 
	 * @param name
	 *            名前
	 * @param condition
	 *            コンディション
	 * @param category
	 *            カテゴリー
	 * @param brand
	 *            ブランド
	 * @param price
	 *            価格
	 * @param description
	 *            商品説明
	 * @param imagePath
	 *            画像のパス
	 */
	public void itemEdit(Integer id, String name, Integer condition, Integer category, String brand, double price,
			String description, String imagePath, Integer saleId) {
		String sql = "UPDATE items SET name=:name, condition=:condition, category=:category, brand=:brand, price=:price, shipping=0, description=:description, picture=:imagePath, saleid=:saleId WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id).addValue("name", name)
				.addValue("condition", condition).addValue("category", category).addValue("brand", brand)
				.addValue("price", price).addValue("description", description).addValue("imagePath", imagePath)
				.addValue("saleId", saleId);
		template.update(sql, param);
		System.out.println("SQL 成功！");
	}

	/**
	 * 画像なしの商品情報を更新するメソッド.
	 * 
	 * @param name
	 *            名前
	 * @param condition
	 *            コンディション
	 * @param category
	 *            カテゴリー
	 * @param brand
	 *            ブランド
	 * @param price
	 *            価格
	 * @param description
	 *            商品説明
	 */
	public void noPicItemEdit(Integer id, String name, Integer condition, Integer category, String brand, double price,
			String description, Integer saleId) {
		String sql = "UPDATE items SET name=:name, condition=:condition, category=:category, brand=:brand, price=:price, shipping=0, description=:description, saleid=:saleId WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id).addValue("name", name)
				.addValue("condition", condition).addValue("category", category).addValue("brand", brand)
				.addValue("price", price).addValue("description", description).addValue("saleId", saleId);
		template.update(sql, param);
	}

	/**
	 * セールテーブルにデータを挿入してIDを返却するメソッド
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param salePrice
	 * @return
	 */
	public Integer saleInsertAndGetId(Timestamp startDateTime, Timestamp endDateTime, double salePrice) {
		// データの挿入
		String sql = "INSERT INTO sale (startdate, enddate, saleprice) VALUES(:startDateTime, :endDateTime, :salePrice)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("startDateTime", startDateTime)
				.addValue("endDateTime", endDateTime).addValue("salePrice", salePrice);
		template.update(sql, param);
		// 挿入したデータidの取得
		String tableName = "sale";
		Integer saleId = findFinalId(tableName);
		return saleId;
	}
}
