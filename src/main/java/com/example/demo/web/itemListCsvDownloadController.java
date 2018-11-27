package com.example.demo.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;

/**
 * 商品情報をCSV出力させるためのクラスです.
 * 
 * @author rks
 *
 */
@Controller
@RequestMapping("/itemListdownload")
public class itemListCsvDownloadController {

	@Autowired
	private HttpServletResponse resp;

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * csvダウンロードページを出力させるためのメソッドです.
	 * 
	 * @return ページ
	 */
	@RequestMapping("/")
	public String view() {
		return "jsp/itemListDownload";
	}

	@RequestMapping("/csvCreate")
	@ResponseBody
	public String[] csvDownload() {
		System.out.println("ファイル生成開始");
		// 生成するファイル名を作成
		SimpleDateFormat sdfmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String downloadDate = sdfmt.format(new Date());
		String csvFileName = "C:/env/app/postgresql/itemsCsv/itemList" + downloadDate + ".csv";
		itemRepository.createItemListCsv(csvFileName);

		// ファイルパスの一部を保持した配列を返す
		String[] fileNum = { downloadDate };

		System.out.println("ファイル生成処理終了");
		System.out.println(fileNum[0]);

		// TODO:
		// 生成ページでのAjaxでこのメソッドを呼び出した際に生成まではできるがreturnがうまくいかずにAjaxが停止してしまうバグあり。現状原因不明です
		return fileNum;
	}

	/**
	 * 指定したパスのファイルをクライアントでダウンロードさせるためのメソッド.
	 * 
	 * @param path
	 *            ファイルの格納箇所（フルパスでのみ動作します）
	 */
	@RequestMapping("/csvDownload")
	@ResponseBody
	public void fileDownloader(String path) {
		System.out.println("ファイルのダウンロードを開始します");

		// ダウンロード対象ファイルの読み込み用オブジェクト
		FileInputStream fis = null;
		InputStreamReader isr = null;

		// ダウンロードファイルの書き出し用オブジェクト
		OutputStream os = null;
		OutputStreamWriter osw = null;

		String fullFilePath = "C:/env/app/postgresql/itemsCsv/itemList" + path + ".csv";

		// ダウンロード対象ファイルのファイルオブジェクトを生成
		File file = new File(fullFilePath);
		try {

			if (!file.exists() || file.isFile()) {
				// ここにファイルが存在しない場合の処理を記述
				// ポップアップ吐かせるか別のところに飛ばす？（再利用できなくなるが）
				// エラー画面へのハンドリングが適切な気がする505エラーとか？
				System.err.println("検索したファイルが存在しません");
			}

			// レスポンスオブジェクトのヘッダ情報を設定
			resp.setContentType("application/octet-stream");
			// ファイル名のセットはこの方法だとうまくいきませんでした。filenameの部分にバックスラッシュで囲うとその中が
			// res.setHeader("Content-Disposition"*/, "attachment; filename=" + new
			// String("商品リスト".getBytes("UTF-8"), "UTF-8"));
			resp.setHeader("Content-Disposition", "attachment; filename=\"itemList.csv\"");

			// ダウンロード対象ファイルの読み込み用オブジェクトを生成
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");

			// ダウンロード対象ファイルの書き出し用オブジェクトを生成
			os = resp.getOutputStream();
			osw = new OutputStreamWriter(os, "UTF-8");

			// IOストリームの機能でダウンロードを行う
			int i;
			while ((i = isr.read()) != -1) {
				osw.write(i);
			}

			System.out.println("ファイルのダウンロードが完了しました");
		} catch (FileNotFoundException e) {
			// TODO: ファイルが見つからない場合
			System.err.println("例外が発生しました（FileNotFoundException） ======> " + e);
		} catch (UnsupportedEncodingException e) {
			// TODO: サポートしていない文字形式だった場合（？）
			System.err.println("例外が発生しました（UnsupportedEncodingException） ======> " + e);
		} catch (IOException e) {
			// TODO: い つ も の
			System.err.println("何らかの例外が発生しました（IOException） ======> " + e);
		} finally {
			try {
				// 各オブジェクトを忘れずにクローズ
				if (osw != null) {
					System.out.println("osw is close");
					osw.close();
				}
				if (os != null) {
					System.out.println("os is close");
					os.close();
				}
				if (isr != null) {
					System.out.println("isr is close");
					isr.close();
				}
				if (fis != null) {
					System.out.println("fis is close");
					fis.close();
				}
				// クローズ処理が終了したらファイルを削除
				System.out.println("削除するファイルのパス" + file.getAbsolutePath());
				if (file.delete()) {
					System.out.println("削除に成功しました");
				} else {
					System.err.println("削除に失敗しました");
				}
			} catch (IOException e) {
				// TODO: オブジェクトのクローズ処理エラー
				System.err.println("オブジェクトのクローズ処理の際に例外が発生しました（IOException） ======> " + e);
			}
		}
	}
//=============================================================================================================
	/**
	 * csvのダウンロードを行うメソッド.
	 * 
	 * @param response
	 *            HttpServletResponseの実装
	 */
	// TODO 処理が重すぎる＆ファイルを生成してからクライアントに渡していない等の理由からこのメソッドを用いての実装は一旦凍結します
	@RequestMapping("/download")
	public void itemListCsvDownload(HttpServletResponse response) {
		System.out.println("ダウンロードメソッドが呼ばれました");
		try (PrintWriter pw = response.getWriter()) {
			// ファイルの出力形式と文字コードを指定する
			//TODO:なぜかS-JIS指定してあったので修正
			response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=UTF-8");
			// ファイル名にダウンロードした時間を挿入する
			SimpleDateFormat sdfmt = new SimpleDateFormat("yyyyMMddHHmmss");
			String downloadDate = sdfmt.format(new Date());
			//TODO:なぜかS-JIS指定してあったので修正
			String encodedFilename = URLEncoder.encode("全商品リスト" + downloadDate + ".csv", "UTF-8");
			// ファイル名セット
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFilename + "\"");
			// response.setHeader("Content-Disposition", "attachment; filename*=\"SJIS''" +
			// encodedFilename + "\"");

			// 総件数の取得
			Integer itemListQuantity = itemRepository.getItemQuantity();

			Item item = new Item();
			// 総件数分forを回す
			for (int i = 0; i < itemListQuantity; i++) {

				if (i % 100000 == 0) {
					System.out.println("カウント ===========> " + i);
				}
				// 現在回した数のアイテムを取得する
				item = itemRepository.getItem(i);
				// 空白ないしnullの文字列の置き換え
				item.setName(csvNullChange(item.getName()));
				item.setBrand(csvNullChange(item.getBrand()));
				item.setDescription(csvNullChange(item.getDescription()));
				item.setBigCategory(csvNullChange(item.getBigCategory()));
				item.setMiddleCategory(csvNullChange(item.getMiddleCategory()));
				item.setSmallCategory(csvNullChange(item.getSmallCategory()));
				
				// 取得したオブジェクトからデータを取り出して整形
				Integer id = item.getId();
				String name = item.getName();
				Integer condition = item.getCondition();
				Integer category = item.getCategory();
				String brand = item.getBrand();
				double price = item.getPrice();
				String description = item.getDescription();
				String bigCategory = item.getBigCategory();
				String middleCategory = item.getMiddleCategory();
				String smallCategory = item.getSmallCategory();

				// カラム内にカンマが入っている可能性のある要素のチェックと置き換え
				/**
				name = csvEscape(name);
				brand = csvEscape(brand);
				description = csvEscape(description);
				 */

				// カンマ区切りで結合
				String outputString = id + "," + name + "," + condition + "," + category + "," + brand + "," + price
						+ "," + description + "," + bigCategory + "," + middleCategory + "," + smallCategory + "\r\n";

				// ファイルに書き込み
				pw.print(outputString);
			}
		} catch (Exception e) {
			System.err.println("例外発生" + e);
			e.printStackTrace();
		}
	}

	/**
	 * csvに整形するためにカンマをエスケープするメソッド.
	 * 
	 * @param string
	 *            判定する文字列
	 * @return 整形した文字列
	 */
	public String csvEscape(String string) {
		if (string.matches(".*,.*")) {
			string = string.replace(",", "");
		}
		return string;
	}

	/**
	 * 文字列のnullか空文字を置き換えるメソッド.
	 * 
	 * @param string
	 *            文字列
	 * @return 変換した文字列
	 */
	public String csvNullChange(String string) {
		if (string == null || string.isEmpty()) {
			string = "";
		}else {
			//nullじゃなければカンマが含まれているかチェック
			string = csvEscape(string);
		}
		return string;
	}
}
