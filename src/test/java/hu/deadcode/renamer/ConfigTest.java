package hu.deadcode.renamer;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigTest {

	private Config config = new Config();

	@Test
	public void testGetInput() {
		Assert.assertEquals(config.GetInputPath(), "D:\\képek\\Input\\");
	}

	@Test
	public void testGetOutput() {
		Assert.assertEquals(config.GetOutputPath(), "D:\\képek\\");
	}

	@Test
	public void testGetFileTypes() {
		List<String> ExpFileTypes = new ArrayList<String>();
		ExpFileTypes.add("JPG");
		ExpFileTypes.add("JPEG");
		ExpFileTypes.add("MOV");
		ExpFileTypes.add("MP4");
		Assert.assertEquals(config.GetFileTypes(), ExpFileTypes);
	}
}
