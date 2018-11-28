package com.example.demo.domain;

import java.sql.Time;
import java.util.Date;

/**
 * 商品情報を格納するドメインクラス.
 * 
 * @author rks
 *
 */
public class Item {
	/** ID */
	private Integer id;
	/** 名前 */
	private String name;
	/** コンディション */
	private Integer condition;
	/** カテゴリー */
	private Integer category;
	/** ブランド */
	private String brand;
	/** 価格 */
	private double price;
	/** 説明文 */
	private String description;
	/** 大カテゴリ */
	private String bigCategory;
	/** 中カテゴリ */
	private String middleCategory;
	/** 小カテゴリ */
	private String smallCategory;
	/** 商品画像 */
	private String picture;
	/** セール終了の日付 */
	private Date saleStartDate;
	/** セール終了の時刻 */
	private Time saleStartTime;
	/** セール終了の日付 */
	private Date saleEndDate;
	/** セール終了の時刻 */
	private Time saleEndTime;
	/** セール開始の日時（日付と時刻合わせたもの） */
	private String saleStart;
	/** セール終了の日時（日付と時刻合わせたもの） */
	private String saleEnd;
	/** セール価格 */
	private Integer salePrice;
	/** カテゴリ有無 */
	private boolean cut = true;
	/** セール判定フラグ */
	private boolean saleFlug = false;

	public Item(Integer id, String name, Integer condition, Integer category, String brand, double price,
			String description, String bigCategory, String middleCategory, String smallCategory, String picture,
			String saleStart, String saleEnd, Integer salePrice) {
		this.id = id;
		this.name = name;
		this.condition = condition;
		this.category = category;
		this.brand = brand;
		this.price = price;
		this.description = description;
		this.bigCategory = bigCategory;
		this.middleCategory = middleCategory;
		this.smallCategory = smallCategory;
		this.picture = picture;
		this.saleEnd = saleEnd;
		this.saleStart = saleStart;
		this.salePrice = salePrice;

	};

	public Item(Integer id, String name, Integer condition, Integer category, String brand, double price,
			String description, String bigCategory, String middleCategory, String smallCategory, String picture) {
		this.id = id;
		this.name = name;
		this.condition = condition;
		this.category = category;
		this.brand = brand;
		this.price = price;
		this.description = description;
		this.bigCategory = bigCategory;
		this.middleCategory = middleCategory;
		this.smallCategory = smallCategory;
		this.picture = picture;

	};

	public Item() {
	};

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBigCategory() {
		return bigCategory;
	}

	public void setBigCategory(String bigCategory) {
		this.bigCategory = bigCategory;
	}

	public String getMiddleCategory() {
		return middleCategory;
	}

	public void setMiddleCategory(String middleCategory) {
		this.middleCategory = middleCategory;
	}

	public String getSmallCategory() {
		return smallCategory;
	}

	public void setSmallCategory(String smallCategory) {
		this.smallCategory = smallCategory;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isCut() {
		return cut;
	}

	public void setCut(boolean cut) {
		this.cut = cut;
	}

	public Date getSaleStartDate() {
		return saleStartDate;
	}

	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}

	public Time getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(Time saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public Date getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	public Time getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(Time saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public String getSaleStart() {
		return saleStart;
	}

	public void setSaleStart(String saleStart) {
		this.saleStart = saleStart;
	}

	public String getSaleEnd() {
		return saleEnd;
	}

	public void setSaleEnd(String saleEnd) {
		this.saleEnd = saleEnd;
	}

	public Integer getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	public boolean isSaleFlug() {
		return saleFlug;
	}


	public void setSaleFlug(boolean saleFlug) {
		this.saleFlug = saleFlug;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", condition=" + condition + ", category=" + category + ", brand="
				+ brand + ", price=" + price + ", description=" + description + ", bigCategory=" + bigCategory
				+ ", middleCategory=" + middleCategory + ", smallCategory=" + smallCategory + ", cut=" + cut + "]";
	}

}
