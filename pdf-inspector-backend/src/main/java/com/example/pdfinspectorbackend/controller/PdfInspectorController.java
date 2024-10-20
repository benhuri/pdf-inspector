package com.example.pdfinspectorbackend.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/api")
public class PdfInspectorController {
    private static final Logger logger = LogManager.getLogger(PdfInspectorController.class);

    @PostMapping("/upload")
    public ResponseEntity<?> inspectPdf(@RequestBody Map<String, String> request) {
        String base64FileContent = request.get("fileContent");

        if (base64FileContent == null || base64FileContent.isEmpty()) {
            logger.error("HTTP Error: " + HttpStatus.BAD_REQUEST.name() + " - File content is missing or invalid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File content is missing or invalid");
        }

        try {
            byte[] pdfBytes = Base64.getDecoder().decode(base64FileContent);
            String extractedText = extractTextFromPDF(pdfBytes);
            String inspectionResult = inspectText(extractedText);
            return ResponseEntity.ok(inspectionResult);
        } catch (IOException e) {
            logger.error("Error processing the PDF file: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file");
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }

    private String extractTextFromPDF(byte[] pdfBytes) throws IOException {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String inspectText(String text) {
        String apiUrl = "https://stg-ps.prompt.security/api/protect";
        String sanitizedText = text.replace("\n", " ").replace("\r", " ");

        HttpHeaders headers = new HttpHeaders();
        headers.set("APP-ID", "13508d17-f4d5-4bd5-a0f4-95540792f6a4");
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"prompt\": \"" + sanitizedText + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            logger.info("Sending request to Prompt Security API...");
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

            String responseBody = response.getBody();
            JSONObject jsonResponse = new JSONObject(responseBody);

            JSONObject result = jsonResponse.getJSONObject("result");
            JSONObject prompt = result.getJSONObject("prompt");
            boolean passed = prompt.getBoolean("passed");

            if (passed) {
                logger.info("File inspection passed successfully!");
            } else {
                logger.warn("File inspection didn't pass");
                String violatingFindings = prompt.optString("violating_findings", "No violating findings");
                if (!violatingFindings.isEmpty()) {
                    logger.warn("Violating Findings: " + violatingFindings);
                }
            }
            return responseBody;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return "HTTP Error: " + e.getMessage();
        } catch (Exception e) {
            logger.error("Error while calling Prompt Security API: " + e.getMessage(), e);
            return "Error processing the API request";
        }
    }
}