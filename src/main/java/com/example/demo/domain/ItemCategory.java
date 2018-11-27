package com.example.demo.domain;

/**
 * 商品のカテゴリー分類のメソッドです.
 * @author rks
 *
 */
public class ItemCategory {

	private String bigCategory;
	private String middleCategory;
	private String smallCategory;
	
	
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
	
}
