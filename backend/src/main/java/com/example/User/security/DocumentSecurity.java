package com.example.User.security;

import com.example.User.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("documentSecurity")
public class DocumentSecurity {
    @Autowired
    private DocumentRepository documentRepository;

    public boolean isOwner(Long documentId, Long userId) {
        return documentRepository.findById(documentId)
                .map(doc -> doc.getEmployee().getEmployeeId().equals(userId))
                .orElse(false);
    }
}
