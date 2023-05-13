package com.example.parser.parsers;

import com.example.parser.Rules;
import com.example.parser.model.LinkModel;
import com.example.parser.model.Node;

import java.util.ArrayList;
import java.util.List;

public class LinkParser extends Parser {

	public LinkParser() {
		super(Rules.HTTP_URL_RULE.regex, "links");
	}

	public LinkParser(String regex, String nodeName) {
		super(regex, nodeName);
	}

	@Override
	protected List<Node> matchText(String text) {
		List<Node> nodes = new ArrayList<>();
		while(matcher.find()) {
			String matchedText = matcher.group();
			String[] urlTag = matchedText.split("; ");
			String url = urlTag[1];
			String title = urlTag[0];
			if(!checkConflict(url) && !checkConflict(title)) {
				nodes.add(new LinkModel(urlTag[0], urlTag[1]));
			}
		}
		return nodes;
	}
}
