package net.jqsoft.common.util.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.jqsoft.phimp.util.ExcelUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:context.xml")
public class ExcelUtilsTest extends AbstractJUnit4SpringContextTests  {

	public static void main(String[] args) {
		System.out.println(ExcelUtilsTest.class.getResource("/"));
	}
	
	@Test
	public void readFile() {
		
		String filePath = "D:/10.xlsx";
		List<String[]> list = ExcelUtils.readFile(filePath, 2, 1, 17);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i)+"*******************");
		}
	}

}
