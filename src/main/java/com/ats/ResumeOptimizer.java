package com.ats;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ResumeOptimizer {
    public String optimizeResume(String resumeText, String jobDescription) {
        List<String> jdKeywords = Arrays.asList(jobDescription.split("\\s+"));
        String optimized = resumeText;

        if (optimized.toLowerCase().contains("skills")) {
            StringBuilder sb = new StringBuilder();
            for (String line : optimized.split("\n")) {
                sb.append(line).append("\n");
                if (line.toLowerCase().contains("skills")) {
                    for (String keyword : jdKeywords) {
                        if (!optimized.toLowerCase().contains(keyword.toLowerCase())) {
                            sb.append(" • ").append(keyword).append("\n");
                        }
                    }
                }
            }
            optimized = sb.toString();
        }
        return optimized;
    }
}
