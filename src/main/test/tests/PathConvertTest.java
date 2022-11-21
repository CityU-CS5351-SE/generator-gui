package tests;

import org.junit.Test;

import com.greedystar.generator.utils.StringUtil;

public class PathConvertTest {

	@Test
	public void pathConvertTest() {
		String packagePath = "controller";
		System.out.println(StringUtil.package2Path(packagePath));
	}

	
	
	@Test
	public void tableName2ClassName() {
		String tableName = "test_table";
		String className = "";
		String[] list = tableName.split("_");
		for (int i=0;i<list.length;i++) {
			className = className + list[i].substring(0,1).toUpperCase() + list[i].substring(1);
		}
		System.out.println(className);
	}
}
