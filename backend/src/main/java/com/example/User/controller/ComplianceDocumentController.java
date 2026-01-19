package com.example.User.controller;

import com.example.User.dto.ComplianceDocumentRequestDto;
import com.example.User.dto.ComplianceDocumentResponseDto;
import com.example.User.entity.ComplianceDocument;
import com.example.User.service.ComplianceDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/documents")
public class ComplianceDocumentController {  //for task 24 -oct hamad
    @Autowired
    private ComplianceDocumentService service;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<ComplianceDocumentResponseDto> uploadDocument(@ModelAttribute ComplianceDocumentRequestDto dto) {
        return ResponseEntity.ok(service.uploadDocument(dto));
    }

@GetMapping("/get/{documentId}")
@PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
public ResponseEntity<Resource> getDocument(@PathVariable Long documentId) {
    // The service method returns a wrapper object with both file and metadata
    ComplianceDocument document = service.getDocumentEntity(documentId); // get entity
    Resource file = service.downloadDocument(documentId); // get Resource

    return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=\"" + document.getFileName() + "\"")
            .header("Content-Type", document.getFileType())
            .body(file);
}

    @GetMapping("/get/employee/{employeeId}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('HR') or hasRole('EMPLOYEE')")
    public ResponseEntity<Resource> downloadEmployeeDocument(@PathVariable Long employeeId) {
        ComplianceDocument document = service.getLatestEmployeeDocument(employeeId); // get latest or single doc
        Resource file = service.downloadDocument(document.getDocumentId());

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + document.getFileName() + "\"")
                .header("Content-Type", document.getFileType())
                .body(file);
    }
    @PutMapping("/{documentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<ComplianceDocumentResponseDto> updateDocument(
            @PathVariable Long documentId,
            @ModelAttribute ComplianceDocumentRequestDto dto) {
        return ResponseEntity.ok(service.updateDocument(documentId, dto));
    }
    @DeleteMapping("/{documentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        service.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/verify/{documentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<ComplianceDocumentResponseDto> verifyDocument(
            @PathVariable Long documentId,
            @RequestParam String verifiedBy) {
        return ResponseEntity.ok(service.verifyDocument(documentId, verifiedBy));
    }
    @GetMapping("/expired")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<List<ComplianceDocumentResponseDto>> getExpiredDocuments() {
        return ResponseEntity.ok(service.getExpiredDocuments());
    }

}
