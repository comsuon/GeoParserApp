package com.example.geoapp;

import android.os.Bundle;

import com.example.geoapp.databinding.ActivityMainBinding;
import com.example.parser.JsonParser;
import com.example.parser.Rules;
import com.example.parser.parsers.LinkParser;
import com.example.parser.parsers.TextParser;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	ActivityMainBinding mainBinding;

	JsonParser jsonParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(mainBinding.getRoot());
		initParser();

		mainBinding.btnSend.setOnClickListener(view -> {
			String textInput = mainBinding.editText.getText().toString();
			String jsonString = jsonParser.getJsonStringFromParsers(textInput);

			mainBinding.tvMain.setText(jsonString);
		});

	}

	private void initParser() {
		jsonParser = new JsonParser();

		// Add http links parser
		LinkParser linkParser = new LinkParser();
		jsonParser.addParser(linkParser);

		// Add mentions parser
		TextParser mentionParser = new TextParser(Rules.MENTION_RULE.regex, "mentions");

		// Add linkParser as the conflicting parser
		// to avoid capture mentions tag which are found in links
		mentionParser.addConflictingParser(linkParser);
		jsonParser.addParser(mentionParser);
	}

}