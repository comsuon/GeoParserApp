package com.example.parser.model;

import com.example.parser.Utils;

public class LinkModel extends Node {
	private String url;
	private String title;

	public LinkModel(String title, String url) {
		this.url = url;
		this.title = title;
	}

	public String getContent() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Utils.getChildOpenBracket());
		stringBuilder.append("\"url\"").append(": ").append("\"").append(url).append("\"").append(",");
		stringBuilder.append("\"title\"").append(": ").append("\"").append(title).append("\"");
		stringBuilder.append(Utils.getChildCloseBracket());
		return stringBuilder.toString();
	}

	@Override
	public boolean isConflict(String text) {
		return title.contains(text) || url.contains(text);
	}
}
