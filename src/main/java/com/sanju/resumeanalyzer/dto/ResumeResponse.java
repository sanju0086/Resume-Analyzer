package com.sanju.resumeanalyzer.dto;

public class ResumeResponse {

    private String candidateName;
    private double matchPercentage;

    public ResumeResponse(String candidateName, double matchPercentage) {
        this.candidateName = candidateName;
        this.matchPercentage = matchPercentage;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }
}