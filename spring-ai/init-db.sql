-- Initialize PostgreSQL database for Spring AI with PgVector
-- This script runs automatically when the Docker container starts

-- Enable pgvector extension
CREATE EXTENSION IF NOT EXISTS vector;

-- Create additional indexes for better performance (optional)
-- These will be created by Spring AI automatically, but you can customize here

-- Log completion
DO $$
BEGIN
    RAISE NOTICE 'PostgreSQL with pgvector initialized successfully';
    RAISE NOTICE 'Database: spring_ai';
    RAISE NOTICE 'Vector extension: enabled';
END $$;
