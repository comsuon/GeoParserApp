package com.example.geoapp.test;

import com.example.parser.Rules;
import com.example.parser.parsers.LinkParser;
import com.example.parser.parsers.TextParser;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TextParserTest {
	TextParser mentionParser = new TextParser(Rules.MENTION_RULE.regex, "mentions");

	@Test
	public void getJson_mentionName_correctJson() {
		String input = "@user1";
		String expected = "{\"mentions\": [\n\"@user1\"\n]}";
		String jsonExpected = new JSONObject(expected).toString();

		String result = mentionParser.getJsonString(input);
		String jsonResult = new JSONObject("{" +result + "}").toString();

		assertEquals(jsonExpected, jsonResult);
	}

	@Test
	public void getJson_mentionName2Times_correctJson() {
		String input = "@user1 @user2";
		String expected = "{\"mentions\": [\n\"@user1\",\n\"@user2\"\n]}";
		String jsonExpected = new JSONObject(expected).toString();

		String result = mentionParser.getJsonString(input);
		String jsonResult = new JSONObject("{" +result + "}").toString();

		assertEquals(jsonExpected, jsonResult);
	}

	@Test
	public void getJson_onlyMentionWith3Tags_returnsEmpty() {
		String input = "@@@user";
		String expected = "{\"mentions\": [\n\"@user\"\n]}";
		String jsonExpected = new JSONObject(expected).toString();

		String result = mentionParser.getJsonString(input);
		String jsonResult = new JSONObject("{" +result + "}").toString();

		assertEquals(jsonExpected, jsonResult);
	}

	@Test
	public void getJson_onlyMentionTagNoText_returnsEmpty() {
		String input = "@";
		assertEquals("", mentionParser.getJsonString(input));
	}

	@Test
	public void getJson_mentionTagWithSpaceBetweenText_returnsEmpty() {
		String input = "@ user1";
		assertEquals("", mentionParser.getJsonString(input));
	}

	@Test
	public void getJson_withConflictingParserNoConflict_returnJson() {
		mentionParser.addConflictingParser(new LinkParser());
		String input = "@user1";
		String expected = "{\"mentions\": [\n\"@user1\"\n]}";
		String jsonExpected = new JSONObject(expected).toString();

		String result = mentionParser.getJsonString(input);
		String jsonResult = new JSONObject("{" +result + "}").toString();
		assertEquals(jsonExpected, jsonResult);
	}

	@Test
	public void getJson_withConflictingParserConflict_returnEmpty() {
		mentionParser.addConflictingParser(new LinkParser());
		String input = "Mention @user in this page ; http://page.com";
		String expected = "";

		String result = mentionParser.getJsonString(input);
		assertEquals(expected, result);
	}
}
