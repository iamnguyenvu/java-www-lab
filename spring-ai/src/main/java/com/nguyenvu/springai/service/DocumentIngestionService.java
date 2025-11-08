package com.nguyenvu.springai.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class DocumentIngestionService {
    private static final Logger log = LoggerFactory.getLogger(DocumentIngestionService.class);
    
    private final VectorStore vectorStore;
    private final Resource[] documents;
    
    public DocumentIngestionService(
            VectorStore vectorStore,
            @Value("classpath:/documents/*.txt") Resource[] documents) {
        this.vectorStore = vectorStore;
        this.documents = documents;
    }

    @PostConstruct
    public void ingestDocuments() {
        if (documents == null || documents.length == 0) {
            log.warn("No documents found to ingest");
            return;
        }

        try {
            log.info("Starting document ingestion - {} files found", documents.length);
            
            for (Resource document : documents) {
                log.info("Processing: {}", document.getFilename());
                
                // Read document
                TextReader reader = new TextReader(document);
                List<Document> docs = reader.get();
                
                // Split into chunks
                TokenTextSplitter splitter = new TokenTextSplitter(500, 100, 5, 2000, true);
                List<Document> chunks = splitter.apply(docs);
                
                // Add metadata
                chunks.forEach(chunk -> {
                    chunk.getMetadata().put("source", document.getFilename());
                    chunk.getMetadata().put("type", "policy");
                });
                
                // Store in vector database
                vectorStore.add(chunks);
                log.info("Ingested {} chunks from {}", chunks.size(), document.getFilename());
            }
            
            log.info("Document ingestion completed successfully");
        } catch (Exception e) {
            log.error("Error during document ingestion", e);
        }
    }

    public void clearStore() {
        log.info("Clearing vector store");
        vectorStore.delete(List.of());
    }

    public void reindex() {
        log.info("Reindexing documents");
        clearStore();
        ingestDocuments();
    }
}
