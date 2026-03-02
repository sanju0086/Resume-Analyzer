package com.sanju.resumeanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sanju.resumeanalyzer.model.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}