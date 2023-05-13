package com.example.parser.model;

public class TextNode extends Node {
	private String text;

	public TextNode(String text) {
		this.text = text;
	}

	@Override
	public String getContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"").append(text).append("\"");
		return sb.toString();
	}

	@Override
	public boolean isConflict(String text) {
		return this.text.contains(text);
	}
}
