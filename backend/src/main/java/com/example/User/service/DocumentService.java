package com.example.User.service;

import com.example.User.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentService {

    Document uploadDocument(MultipartFile file, Long employeeId, String uploadedBy, String documentType) throws IOException, IOException;

    List<Document> getDocumentsByEmployee(Long employeeId);

    List<Document> verifyAllDocumentsByEmployee(Long employeeId, String remarks);

    List<Document> rejectAllDocumentsByEmployee(Long employeeId, String remarks);

    void deleteDocument(Long documentId);

    void deleteDocumentsByEmployeeId(Long employeeId);

    List<Document> getDocumentsForManager(Long managerId);

    Document getDocumentById(Long id) throws IOException;
}

