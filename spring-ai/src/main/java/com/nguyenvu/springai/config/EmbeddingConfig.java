package com.nguyenvu.springai.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingConfig {

    @Bean
    public EmbeddingModel embeddingModel() {
        // Using local transformer model for embeddings
        // This model will run locally without needing external API calls
        return new TransformersEmbeddingModel();
    }
}
