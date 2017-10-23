package com.wholesomechat.prototype.prototype;


import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import static spark.Spark.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * CS 480 - Wholesome Chat
 * Prototype v1
 */
public class App 
{
    public static void main( String[] args ){
    	//Read credentials from file
    	String watsonUser = "";
		try {
			watsonUser = new String(Files.readAllBytes(Paths.get(
					"/home/chris/Documents/watson/login/user")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	String watsonPass = "";
		try {
			watsonPass = new String(Files.readAllBytes(Paths.get(
					"/home/chris/Documents/watson/login/pass")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
		
		final String VERSION_DATE = "2016-05-19";
    	
    	//initilize Watson Tone Analyzer with version and credentials
    	ToneAnalyzer service = new ToneAnalyzer(VERSION_DATE);
    	service.setUsernameAndPassword(watsonUser, watsonPass);
    	service.setEndPoint("https://gateway.watsonplatform.net/tone-analyzer/api");
    	
    	//Testing codeblock
    	/*
    	String text1 = "Good Morning";
    	String text2 = "You suck";
    	
    	ToneOptions toneOptions = new ToneOptions.Builder().html(text1).build();
    	ToneAnalysis tone = service.tone(toneOptions).execute();
    	System.out.println(tone.getDocumentTone());
    	*/
    	
    	//Start jetty server and listen to API endpoint
    	post("/api/getTone", (request, response) -> {
        	String userText = request.body();
        	ToneOptions toneOptions = new ToneOptions.Builder().html(userText).build();
        	ToneAnalysis tone = service.tone(toneOptions).execute();
        	return tone.getDocumentTone();
        });
    	
    } 
}
