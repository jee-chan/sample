package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.web.itemListCsvDownloadController;

//import com.example.demo.repository.ItemInsertRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MercariApplicationTests {
//	@Autowired
//	private ItemInsertRepository itemRepository;

	@Test
	public void contextLoads() {
		try {
//			itemRepository.categoryExtraction();
//			itemRepository.categoryInsert();
//			itemRepository.itemsTableInsert();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
