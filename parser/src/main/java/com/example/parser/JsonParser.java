package com.example.parser;

import com.example.parser.parsers.Parser;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
	private List<Parser> parsers;

	public List<Parser> getParsers() {
		return parsers;
	}

	/**
	 * Add the parser to the list of parsers
	 * Each parser has its own Regex and matching function to parse the json string
	 *
	 * @param parser
	 * @return
	 */
	public JsonParser addParser(Parser parser) {
		if(parsers == null) {
			parsers = new ArrayList<>();
		}
		for(Parser p : parsers) {
			if(p == parser) {
				return this;
			}
		}
		parsers.add(parser);

		return this;
	}

	/**
	 * Use the collections of parser to parse the json string
	 * from the given input string
	 *
	 * @param text
	 * @return
	 */
	public String getJsonStringFromParsers(String text) {
		if(parsers == null || parsers.isEmpty()) return "";

		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < parsers.size(); i++) {

			Parser p = parsers.get(i);
			String parsedJson = p.getJsonString(text);

			// Add comma if has previous item
			if(!sb.toString().isEmpty() && !(parsedJson == null || parsedJson.isEmpty())) {
				sb.append(",").append("\n");
			}
			sb.append(parsedJson);
		}

		// Add open and closing json braces
		sb.insert(0, "{\n");
		sb.append("}");

		// No parsed items found return empty string
		if(sb.toString().equals("{\n}")) {
			return "";
		}

		String resultString = sb.toString();

		// Formatting json string
		JSONObject json = new JSONObject(resultString);

		// Unescape json for `//` in http link
		return StringEscapeUtils.unescapeJson(json.toString(4));
	}
}
