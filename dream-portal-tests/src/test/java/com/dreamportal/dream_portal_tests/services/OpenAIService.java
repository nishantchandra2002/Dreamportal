package com.dreamportal.dream_portal_tests.services;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import java.time.Duration;

public class OpenAIService {
    private final OpenAiService service;

    public OpenAIService() {
        // IMPORTANT: Never hardcode API keys. Use environment variables.
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null) {
            throw new IllegalArgumentException("OPENAI_API_KEY environment variable not set.");
        }
        this.service = new OpenAiService(apiKey, Duration.ofSeconds(30));
    }

    public String classifyDream(String dreamName) {
        String prompt = String.format(
                "Classify the following dream title as only 'Good' or 'Bad'. Do not add any other text. Dream: \"%s\". Classification:",
                dreamName
        );

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct") // A fast and cheap model for this task
                .prompt(prompt)
                .maxTokens(5)
                .temperature(0.0) // Be deterministic
                .build();

        try {
            String response = service.createCompletion(completionRequest).getChoices().get(0).getText().trim();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}