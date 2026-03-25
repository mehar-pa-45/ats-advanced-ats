package com.ats;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class ResumeTemplateService {
    public byte[] generateResume(String optimizedResume, String templatePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(templatePath);
             XWPFDocument document = new XWPFDocument(fis);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            for (XWPFParagraph p : document.getParagraphs()) {
                String text = p.getText();
                if (text.contains("{{RESUME_CONTENT}}")) {
                    text = text.replace("{{RESUME_CONTENT}}", optimizedResume);
                    p.getRuns().forEach(run -> run.setText("", 0));
                    p.createRun().setText(text);
                }
            }
            document.write(bos);
            return bos.toByteArray();
        }
    }
}
