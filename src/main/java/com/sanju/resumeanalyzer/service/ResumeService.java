package com.sanju.resumeanalyzer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sanju.resumeanalyzer.model.Resume;
import com.sanju.resumeanalyzer.repository.ResumeRepository;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.InputStream;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    // 🔥 PDF Text Extract
    public String extractTextFromPdf(MultipartFile file) throws Exception {

        InputStream inputStream = file.getInputStream();
        PDDocument document = PDDocument.load(inputStream);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();

        return text;
    }

    // 🔥 Analyze + Save
    public double analyzeAndSave(String candidateName,
                                 String email,
                                 String resumeText,
                                 String jobDescription) {

        double matchPercentage = calculateMatch(resumeText, jobDescription);

        Resume resume = new Resume();
        resume.setCandidateName(candidateName);
        resume.setEmail(email);
        resume.setExtractedText(resumeText);
        resume.setMatchPercentage(matchPercentage);

        resumeRepository.save(resume);

        return matchPercentage;
    }

    // 🔥 Matching Logic
    public double calculateMatch(String resumeText, String jobDescription) {

        String[] resumeWords = resumeText.toLowerCase().split("\\W+");
        String[] jobWords = jobDescription.toLowerCase().split("\\W+");

        int matchCount = 0;

        for (String jobWord : jobWords) {
            for (String resumeWord : resumeWords) {
                if (jobWord.equals(resumeWord)) {
                    matchCount++;
                    break;
                }
            }
        }

        if (jobWords.length == 0) return 0;

        return ((double) matchCount / jobWords.length) * 100;
    }
}