package com.example.parser.model;

public abstract class Node {
	public abstract String getContent();
	public abstract boolean isConflict(String text);
}