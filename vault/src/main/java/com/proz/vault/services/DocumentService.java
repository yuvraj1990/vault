package com.proz.vault.services;

import com.proz.vault.data.entities.Document;
import com.proz.vault.data.repository.DocumentRepository;
import com.proz.vault.exceptions.RecordNotFoundException;
import com.proz.vault.helpers.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author yubraj.singh
 */

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository repository;

    @Transactional
    public  void uploadfile(String filename,int appointmentId, int userId ,MultipartFile file) throws Throwable {
        Document document = new Document();
        document.setFileContent(file.getBytes());
        document.setFileName(filename);
        document.setUserId(userId);
        document.setS3path("s3//"+filename);
        document.setAppointmentId(appointmentId);
        document.setAccessRevoked(false);
        document.setCreatedAt(DateTimeHelper.getCurrentTime());
        repository.save(document);
    }

    public  Document getFile(int documentId) throws Throwable {
        Optional <Document> document = repository.findById(documentId);
        if(!document.isPresent()) {
            throw new RecordNotFoundException("file is not availble");
        }
        return document.get();
    }

    @Transactional
    public  void removeAccess(int appointmentId) throws Throwable  {
        repository.revokeAcessForAppointment(appointmentId,true);
    }

    @Transactional
    public  void shareDocumentsBetween(int appointmentId,int shareAppointment) throws
            Throwable  {
       List<Document> docsByAppointment = repository.getDocsByAppointment(appointmentId);
       List<Document> newDocs = new ArrayList<>();
       docsByAppointment.stream().filter(document -> !document.getAccessRevoked())
               .forEach(document -> {
           Document doc = new Document();
           doc.setAppointmentId(shareAppointment);
           doc.setAccessRevoked(false);
           doc.setS3path(document.getS3path());
           doc.setCreatedAt(document.getCreatedAt());
           doc.setFileName(document.getFileName());
           doc.setFileContent(document.getFileContent());
           doc.setUserId(document.getUserId());
           newDocs.add(doc);
       });
       repository.saveAll(newDocs);
    }
}
