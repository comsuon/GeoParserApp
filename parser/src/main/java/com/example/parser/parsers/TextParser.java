package com.example.parser.parsers;

import com.example.parser.model.Node;
import com.example.parser.model.TextNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This parser is used to look up for the text with provided regex and parse it to json
 * <p>
 * <p>
 * Example:
 * {
 * "mentions": [
 * "@user1",
 * ]}
 */
public class TextParser extends Parser {
	public TextParser(String regex, String nodeName) {
		super(regex, nodeName);
	}

	@Override
	protected List<Node> matchText(String text) {
		List<Node> nodes = new ArrayList<>();
		while(this.matcher.find()) {
			String matchedText = matcher.group();
			if(!checkConflict(matchedText)) {
				nodes.add(new TextNode(matchedText));
			}
		}
		return nodes;
	}
}
