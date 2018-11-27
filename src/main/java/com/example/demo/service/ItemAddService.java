package com.example.demo.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.repository.ItemRepository;

/**
 * 商品の登録処理を行うメソッド.
 * 
 * @author rks
 *
 */
@Service
public class ItemAddService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * カテゴリテーブル系の登録処理を行うメソッド.
	 * 
	 * @param bigCategory
	 *            親カテゴリ
	 * @param middleCategory
	 *            子カテゴリ
	 * @param smallCategory
	 *            孫カテゴリ
	 * @return カテゴリid
	 */
	public Integer AddCategory(String bigCategory, String middleCategory, String smallCategory) {
		// カテゴリーをcategoryテーブルに追加する
		itemRepository.itemAddCategory(bigCategory, middleCategory, smallCategory);
		// CategoryAllテーブル用の文字列結合
		String categoryNameAll = bigCategory + "/" + middleCategory + "/" + smallCategory;
		Integer categoryId = itemRepository.findCategoryId(categoryNameAll);
		return categoryId;
	}

	/**
	 * 商品を追加するメソッド.
	 * 
	 * @param name
	 *            商品名
	 * @param condition
	 *            コンディション
	 * @param category
	 *            カテゴリ
	 * @param brand
	 *            ブランド
	 * @param price
	 *            価格
	 * @param description
	 *            商品説明
	 * @param imagefile
	 *            画像ファイル
	 * @return 成否判定結果
	 */
	public boolean ItemAdd(String name, Integer condition, Integer category, String brand, double price,
			String description, MultipartFile imagefile) {
		// 処理成功時にはtrueを返すようにする.
		Boolean result = false;
		// ファイルが空だった時は画像ファイルをスルーしたインサート文を記述する
		if (imagefile.isEmpty()) {
			// ファイルのデータ量が0byteの時には画像なしで商品を登録
			itemRepository.noPicItemAdd(name, condition, category, brand, price, description);
			result = true;
			return result;
		}
		try {
			// ファイルの型を取得する
			// contentTypeは「ファイル種類/ファイルの型式」の形で出力されるため/で区切り取得する
			String imagePath = imagefile.getContentType();
			StringBuilder sb = new StringBuilder(imagePath);
			int fileTypePunctuation = sb.indexOf("/");
			String fileType = sb.substring(fileTypePunctuation + 1);

			// ファイル形式が当てはまらない場合画像登録処理はスルーする
			if (!fileType.equals("jpg") && !fileType.equals("png") && !fileType.equals("gif") && !fileType.equals("jpeg")) {
				itemRepository.noPicItemAdd(name, condition, category, brand, price, description);
				result = true;
			} else {

				 System.out.println("送信された画像ファイルは" + fileType + "形式です");

				// ファイルに追加する日付
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
				
				String fileName = "pic"+sdf.format(now)+"." + fileType;

				// ファイルパスの作成
				StringBuffer filePath = new StringBuffer("C:\\env\\spring workspace\\Mercari\\src\\main\\webapp\\image\\itemPictures\\"+fileName);

				System.out.println("保存先は"+filePath.toString());
				// 作成したパスをもとにファイルオブジェクトを生成
				File addItemFile = new File(filePath.toString());

				// 送信されてきたファイルをbyte形式に変換
				byte[] bytes = imagefile.getBytes();

				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(addItemFile));
				// 商品追加。追加のに必要な要素であるcategory情報をカテゴリーの名前から取得している
//				itemRepository.itemAdd(name, condition, category, brand, price, description, filePath.toString());
				itemRepository.itemAdd(name, condition, category, brand, price, description, fileName);
				// byteデータをもとにファイルの書き込み
				bos.write(bytes);
				// クローズしてメモリ開放
				bos.close();

				// 成功したときにはフラグをtrueにする
				result = true;
			}
		} catch (Exception e) {
			System.err.println(e);
			result = false;
			return result;
		}
		return result;
	}

	/**
	 * 商品リストの最後の商品IDを取得するメソッド.
	 * @return id
	 */
	public Integer findFinalItemId() {
		String tableName = "items";
		Integer id = itemRepository.findFinalId(tableName);
		return id;
	}

	// /**
	// * アップロードしたファイルの格納場所を作成
	// *
	// * @param filePath
	// * 格納するファイルの名前
	// * @return 作成したファイルのオブジェクト
	// */
	// private File makeDirectory(StringBuffer filePath) {
	// Date now = new Date();
	// SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
	// File uploadDir = new File(filePath.toString(), sdf.format(now));
	//
	// int prefix = 0;
	// while (uploadDir.exists()) {
	// prefix++;
	// uploadDir = new File(filePath.toString(), sdf.format(now) + "-" +
	// String.valueOf(prefix));
	// }
	// // ファイル作成
	// uploadDir.mkdir();
	// return uploadDir;
	// }
}
