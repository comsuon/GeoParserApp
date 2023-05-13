package com.example.parser;

public enum Rules {
	MENTION_RULE("@[a-zA-Z0-9_.]+?(?![a-zA-Z0-9_.])"),
	HTTP_URL_RULE("[a-zA-Z0-9_. $-_@.&+]+?; http[s]?:\\/\\/(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\\(\\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+");

	public final String regex;

	Rules(String regex) {
		this.regex = regex;
	}
}
