package com.joshi.listener;

import com.joshi.service.MetadataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import com.joshi.dto.DocumentProcessedEvent;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MetaDataEventListener {

    @Autowired
    private MetadataService metadataService;

    @KafkaListener(topics= "document.processed", groupId = "metadata-group")
    public void handleDocumentProcessed(DocumentProcessedEvent event) {
        log.info("Received document.processed event for file={}, classification={}",
                event.getFileName(), event.getClassification());

        if ("resume".equalsIgnoreCase(event.getClassification())) {
            metadataService.processResume(event.getTextExtracted());
        } else {
            metadataService.processGeneric(event.getFileName(), event.getClassification());
        }
    }
}

