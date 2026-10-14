
ALTER TABLE users
    ADD COLUMN is_verified BOOLEAN NOT NULL DEFAULT FALSE;

CREATE TABLE otp (
                     otp_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                     user_id UUID NOT NULL
                         REFERENCES users(id) ON DELETE RESTRICT,

                     otp_hash VARCHAR(255) NOT NULL,

                     expires_at TIMESTAMPTZ NOT NULL,

                     attempts INT NOT NULL DEFAULT 0,

                     is_used BOOLEAN NOT NULL DEFAULT FALSE,

                     reset_token_hash VARCHAR(255),

                     reset_token_expires_at TIMESTAMPTZ,

                     otp_type VARCHAR(30) NOT NULL
                         CHECK (otp_type IN ('EMAIL_VERIFICATION', 'PASSWORD_RESET')),

                     created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                     updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_otp_user_type
    ON otp(user_id, otp_type);