package com.example.User.serviceimpl;

import com.example.User.entity.Document;
import com.example.User.entity.DocumentStatus;
import com.example.User.entity.Employee;
import com.example.User.exception.ResourceNotFoundException;
import com.example.User.repository.DocumentRepository;
import com.example.User.repository.EmployeeRepository;
import com.example.User.service.DocumentService;
import com.example.User.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private  DocumentRepository documentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailService emailService;



    @Value("${file.upload-dir}")
    private String uploadDir;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

//    private Document getDocumentById(Long documentId) {
//        return null;
//    }


    @Override
    public Document uploadDocument(MultipartFile file, Long employeeId, String uploadedBy, String documentType) throws IOException {
        // Check if employee exists
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path fileStorage = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(fileStorage);

        Path targetLocation = fileStorage.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        Document doc = new Document();
        doc.setEmployee(employee); // map full entity
        doc.setUploadedBy(uploadedBy);
        doc.setDocumentType(documentType);
        doc.setFileName(fileName);
        doc.setFilePath(targetLocation.toString());
        doc.setUploadedAt(LocalDateTime.now());
        doc.setStatus(String.valueOf(DocumentStatus.PENDING));

        return documentRepository.save(doc);
    }



    @Override
    public List<Document> getDocumentsByEmployee(Long employeeId) {
        return documentRepository.findByEmployee_EmployeeId(employeeId);
    }

    @Override
    public List<Document> verifyAllDocumentsByEmployee(Long employeeId, String remarks) {
        List<Document> docs = documentRepository.findByEmployee_EmployeeId(employeeId);

        if (docs.isEmpty()) {
            throw new ResourceNotFoundException("No documents found for employeeId " + employeeId);
        }

        for (Document doc : docs) {
            doc.setStatus(String.valueOf(DocumentStatus.VERIFIED));
            doc.setRemarks(remarks);
            doc.setVerifiedAt(LocalDateTime.now());
        }

      //  return documentRepository.saveAll(docs);
        documentRepository.saveAll(docs);
        // Fetch updated list with relations loaded
        return documentRepository.findByEmployee_EmployeeId(employeeId);
    }

    @Override
    public List<Document> rejectAllDocumentsByEmployee(Long employeeId, String remarks) {
        List<Document> docs = documentRepository.findByEmployee_EmployeeId(employeeId);

        if (docs.isEmpty()) {
            throw new ResourceNotFoundException("No documents found for employeeId " + employeeId);
        }

        for (Document doc : docs) {
            doc.setStatus(String.valueOf(DocumentStatus.REJECTED));
            doc.setRemarks(remarks);
            doc.setVerifiedAt(LocalDateTime.now());
        }
        // 🔔 Fetch employee email
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        String emailText = "Dear " + employee.getName() + ",\n\n"
                + "Your documents have been rejected for the following reason:\n\n"
                + remarks + "\n\n"
                + "Please upload the required documents again.\n\n"
                + "Regards,\nHR Team";


        emailService.sendEmail(employee.getEmail(), "Document Rejection Notice", emailText);


      //  return documentRepository.saveAll(docs);
        documentRepository.saveAll(docs);
        return documentRepository.findByEmployee_EmployeeId(employeeId);

    }

    @Override
    public void deleteDocument(Long documentId) {
       Document docs = documentRepository.findByDocumentId(documentId);
     //   Document doc = getDocumentById(documentId);
        documentRepository.delete(docs);
    }



    @Override
    public void deleteDocumentsByEmployeeId(Long employeeId) {
        List<Document> docs = documentRepository.findByEmployee_EmployeeId(employeeId);
        documentRepository.deleteAll(docs);
    }




    @Override
    public List<Document> getDocumentsForManager(Long managerId) {
        // Step 1: Fetch employees under the manager
        List<Employee> employees = employeeRepository.findByManagerId(managerId);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found under manager with ID: " + managerId);
        }

        // Step 2: Extract employee IDs
        List<Long> employeeIds = employees.stream()
                .map(Employee::getId)
                .toList();  // This gives List<Long>
        System.out.println("Employee IDs: " + employeeIds);

        // Step 3: Define allowed document types for managers
        List<String> allowedTypes = Arrays.asList("resume", "project history");

        // Debug print
        System.out.println("Employee IDs: " + employeeIds);
        System.out.println("Allowed Types: " + allowedTypes);

        // Step 4: Fetch documents for those employees with allowed types
        List<Document> documents = documentRepository.findByEmployeeIdInAndDocumentTypeIn(employeeIds, allowedTypes);
        if (documents.isEmpty()) {
            throw new ResourceNotFoundException("No documents found for employees under this manager.");
        }

        return documents;
    }


    @Override
    public Document getDocumentById(Long id) throws IOException {
        Optional<Document> optionalDoc = documentRepository.findById(id);
        if (optionalDoc.isEmpty()) {
            throw new ResourceNotFoundException("Document not found with ID: " + id);
        }

        Document document = optionalDoc.get();

        // Verify file exists on disk
        Path filePath = Paths.get(document.getFilePath());
        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException("File not found on disk for document ID: " + id);
        }

        return document;
    }
}





