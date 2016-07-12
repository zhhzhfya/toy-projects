package com.toy.dev.utils;

import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class MetaFileUtils {

	public static JsonObject readMeteData(String str) throws DocumentException {
		Document document = DocumentHelper.parseText(str);
		Element root = document.getRootElement();
		Element metaElement = (Element) root.elements().get(0);
		JsonObject meta = new JsonObject().put(metaElement.getName(), readMetas(metaElement));
		//System.out.println("------------>" + meta);
		return meta;
	}

	private static JsonObject readMetas(Element metaElement) {
		List<JsonObject> items = new ArrayList<>();
		JsonObject meta = new JsonObject();
		List<Element> elements = metaElement.elements();
		for (Element ele : elements) {
			if (ele.elements().size() == 0) {
				JsonObject item = new JsonObject();
				List<Attribute> attributes = ele.attributes();
				for (Attribute attr : attributes) {
					item.put(attr.getName(), attr.getValue());
				}
				item.put("ITEM_CODE", ele.getName());
				items.add(item);
			} else {
				String metaNameX = ele.getName();
				JsonObject metaX = readMetas(ele);
				meta.put(metaNameX, metaX);
			}
		}
		meta.put("ITEMS", items);
		return meta;
	}

}
