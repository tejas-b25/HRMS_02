package com.example.User.repository;

import com.example.User.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByEmployee_EmployeeId(Long employeeId);
    List<Document> findByStatus(String uploadedBy);
    Document findByDocumentId(Long documentId);
    List<Document> findByEmployeeIdInAndDocumentTypeIn(List<Long> employeeIds, List<String> documentTypes);
    void deleteByEmployeeEmployeeId(Long employeeId);



}

