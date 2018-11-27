package com.example.demo.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class ItemForm {
	/** ID */
	private Integer id;
	/** 名前 */
	@NotEmpty(message = "商品名を入力してください")
	private String name;
	/** コンディション */
	@NotNull(message = "コンディションを入力してください")
	private Integer condition;
	/** カテゴリー */
	private Integer category;
	/** ブランド */
	@NotEmpty(message = "ブランドを入力してください")
	private String brand;
	/** 価格 */
	@NotNull(message = "価格を入力してください")
	private Integer price;
	/** 説明文 */
	@NotEmpty(message = "商品説明を入力してください")
	private String description;
	/** 大カテゴリ */
	@NotBlank(message = "カテゴリを入力してください")
	private String bigCategory;
	/** 中カテゴリ */
	@NotBlank(message = "カテゴリを入力してください")
	private String middleCategory;
	/** 小カテゴリ */
	@NotBlank(message = "カテゴリを入力してください")
	private String smallCategory;
	/** 商品画像 */
	private MultipartFile picture;
	/** セール開始日付 */
	private String saleStartDate;
	/** セール開始時刻 */
	private String saleStartTime;
	/** セール終了日付 */
	private String saleEndDate;
	/** セール終了日時 */
	private String saleEndTime;
	/** セール価格 */
	private Integer salePrice;

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
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

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}

	public String getSaleStartDate() {
		return saleStartDate;
	}

	public void setSaleStartDate(String saleStartDate) {
		this.saleStartDate = saleStartDate;
	}

	public String getSaleStartTime() {
		return saleStartTime;
	}

	public void setSaleStartTime(String saleStartTime) {
		this.saleStartTime = saleStartTime;
	}

	public String getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleEndDate(String saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	public String getSaleEndTime() {
		return saleEndTime;
	}

	public void setSaleEndTime(String saleEndTime) {
		this.saleEndTime = saleEndTime;
	}

	public Integer getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

}
