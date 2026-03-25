package com.ats;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ResumeAnalyzer {
    public int scoreResume(String resumeText, String jobDescription) {
        List<String> jdKeywords = Arrays.asList(jobDescription.split("\\s+"));
        int matched = 0;
        for (String keyword : jdKeywords) {
            if (resumeText.toLowerCase().contains(keyword.toLowerCase())) {
                matched++;
            }
        }
        double similarity = (double) matched / jdKeywords.size();
        return (int)(similarity * 100);
    }
}
