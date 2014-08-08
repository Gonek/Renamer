package hu.deadcode.renamer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Config {

	private Element conf;

	public Config() {
		try {
			File fXmlFile = new File("src\\main\\resources\\Config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			conf = doc.getDocumentElement();
			conf.normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String GetInputPath() {
		return conf.getElementsByTagName("input_path")
			.item(0)
			.getTextContent();
	}

	public String GetOutputPath() {
		return conf.getElementsByTagName("output_path")
			.item(0)
			.getTextContent();
	}

	public List<String> GetFileTypes() {
		List<String> filetypes = new ArrayList<String>();
		NodeList fileTypeList = conf.getElementsByTagName("file_type");
		for (int i = 0; i < fileTypeList.getLength(); i++) {
			filetypes.add(fileTypeList.item(i)
				.getTextContent());
		}
		return filetypes;
	}
}
