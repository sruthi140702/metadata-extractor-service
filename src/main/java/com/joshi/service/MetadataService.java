package com.joshi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class MetadataService {

    public void processResume(String content) {
        log.info("Extracting resume metadata...");

        String experience = extractSection(content, "experience");
        String projects = extractSection(content, "projects");
        String education = extractSection(content, "education");
        String skills = extractSection(content, "skills");

        log.info("Resume Metadata Extracted:");
        log.info("Experience: {}", experience);
        log.info("Projects: {}", projects);
        log.info("Education: {}", education);
        log.info("Skills: {}", skills);
    }

    public void processGeneric(String fileName, String classification) {
        log.info("Generic document detected.");
        log.info("File Name: {}", fileName);
        log.info("File Type: {}", classification);
    }

    private String extractSection(String content, String keyword) {
        Pattern pattern = Pattern.compile("(?i)" + keyword + "[:\\n](.*?)(\\n\\n|$)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "Not Found";
    }
}

