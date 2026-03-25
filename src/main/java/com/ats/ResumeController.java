package com.ats;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ResumeController {

    @Autowired private ResumeAnalyzer analyzer;
    @Autowired private ResumeOptimizer optimizer;
    @Autowired private ResumeTemplateService templateService;

    @PostMapping("/score")
    public Map<String, Object> scoreResume(@RequestBody ResumeRequest request) {
        int score = analyzer.scoreResume(request.getResumeText(), request.getJobDescription());
        Map<String, Object> response = new HashMap<>();
        response.put("score", score);
        return response;
    }

    @PostMapping("/optimize")
    public Map<String, Object> optimizeResume(@RequestBody ResumeRequest request) {
        String optimized = optimizer.optimizeResume(request.getResumeText(), request.getJobDescription());
        int beforeScore = analyzer.scoreResume(request.getResumeText(), request.getJobDescription());
        int afterScore = analyzer.scoreResume(optimized, request.getJobDescription());

        Map<String, Object> response = new HashMap<>();
        response.put("beforeScore", beforeScore);
        response.put("afterScore", afterScore);
        response.put("optimizedResume", optimized);
        return response;
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadResume(@RequestBody ResumeRequest request,
                                                 @RequestParam String template) throws Exception {
        String optimized = optimizer.optimizeResume(request.getResumeText(), request.getJobDescription());
        String templatePath = "templates/" + template + ".docx";
        byte[] resumeFile = templateService.generateResume(optimized, templatePath);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=optimized_resume.docx")
                .body(resumeFile);
    }
}
