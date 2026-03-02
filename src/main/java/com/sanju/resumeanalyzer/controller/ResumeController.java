package com.sanju.resumeanalyzer.controller;

import com.sanju.resumeanalyzer.dto.ResumeResponse;
import com.sanju.resumeanalyzer.service.ResumeService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping(value = "/analyze", consumes = "multipart/form-data")
    public ResumeResponse analyzeResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobDescription") String jobDescription,
            @RequestParam("candidateName") String candidateName,
            @RequestParam("email") String email) throws Exception {

        String resumeText = resumeService.extractTextFromPdf(file);

        double match = resumeService.analyzeAndSave(
                candidateName,
                email,
                resumeText,
                jobDescription
        );

        return new ResumeResponse(candidateName, match);
    }
}