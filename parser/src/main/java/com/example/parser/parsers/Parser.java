package com.example.parser.parsers;

import com.example.parser.model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Parser {

	protected String nodeName;
	private Pattern pattern;

	protected Matcher matcher;

	List<Node> parsedItems;
	List<Parser> conflictingParsers;

	Parser(String rule, String nodeName) {
		this.pattern = Pattern.compile(rule, Pattern.MULTILINE);
		this.nodeName = nodeName;
	}

	public List<Node> getParsedItems() {
		return this.parsedItems;
	}

	/**
	 * Check if the matched text is captured in the conflicting parsers to skip it
	 *
	 * @param matchedText
	 * @return
	 */
	protected boolean checkConflict(String matchedText) {
		if(conflictingParsers == null || conflictingParsers.isEmpty()) {
			return false;
		}

		for(Parser parser : conflictingParsers) {
			for(Node node : parser.getParsedItems()) {
				if(node.isConflict(matchedText)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Add conflicting parsers to add condition in case the text is found in the conflicting parser,
	 * it will be skipped by this parser
	 *
	 * @param parser
	 * @return
	 */
	public void addConflictingParser(Parser parser) {
		if(parser == null) return;

		if(conflictingParsers == null) {
			conflictingParsers = new ArrayList<>();
		}

		// Check no duplicating parser before add
		for(Parser conflictingParser : conflictingParsers) {
			if(conflictingParser == parser) {
				return;
			}
		}

		conflictingParsers.add(parser);
	}

	/**
	 * This method is used to parse the text and return a list of nodes
	 * Override this method to customise the node parsing
	 *
	 * @param text
	 * @return
	 */
	protected abstract List<Node> matchText(String text);

	/**
	 * Parse the given text to get a list of nodes which matched the provided Rule
	 *
	 * @param text
	 */
	protected void parseText(String text) {
		//Check if there are conflicting parsers and parse beforehand
		if(conflictingParsers != null && !conflictingParsers.isEmpty()) {
			for(Parser parser : conflictingParsers) {
				parser.parseText(text);
			}
		}

		//Get parsed items
		this.matcher = this.pattern.matcher(text);
		parsedItems = matchText(text);
	}

	/**
	 * Build Json string from the given input
	 *
	 * @param text
	 * @return
	 */
	public String getJsonString(String text) {
		//Call parse to get parsed items
		parseText(text);

		//If no item parsed then return empty json for this node
		if(parsedItems == null || parsedItems.isEmpty()) {
			return "";
		}

		//Build json string
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\"").append(nodeName).append("\": ").append("[\n");
		for(int i = 0; i < this.parsedItems.size(); i++) {
			Node node = this.parsedItems.get(i);
			if(i != 0) {
				stringBuilder.append(",\n");
			}
			stringBuilder.append(node.getContent());
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}