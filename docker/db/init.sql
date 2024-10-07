SELECT 'CREATE DATABASE book-review-service'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'book-review-service')\gexec


