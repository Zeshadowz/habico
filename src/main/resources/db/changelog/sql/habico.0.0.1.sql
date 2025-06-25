-- liquibase formatted sql

-- changeset author:habico_0_0_1 id:1
CREATE TABLE address
(
    id            BIGINT PRIMARY KEY,
    street        VARCHAR(255) NOT NULL,
    street_number VARCHAR(5)   NOT NULL,
    additional    VARCHAR(255),
    city          VARCHAR(100) NOT NULL,
    state         VARCHAR(100),
    zipcode       VARCHAR(20)  NOT NULL,
    country       VARCHAR(100) NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE building
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    name       VARCHAR(30)           NOT NULL,
    is_active  BOOLEAN                        DEFAULT FALSE,
    status     VARCHAR(1)            NOT NULL DEFAULT 'O',
    address_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE       DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE       DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_building_address FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE unit
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    designation VARCHAR(30)           NOT NULL,
    status      VARCHAR(15)            NOT NULL DEFAULT 'O',
    building_id BIGINT                NOT NULL,
    address_id BIGINT,
    created_at  TIMESTAMP WITH TIME ZONE       DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE       DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_unit_building FOREIGN KEY (building_id) REFERENCES building (id),
    CONSTRAINT fk_unit_address FOREIGN KEY (address_id) REFERENCES (id)
);