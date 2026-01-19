package com.example.User.service;

import com.example.User.dto.ComplianceDocumentRequestDto;
import com.example.User.dto.ComplianceDocumentResponseDto;
import com.example.User.entity.ComplianceDocument;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ComplianceDocumentService {
    ComplianceDocumentResponseDto uploadDocument(ComplianceDocumentRequestDto requestDto);
    //UrlResource getDocument(Long documentId);

    Resource downloadDocument(Long documentId);
    //ComplianceDocument getDocumentById(Long documentId);
   // List<ComplianceDocumentResponseDto> getEmployeeDocuments(Long employeeId);
    ComplianceDocument getLatestEmployeeDocument(Long employeeId);
    ComplianceDocumentResponseDto updateDocument(Long documentId, ComplianceDocumentRequestDto dto);
    void deleteDocument(Long documentId);
    ComplianceDocumentResponseDto verifyDocument(Long documentId, String verifiedBy);
    List<ComplianceDocumentResponseDto> getExpiredDocuments();
    ComplianceDocument getDocumentEntity(Long documentId);
}
