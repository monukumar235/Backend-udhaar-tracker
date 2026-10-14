
-- V1: Initial schema for UdhaarTrack Pro

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       full_name VARCHAR(120) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
                           CHECK (status IN ('ACTIVE', 'LOCKED', 'DISABLED')),
                       created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE businesses (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            name VARCHAR(160) NOT NULL,
                            status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
                                CHECK (status IN ('ACTIVE', 'SUSPENDED', 'CLOSED')),
                            created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE business_memberships (
                                      business_id UUID NOT NULL
                                          REFERENCES businesses(id) ON DELETE RESTRICT,
                                      user_id UUID NOT NULL
                                          REFERENCES users(id) ON DELETE RESTRICT,
                                      role VARCHAR(20) NOT NULL
                                          CHECK (role IN ('OWNER', 'MANAGER', 'CASHIER')),
                                      created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      PRIMARY KEY (business_id, user_id)
);

CREATE INDEX idx_business_memberships_user_id
    ON business_memberships(user_id);

CREATE TABLE customers (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           business_id UUID NOT NULL
                               REFERENCES businesses(id) ON DELETE RESTRICT,
                           name VARCHAR(120) NOT NULL,
                           phone VARCHAR(20),
                           email VARCHAR(255),
                           status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
                               CHECK (status IN ('ACTIVE', 'INACTIVE')),
                           created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_customers_business_created
    ON customers(business_id, created_at DESC);

CREATE INDEX idx_customers_business_phone
    ON customers(business_id, phone);