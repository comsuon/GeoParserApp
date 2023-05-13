package com.example.geoapp.test;

import com.example.parser.JsonParser;
import com.example.parser.Rules;
import com.example.parser.parsers.LinkParser;
import com.example.parser.parsers.Parser;
import com.example.parser.parsers.TextParser;

import org.json.JSONObject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonParserTest {
	JsonParser SUT = new JsonParser();

	@Test
	public void testAddParser() {
		Parser mentionParser = new TextParser(Rules.MENTION_RULE.regex, "mentions");
		List<Parser> listParser =
			SUT.addParser(mentionParser).getParsers();
		assertEquals(1, listParser.size());
		assertEquals(mentionParser, listParser.get(0));
	}

	@Test
	public void testAddParser_addTwice_onlyOneInstance() {
		Parser mentionParser = new TextParser(Rules.MENTION_RULE.regex, "mentions");
		List<Parser> listParser = SUT.addParser(mentionParser).addParser(mentionParser).getParsers();
		assertEquals(1, listParser.size());
		assertEquals(mentionParser, listParser.get(0));
	}

	@Test
	public void testAddParser_add2Instances_returnsSizeOfTwo() {
		Parser mentionParser = new TextParser(Rules.MENTION_RULE.regex, "mentions");
		Parser linkParser = new LinkParser();
		List<Parser> listParser = SUT.addParser(mentionParser).addParser(linkParser).getParsers();
		assertEquals(2, listParser.size());
		assertEquals(mentionParser, listParser.get(0));
	}

	@Test
	public void testGetJsonFromParsers_onlyMention_returnMentionJson() {
		String input = "This text mention this @user only";
		String expected = "{\"mentions\":[\"@user\"]}";
		String expectedJson = new JSONObject(expected).toString();

		addBothParser();

		String actual = SUT.getJsonStringFromParsers(input);
		String actualJson = new JSONObject(actual).toString();

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetJsonFromParsers_mentionAndUrl_returnMentionAndUrlJson() {
		String input = "This text mention this @user in the url; https://page.com";
		String expected = "{\"mentions\":[\"@user\"]," +
			"\"links\":[{\"url\":\"https://page.com\", \"title\": \"This text mention this @user in the url\"}]}";
		String expectedJson = new JSONObject(expected).toString();

		addBothParser();

		String actual = SUT.getJsonStringFromParsers(input);
		String actualJson = new JSONObject(actual).toString();

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetJsonFromParsers_mentionAndUrlWithConflict_returnOnlyUrlJson() {
		String input = "This text mention this @user in the url; https://page.com";
		String expected = "{\"links\":[{\"url\":\"https://page.com\", \"title\": \"This text mention this @user in the url\"}]}";
		String expectedJson = new JSONObject(expected).toString();

		addLinkParserWithMentionParserConflict();

		String actual = SUT.getJsonStringFromParsers(input);
		String actualJson = new JSONObject(actual).toString();

		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void testGetJsonFromParsers_noMatching_returnEmpty() {
		String input = "test";
		String expected = "";

		addLinkParserWithMentionParserConflict();

		String actual = SUT.getJsonStringFromParsers(input);
		assertEquals(expected, actual);
	}

	private void addBothParser() {
		SUT.addParser(new TextParser(Rules.MENTION_RULE.regex, "mentions"));
		SUT.addParser(new LinkParser());
	}

	private void addLinkParserWithMentionParserConflict() {
		LinkParser linkParser = new LinkParser();
		TextParser mentionParser = new TextParser(Rules.MENTION_RULE.regex, "mentions");
		mentionParser.addConflictingParser(linkParser);

		SUT.addParser(mentionParser);
		SUT.addParser(linkParser);
	}
}