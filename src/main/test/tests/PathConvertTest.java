package tests;

import org.junit.Test;

import com.greedystar.generator.utils.StringUtil;

public class PathConvertTest {

	@Test
	public void pathConvertTest() {
		String packagePath = "controller";
		System.out.println(StringUtil.package2Path(packagePath));
	}

}
