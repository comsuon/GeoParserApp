package com.example.geoapp.test;

import com.example.parser.Rules;
import com.example.parser.parsers.LinkParser;
import com.example.parser.parsers.TextParser;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinkParserTest {
	LinkParser linkParserTest = new LinkParser();

	@Test
	public void getJson_correctUrlTagsHttp_correctJson() {
		String input = "Page title; https://page.com";
		String expected = "{\"links\": [" +
			"{" +
			"\"url\": \"https://page.com\"," +
			"\"title\": \"Page title\"" +
			"}]}";

		JSONObject jsonObjectExpected = new JSONObject(expected);

		String result = linkParserTest.getJsonString(input);

		//Child node does not have the open and close bracket
		String resultJson = "{" + result + "}";
		JSONObject jsonObjectResult = new JSONObject(resultJson);

		assertEquals(jsonObjectExpected.toString(), jsonObjectResult.toString());
	}

	@Test
	public void getJson_notAnURL_noJson() {
		String input = "Page title";
		String expected = "";
		String result = linkParserTest.getJsonString(input);

		assertEquals(expected, result);
	}

	@Test
	public void getJson_correctHttpUrl_noJson() {
		String input = "Page title; http://page.com";
		String expected = "{\"links\": [" +
			"{" +
			"\"url\": \"http://page.com\"," +
			"\"title\": \"Page title\"" +
			"}]}";

		JSONObject jsonObjectExpected = new JSONObject(expected);

		String result = linkParserTest.getJsonString(input);

		//Child node does not have the open and close bracket
		String resultJson = "{" + result + "}";
		JSONObject jsonObjectResult = new JSONObject(resultJson);

		assertEquals(jsonObjectExpected.toString(), jsonObjectResult.toString());
	}

	@Test
	public void getJson_withConflictingParserButNotConflict_correctJson() {
		String input = "Page title; https://page.com";
		String expected = "{\"links\": [" +
			"{" +
			"\"url\": \"https://page.com\"," +
			"\"title\": \"Page title\"" +
			"}]}";
		JSONObject jsonObjectExpected = new JSONObject(expected);

		linkParserTest.addConflictingParser(new TextParser(Rules.MENTION_RULE.regex, "mentions"));
		String result = linkParserTest.getJsonString(input);

		//Child node does not have the open and close bracket
		String resultJson = "{" + result + "}";
		JSONObject jsonObjectResult = new JSONObject(resultJson);

		assertEquals(jsonObjectExpected.toString(), jsonObjectResult.toString());
	}

	@Test
	public void getJson_withConflictingParserAndConflict_emptyJson() {
		String input = "@user; https://page.com";
		String expected = "";

		linkParserTest.addConflictingParser(new TextParser(Rules.MENTION_RULE.regex, "mentions"));
		String result = linkParserTest.getJsonString(input);

		assertEquals(expected, result);
	}

}
