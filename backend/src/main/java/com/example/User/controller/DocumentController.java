package com.example.User.controller;


import com.example.User.entity.Document;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.service.DocumentService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("uploadedBy") String uploadedBy,
            @RequestParam("documentType") String documentType)

            throws Exception {
        Document doc = documentService.uploadDocument(file, employeeId, uploadedBy, documentType);
        return ResponseEntity.ok(doc);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Document>> getDocumentsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(documentService.getDocumentsByEmployee(employeeId));
    }

    @PutMapping("/{employeeId}/verify")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<List<Document>> verifyAllDocumentsByEmployee(
            @PathVariable Long employeeId,
            @RequestParam(required = false) String remarks) {
        List<Document> updatedDocs = documentService.verifyAllDocumentsByEmployee(employeeId, remarks);
        return ResponseEntity.ok(updatedDocs);
    }

    @PutMapping("/{employeeId}/reject")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<List<Document>> rejectDocument(
            @PathVariable Long employeeId,
            @RequestParam(required = true) String remarks) {
        List<Document> updatedDocs = documentService.rejectAllDocumentsByEmployee(employeeId, remarks);
        return ResponseEntity.ok(updatedDocs);
    }

    //  Delete single document
    @DeleteMapping("/{documentId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }

    //  Delete all by employee
    @DeleteMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public ResponseEntity<Void> deleteDocumentsByEmployee(@PathVariable Long employeeId) {
        documentService.deleteDocumentsByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/manager/{managerId}/employees")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<Document>> getDocumentsForManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(documentService.getDocumentsForManager(managerId));
    }


    @GetMapping("/{id}/download")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or hasRole('MANAGER') or @documentSecurity.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) throws IOException, IOException {
        Document doc = documentService.getDocumentById(id);

        Path path = Paths.get(doc.getFilePath());
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists()) {
            throw new ResourceNotFoundException("File not found");
        }

        String safeFileName = URLEncoder.encode(doc.getFileName(), StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + safeFileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    @GetMapping("/{id}/preview")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or @documentSecurity.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<Resource> previewDocument(@PathVariable Long id) throws IOException {
        Document doc = documentService.getDocumentById(id);
        String fileName = doc.getFileName().toLowerCase();

        // Allow only safe types
        if (!(fileName.endsWith(".pdf") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png"))) {
            throw new SecurityException("Unsupported file type for preview");
        }

        Path path = Paths.get(doc.getFilePath());
        Resource resource = new UrlResource(path.toUri());

        MediaType contentType = fileName.endsWith(".pdf") ? MediaType.APPLICATION_PDF : MediaType.IMAGE_JPEG;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(contentType)
                .body(resource);
    }

}

