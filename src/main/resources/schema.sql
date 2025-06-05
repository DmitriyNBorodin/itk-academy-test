CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS wallets(
    id TEXT PRIMARY KEY,
    balance BIGINT NOT NULL,
    CONSTRAINT not_negative_balance CHECK (balance >= 0));