package com.example.User.serviceimpl;

import com.example.User.dto.ComplianceDocumentRequestDto;
import com.example.User.dto.ComplianceDocumentResponseDto;
import com.example.User.entity.ComplianceDocument;
import com.example.User.repository.ComplianceDocumentRepository;
import com.example.User.service.ComplianceDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

// for task 24-oct hamad
@Service
public class ComplianceDocumentServiceImpl implements ComplianceDocumentService {

    @Autowired
    private ComplianceDocumentRepository repository;

    private final String uploadDir = "c:uploads/compliance_docs/";

@Override
public ComplianceDocumentResponseDto uploadDocument(ComplianceDocumentRequestDto requestDto) {
    MultipartFile file = requestDto.getFile();

    if (file == null || file.isEmpty()) {
        throw new IllegalArgumentException("File is empty");
    }

    try {
        // Define a folder to save files, e.g., "uploads/"
        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir)); // create folder if not exists

        // Generate a unique file name to avoid overwriting
        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Full path where file will be saved
        Path filePath = Paths.get(uploadDir, uniqueFileName);

        // Save file to local disk
        Files.write(filePath, file.getBytes());

        // Build entity
        ComplianceDocument document = ComplianceDocument.builder()
                .complianceId(requestDto.getComplianceId())
                .employeeId(requestDto.getEmployeeId())
                .complianceMappingId(requestDto.getComplianceMappingId())
                .fileName(file.getOriginalFilename()) // original file name
                .fileType(file.getContentType())
                .filePath(filePath.toString()) // path on local machine
                .fileSizeBytes(file.getSize())
                .uploadedBy(requestDto.getUploadedBy())
                .expiryDate(requestDto.getExpiryDate())
                .isVerified(false)
                .createdBy(requestDto.getUploadedBy())
                .status(ComplianceDocument.DocumentStatus.PendingVerification)
                .build();

        // Save to DB
        ComplianceDocument saved = repository.save(document);

        return mapToResponse(saved);

    } catch (Exception e) {
        throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
    }
}

@Override
public Resource downloadDocument(Long documentId) {
    ComplianceDocument document = repository.findById(documentId)
            .orElseThrow(() -> new NoSuchElementException("Document not found"));

    try {
        Path path = Paths.get(document.getFilePath()); // filePath stored in DB
        if (!Files.exists(path)) {
            throw new NoSuchElementException("File not found on disk");
        }

        return new UrlResource(path.toUri());

    } catch (MalformedURLException e) {
        throw new RuntimeException("Error reading file: " + e.getMessage(), e);
    }
}

    @Override
    public ComplianceDocument getLatestEmployeeDocument(Long employeeId) {
        List<ComplianceDocument> documents = repository.findByEmployeeId(employeeId);
        if (documents.isEmpty()) {
            throw new NoSuchElementException("No document found for this employee");
        }
        return documents.stream()
                .sorted((a, b) -> b.getUploadedAt().compareTo(a.getUploadedAt()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No document found"));
    }

    @Override
    public ComplianceDocumentResponseDto updateDocument(Long documentId, ComplianceDocumentRequestDto dto) {
        ComplianceDocument document = repository.findById(documentId)
                .orElseThrow(() -> new NoSuchElementException("Document not found"));

        // Update metadata
        if (dto.getComplianceId() != null) document.setComplianceId(dto.getComplianceId());
        if (dto.getComplianceMappingId() != null) document.setComplianceMappingId(dto.getComplianceMappingId());
        if (dto.getEmployeeId() != null) document.setEmployeeId(dto.getEmployeeId());
        if (dto.getExpiryDate() != null) document.setExpiryDate(dto.getExpiryDate());
        if (dto.getUploadedBy() != null) document.setUploadedBy(dto.getUploadedBy());

        // If a new file is uploaded, replace the old file
        MultipartFile file = dto.getFile();
        if (file != null && !file.isEmpty()) {
            try {
                String uploadDir = "uploads/";
                Files.createDirectories(Paths.get(uploadDir));

                String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, uniqueFileName);

                Files.write(filePath, file.getBytes());

                // Update file info in entity
                document.setFileName(file.getOriginalFilename());
                document.setFileType(file.getContentType());
                document.setFilePath(filePath.toString());
                document.setFileSizeBytes(file.getSize());

            } catch (Exception e) {
                throw new RuntimeException("Failed to update file: " + e.getMessage(), e);
            }
        }

        ComplianceDocument updated = repository.save(document);
        return mapToResponse(updated);
    }

    @Override
    public void deleteDocument(Long documentId) {
        ComplianceDocument document = repository.findById(documentId)
                .orElseThrow(() -> new NoSuchElementException("Document not found"));

        try {
            // Delete the file from local disk if exists
            Path filePath = Paths.get(document.getFilePath());
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file from disk: " + e.getMessage(), e);
        }

        // Delete record from database
        repository.delete(document);
    }

    @Override
    public ComplianceDocumentResponseDto verifyDocument(Long documentId, String verifiedBy) {
        ComplianceDocument document = repository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with ID: " + documentId));

        // ✅ Correct field name usage
        document.setIsVerified(true);
        document.setVerifiedBy(verifiedBy);
        document.setVerifiedAt(LocalDateTime.now());
        document.setStatus(ComplianceDocument.DocumentStatus.Active);

        ComplianceDocument updated = repository.save(document);

        return mapToResponse(updated);
    }

    @Override
    public List<ComplianceDocumentResponseDto> getExpiredDocuments() {
        LocalDate today = LocalDate.now();

        List<ComplianceDocument> expiredDocs = repository.findByExpiryDateBefore(today);

        if (expiredDocs.isEmpty()) {
            throw new NoSuchElementException("No expired documents found");
        }

        return expiredDocs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private ComplianceDocumentResponseDto mapToResponse(ComplianceDocument doc) {
        return ComplianceDocumentResponseDto.builder()
                .employeeId(doc.getEmployeeId())
                .documentId(doc.getDocumentId())
                .complianceId(doc.getComplianceId())
                .complianceMappingId(doc.getComplianceMappingId())
                .fileName(doc.getFileName())
                .fileType(doc.getFileType())
                .filePath(doc.getFilePath())
                .fileSizeBytes(doc.getFileSizeBytes())
                .uploadedBy(doc.getUploadedBy())
                .uploadedAt(doc.getUploadedAt())
                .expiryDate(doc.getExpiryDate())
                .isVerified(doc.getIsVerified())
                .verifiedBy(doc.getVerifiedBy())
                .verifiedAt(doc.getVerifiedAt())
                .status(doc.getStatus().name())
                .build();
    }

    @Override
    public ComplianceDocument getDocumentEntity(Long documentId) {
        return repository.findById(documentId)
                .orElseThrow(() -> new NoSuchElementException("Document not found"));
    }
}
