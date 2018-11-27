package com.example.demo.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.repository.ItemRepository;

/**
 * 商品編集関連のサービスクラスです.
 * 
 * @author rks
 *
 */
@Service
public class ItemEditService {

	private static final Logger logger = LoggerFactory.getLogger(ItemEditService.class);
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品カテゴリの編集を行うメソッド.
	 * 
	 * @param bigCategor
	 *            親カテゴリ
	 * @param middleCategory
	 *            子カテゴリ
	 * @param smallCategory
	 *            孫カテゴリ
	 * @return 編集したカテゴリのID
	 */
	public Integer categoryEdit(String bigCategory, String middleCategory, String smallCategory) {
		// カテゴリーをcategoryテーブルに追加する
		itemRepository.itemAddCategory(bigCategory, middleCategory, smallCategory);
		// 商品編集。itemsテーブル編集の際に必要な要素であるcategory情報を入力されたカテゴリーの名前を用いてcategoryテーブルから取得している
		String categoryNameAll = bigCategory + "/" + middleCategory + "/" + smallCategory;
		Integer categoryId = itemRepository.findCategoryId(categoryNameAll);

		return categoryId;
	}

	/**
	 * 商品編集操作を行うメソッド.
	 * 
	 * @param id
	 *            id
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
	 * @param imageFile
	 *            送信されてきたファイル
	 * @return 商品追加結果
	 */
	public boolean itemEdit(Integer id, String name, Integer condition, Integer category, String brand, double price,
			String description, MultipartFile imageFile, String saleStartDate, String saleStartTime, String saleEndDate,
			String saleEndTime, Integer salePrice) {
		// TODO: 画像の例外処理の記述相談する
		// 処理成功時にはtrueを返すようにする.
		boolean result = false;

		// セール設定の有無を確認する
		// セール条件すべてが入力されていた時にはセール追加処理を行う
		Integer saleId = 0;
		if (saleStartDate != null || saleStartTime != null || saleEndDate != null || saleEndTime != null
				|| salePrice != null) {
			String saleStartDatetime = dateTimeGenerate(saleStartDate, saleStartTime);
			String saleEndDatetime = dateTimeGenerate(saleEndDate, saleEndTime);
			Timestamp startDateTime = null;
			Timestamp endDateTime = null;
			try {
				startDateTime = new Timestamp(
						new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(saleStartDatetime).getTime());
				endDateTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(saleEndDatetime).getTime());
			} catch (ParseException e) {
				// TODO 失敗した場合falseで返して適切か考える
				logger.error("日付の変換に失敗しました");
				e.printStackTrace();
			}
			String sp = salePrice.toString();
			double insertsalePrice = Double.parseDouble(sp);
			saleId = itemRepository.saleInsertAndGetId(startDateTime, endDateTime, insertsalePrice);
		}

		// ファイルが空だった時は画像ファイルをスルーしたインサート文を記述する
		if (imageFile.isEmpty()) {
			// ファイルのデータ量が0byteの時には画像なしで商品を登録
			// ここのeditメソッドは写真の有無で分けるより
			itemRepository.noPicItemEdit(id, name, condition, category, brand, price, description, saleId);
			result = true;
			return result;
		}
		try {
			// ファイルの型を取得する
			// contentTypeは「ファイル種類/ファイルの型式」の形で出力されるため/で区切り取得する
			String imagePath = imageFile.getContentType();
			StringBuilder sb = new StringBuilder(imagePath);
			int fileTypePunctuation = sb.indexOf("/");
			String fileType = sb.substring(fileTypePunctuation + 1);
			// System.out.println("送信されてきたファイルの形式は" + fileType + "です");

			// ファイル形式が当てはまらない場合画像登録処理はスルーする
			if (!fileType.equals("jpg") && !fileType.equals("png") && !fileType.equals("gif")
					&& !fileType.equals("jpeg")) {
				itemRepository.noPicItemEdit(id, name, condition, category, brand, price, description, saleId);
				result = true;
			} else {

				// System.out.println("送信された画像ファイルは" + fileType + "形式です");

				// ファイルに追加する日付
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");

				String fileName = "pic" + sdf.format(now) + "." + fileType;

				// ファイルパスの作成
				StringBuffer filePath = new StringBuffer(
						"C:\\env\\spring workspace\\Mercari\\src\\main\\webapp\\image\\itemPictures\\" + fileName);

				// 作成したパスをもとにファイルオブジェクトを生成
				File addItemFile = new File(filePath.toString());

				// 送信されてきたファイルをbyte形式に変換
				byte[] bytes = imageFile.getBytes();

				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(addItemFile));
				// 商品追加。追加のに必要な要素であるcategory情報をカテゴリーの名前から取得している
				itemRepository.itemEdit(id, name, condition, category, brand, price, description, fileName, saleId);
				// byteデータをもとにファイルの書き込み
				bos.write(bytes);
				// クローズしてメモリ開放
				bos.close();

				// TODO 画像が無限に増えるので商品追加に成功したときは元の画像ファイルを削除する処理追記したほうがいいかもしれません

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

	private String dateTimeGenerate(String saleDate, String saletime) {
		String dateTime = saleDate + " " + saletime;
		return dateTime;
	}
}