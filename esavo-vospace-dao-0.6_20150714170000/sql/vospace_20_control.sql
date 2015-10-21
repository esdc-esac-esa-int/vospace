-- Schema controls for vospace db

CREATE SCHEMA controls;

ALTER SCHEMA controls OWNER TO postgres;

SET search_path = controls, pg_catalog;

-- Enum Types

--CREATE TYPE status_type AS ENUM ('active', 'deleted');
--CREATE TYPE role_type AS ENUM ('viewer', 'editor');
--CREATE TYPE data_mng_type AS ENUM ('create', 'delete', 'move', 'copy', 'get', 'set', 'find');
--CREATE TYPE metadata_retrieval_type AS ENUM ('protocols', 'views', 'properties');
--CREATE TYPE transfer_oper_type AS ENUM ('pushToVoSpace', 'pullToVoSpace', 'pullFromVoSpace', 'pushFromVoSpace');

-- User Table

CREATE TABLE controls."user" (
    user_oid INTEGER NOT NULL,
    uname VARCHAR UNIQUE NOT NULL,
    surname VARCHAR NOT NULL,
    institute VARCHAR,
    address1 VARCHAR,
    address2 VARCHAR,
    town VARCHAR,
    state VARCHAR,
    zip_code INTEGER,
    country VARCHAR,
    mail VARCHAR NOT NULL,
    phone_number VARCHAR,
    fax VARCHAR,
    register_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT user_id PRIMARY KEY (user_oid)
);

-- Access Log Table

CREATE TABLE controls.access_log (
    access_log_oid INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    login_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    logout_timestamp TIMESTAMP WITHOUT TIME ZONE,
    quota BIGINT NOT NULL,
    internetSite VARCHAR,
    environment VARCHAR,
    CONSTRAINT access_log_id PRIMARY KEY (access_log_oid),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id)
        REFERENCES "user" (user_oid) ON DELETE CASCADE ON UPDATE CASCADE
        NOT DEFERRABLE
);

-- Group Table

CREATE TABLE controls."group" (
    group_oid INTEGER NOT NULL,
    owner_id INTEGER NOT NULL,
    name VARCHAR NOT NULL,
    creation_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    deletion_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT group_id PRIMARY KEY (group_oid),
    CONSTRAINT owner_id_fk FOREIGN KEY (owner_id)
        REFERENCES "user" (user_oid) ON DELETE CASCADE ON UPDATE CASCADE
        NOT DEFERRABLE
);

-- Group Management Operation Table

--CREATE TABLE controls.group_mng_oper (
--    group_mng_oper_oid BIGINT NOT NULL,
--    group_id BIGINT NOT NULL,
--    operation_type group_oper_type NOT NULL,
--    group_new_name VARCHAR,
--    group_new_description VARCHAR,
--    user_id BIGINT,
--    role role_type,
--    CONSTRAINT group_mng_oper_id PRIMARY KEY (group_mng_oper_oid),
--    CONSTRAINT group_mng_oper_user_id_fk FOREIGN KEY (user_id)
--        REFERENCES "user" (user_oid) ON DELETE CASCADE ON UPDATE CASCADE
--        NOT DEFERRABLE,
--    CONSTRAINT group_mng_oper_group_id_fk FOREIGN KEY (group_id)
--        REFERENCES "group" (group_oid) ON DELETE CASCADE ON UPDATE CASCADE
--        NOT DEFERRABLE    
--);

-- Group Management Log Table

--CREATE TABLE controls.group_mng_log (
--    group_mng_log_oid BIGINT NOT NULL,
--    user_id BIGINT NOT NULL,
--    operation_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
--    group_mng_oper_id  BIGINT NOT NULL,
--    CONSTRAINT group_mng_log_id PRIMARY KEY (group_mng_log_oid),
--    CONSTRAINT group_mng_log_user_id_fk FOREIGN KEY (user_id)
--        REFERENCES "user" (user_oid) ON DELETE CASCADE ON UPDATE CASCADE
--        NOT DEFERRABLE,
--    CONSTRAINT group_mng_log_group_mng_oper_id_fk FOREIGN KEY (group_mng_oper_id)
--        REFERENCES group_mng_oper (group_mng_oper_oid) ON DELETE CASCADE ON UPDATE CASCADE
--        NOT DEFERRABLE
--);

-- Data Management
-- Represents the following Vospace operations: create, delete, move and copy

--CREATE TABLE controls.data_mng_oper (
--    data_mng_oper_oid BIGINT NOT NULL,
--    operation_type data_mng_type NOT NULL,
--    node_source_id BIGINT NOT NULL,
--    node_target_id BIGINT,
--    size BIGINT,
--    CONSTRAINT data_mng_oper_id PRIMARY KEY (data_mng_oper_oid),
--    CONSTRAINT data_mng_oper_node_source_fk FOREIGN KEY (node_source_id)
--        REFERENCES node (node_oid) ON DELETE CASCADE ON UPDATE CASCADE
--        NOT DEFERRABLE,
--    CONSTRAINT data_mng_oper_node_target_fk FOREIGN KEY (node_target_id)
--        REFERENCES node (node_oid) ON DELETE CASCADE ON UPDATE CASCADE
--        NOT DEFERRABLE
--);

CREATE TABLE controls.data_mng_log (
    data_mng_log_oid INTEGER NOT NULL,
    start_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    stop_timestamp TIMESTAMP WITHOUT TIME ZONE,
    operation_type VARCHAR NOT NULL,
    user_id INTEGER NOT NULL,
    node_source VARCHAR NOT NULL,
    node_target VARCHAR,
    size BIGINT,
    CONSTRAINT data_mng_log_id PRIMARY KEY (data_mng_log_oid),
    CONSTRAINT data_mng_log_user_id_fk FOREIGN KEY (user_id)
        REFERENCES "user" (user_oid) ON DELETE CASCADE ON UPDATE CASCADE
        NOT DEFERRABLE
);

-- Metadata Retrieval
-- Represents the following Vospace operations: getProtocols, getViews and getProperties

--CREATE TABLE controls.metadata_retrieval_oper (
--    metadata_retrieval_oper_oid BIGINT NOT NULL,
--    operation_type metadata_retrieval_type NOT NULL,
--    CONSTRAINT metadata_retrieval_oper_id PRIMARY KEY (metadata_retrieval_oper_oid)
--);

CREATE TABLE controls.metadata_retrieval_log (
    metadata_retrieval_log_oid INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    start_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    stop_timestamp TIMESTAMP WITHOUT TIME ZONE,
    operation_type VARCHAR NOT NULL,    
    CONSTRAINT metadata_retrieval_log_id PRIMARY KEY (metadata_retrieval_log_oid),
    CONSTRAINT metadata_retrieval_log_user_id_fk FOREIGN KEY (user_id)
        REFERENCES "user" (user_oid) ON DELETE CASCADE ON UPDATE CASCADE
        NOT DEFERRABLE
);

-- Share Operation Table
-- Represents the following operations: shareNodeWithUser and shareNodeWithGroup

--CREATE TABLE controls.share_oper (
--    share_oper_oid BIGINT NOT NULL,
--    node_id BIGINT NOT NULL,
--    share_with_user_id BIGINT,
--    share_with_group_id BIGINT,
--    role role_type,
--    status status_type,
--    CONSTRAINT share_oper_id PRIMARY KEY (share_oper_oid),
--    CONSTRAINT share_oper_node_id_fk FOREIGN KEY (node_id)
--        REFERENCES node (node_oid) ON DELETE CASCADE ON UPDATE CASCADE
--        NOT DEFERRABLE,
--    CONSTRAINT share_oper_user_id_fk FOREIGN KEY (share_with_user_id)
--        REFERENCES "user" (user_oid) ON DELETE CASCADE ON UPDATE CASCADE
--        NOT DEFERRABLE,
--    CONSTRAINT share_oper_group_id_fk FOREIGN KEY (share_with_group_id)
--        REFERENCES "group" (group_oid) ON DELETE CASCADE ON UPDATE CASCADE
--        NOT DEFERRABLE
--
--);

CREATE TABLE controls.share_log (
    share_log_oid INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    operation_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_timestamp TIMESTAMP WITHOUT TIME ZONE,
    node VARCHAR NOT NULL,
    CONSTRAINT share_log_id PRIMARY KEY (share_log_oid),
    CONSTRAINT share_log_user_id_fk FOREIGN KEY (user_id)
        REFERENCES "user" (user_oid) ON DELETE CASCADE ON UPDATE CASCADE
        NOT DEFERRABLE
);

-- Transfer Operation Table
-- Represents the following VoSpace operations: pushToVoSpace, pullToVoSpace, pullFromVoSpace and pushFromVoSpace

--CREATE TABLE controls.transfer_oper (
--    transfer_oper_oid BIGINT NOT NULL,
--    operation_type transfer_oper_type NOT NULL,
--    CONSTRAINT transfer_oper_id PRIMARY KEY (transfer_oper_oid)
--);

CREATE TABLE controls.transfer_log (
    transfer_log_oid INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    start_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    stop_timestamp TIMESTAMP WITHOUT TIME ZONE,
    operation_type VARCHAR NOT NULL,
    protocol_param VARCHAR,
    view_param VARCHAR,
    total_size BIGINT,    
    CONSTRAINT transfer_log_id PRIMARY KEY (transfer_log_oid),
    CONSTRAINT transfer_log_user_id_fk FOREIGN KEY (user_id)
        REFERENCES "user" (user_oid) ON DELETE CASCADE ON UPDATE CASCADE
        NOT DEFERRABLE
);

-- Alter Tables

ALTER TABLE controls."user" OWNER TO postgres;

CREATE SEQUENCE user_user_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
ALTER TABLE controls.user_user_oid_seq OWNER TO postgres;
    
ALTER SEQUENCE user_user_oid_seq OWNED BY "user".user_oid;

ALTER TABLE ONLY "user" ALTER COLUMN user_oid SET DEFAULT nextval('user_user_oid_seq'::regclass);

--

ALTER TABLE controls.access_log OWNER TO postgres;

CREATE SEQUENCE controls.access_log_access_log_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
ALTER TABLE controls.access_log_access_log_oid_seq OWNER TO postgres;
    
ALTER SEQUENCE controls.access_log_access_log_oid_seq OWNED BY access_log.access_log_oid;

ALTER TABLE ONLY controls.access_log ALTER COLUMN access_log_oid SET DEFAULT nextval('access_log_access_log_oid_seq'::regclass);

--

ALTER TABLE controls."group" OWNER TO postgres;

CREATE SEQUENCE controls.group_group_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
ALTER TABLE controls.group_group_oid_seq OWNER TO postgres;
    
ALTER SEQUENCE group_group_oid_seq OWNED BY "group".group_oid;

ALTER TABLE ONLY "group" ALTER COLUMN group_oid SET DEFAULT nextval('group_group_oid_seq'::regclass);

--

--ALTER TABLE controls.group_mng_oper OWNER TO postgres;
--
--CREATE SEQUENCE group_mng_oper_group_mng_oper_oid_seq
--    START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;
--    
--ALTER TABLE controls.group_mng_oper_group_mng_oper_oid_seq OWNER TO postgres;
--    
--ALTER SEQUENCE group_mng_oper_group_mng_oper_oid_seq OWNED BY group_mng_oper.group_mng_oper_oid;
--
--ALTER TABLE ONLY group_mng_oper ALTER COLUMN group_mng_oper_oid SET DEFAULT nextval('group_mng_oper_group_mng_oper_oid_seq'::regclass);

--

--ALTER TABLE controls.group_mng_log OWNER TO postgres;
--
--CREATE SEQUENCE group_mng_log_group_mng_log_oid_seq
--    START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;
--    
--ALTER TABLE controls.group_mng_log_group_mng_log_oid_seq OWNER TO postgres;
--    
--ALTER SEQUENCE group_mng_log_group_mng_log_oid_seq OWNED BY group_mng_log.group_mng_log_oid;
--
--ALTER TABLE ONLY group_mng_log ALTER COLUMN group_mng_log_oid SET DEFAULT nextval('group_mng_log_group_mng_log_oid_seq'::regclass);

--

--ALTER TABLE controls.data_mng_oper OWNER TO postgres;
--
--CREATE SEQUENCE data_mng_oper_data_mng_oper_oid_seq
--    START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;
--    
--ALTER TABLE controls.data_mng_oper_data_mng_oper_oid_seq OWNER TO postgres;
--    
--ALTER SEQUENCE data_mng_oper_data_mng_oper_oid_seq OWNED BY data_mng_oper.data_mng_oper_oid;
--
--ALTER TABLE ONLY data_mng_oper ALTER COLUMN data_mng_oper_oid SET DEFAULT nextval('data_mng_oper_data_mng_oper_oid_seq'::regclass);

--

ALTER TABLE controls.data_mng_log OWNER TO postgres;

CREATE SEQUENCE data_mng_log_data_mng_log_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
ALTER TABLE controls.data_mng_log_data_mng_log_oid_seq OWNER TO postgres;
    
ALTER SEQUENCE data_mng_log_data_mng_log_oid_seq OWNED BY data_mng_log.data_mng_log_oid;

ALTER TABLE ONLY data_mng_log ALTER COLUMN data_mng_log_oid SET DEFAULT nextval('data_mng_log_data_mng_log_oid_seq'::regclass);

--

--ALTER TABLE controls.metadata_retrieval_oper OWNER TO postgres;
--
--CREATE SEQUENCE metadata_retrieval_oper_metadata_retrieval_oper_oid_seq
--    START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;
--    
--ALTER TABLE controls.metadata_retrieval_oper_metadata_retrieval_oper_oid_seq OWNER TO postgres;
--    
--ALTER SEQUENCE metadata_retrieval_oper_metadata_retrieval_oper_oid_seq OWNED BY metadata_retrieval_oper.metadata_retrieval_oper_oid;
--
--ALTER TABLE ONLY metadata_retrieval_oper ALTER COLUMN metadata_retrieval_oper_oid SET DEFAULT nextval('metadata_retrieval_oper_metadata_retrieval_oper_oid_seq'::regclass);

--

ALTER TABLE controls.metadata_retrieval_log OWNER TO postgres;

CREATE SEQUENCE metadata_retrieval_log_metadata_retrieval_log_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
ALTER TABLE controls.metadata_retrieval_log_metadata_retrieval_log_oid_seq OWNER TO postgres;
    
ALTER SEQUENCE metadata_retrieval_log_metadata_retrieval_log_oid_seq OWNED BY metadata_retrieval_log.metadata_retrieval_log_oid;

ALTER TABLE ONLY metadata_retrieval_log ALTER COLUMN metadata_retrieval_log_oid SET DEFAULT nextval('metadata_retrieval_log_metadata_retrieval_log_oid_seq'::regclass);

--

--ALTER TABLE controls.share_oper OWNER TO postgres;
--
--CREATE SEQUENCE share_oper_share_oper_oid_seq
--    START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;
--    
--ALTER TABLE controls.share_oper_share_oper_oid_seq OWNER TO postgres;
--    
--ALTER SEQUENCE share_oper_share_oper_oid_seq OWNED BY share_oper.share_oper_oid;
--
--ALTER TABLE ONLY share_oper ALTER COLUMN share_oper_oid SET DEFAULT nextval('share_oper_share_oper_oid_seq'::regclass);

--

ALTER TABLE controls.share_log OWNER TO postgres;

CREATE SEQUENCE share_log_share_log_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
ALTER TABLE controls.share_log_share_log_oid_seq OWNER TO postgres;
    
ALTER SEQUENCE share_log_share_log_oid_seq OWNED BY share_log.share_log_oid;

ALTER TABLE ONLY share_log ALTER COLUMN share_log_oid SET DEFAULT nextval('share_log_share_log_oid_seq'::regclass);

--

--ALTER TABLE controls.transfer_oper OWNER TO postgres;
--
--CREATE SEQUENCE transfer_oper_transfer_oper_oid_seq
--    START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;
--    
--ALTER TABLE controls.transfer_oper_transfer_oper_oid_seq OWNER TO postgres;
--    
--ALTER SEQUENCE transfer_oper_transfer_oper_oid_seq OWNED BY transfer_oper.transfer_oper_oid;
--
--ALTER TABLE ONLY transfer_oper ALTER COLUMN transfer_oper_oid SET DEFAULT nextval('transfer_oper_transfer_oper_oid_seq'::regclass);

--

ALTER TABLE controls.transfer_log OWNER TO postgres;

CREATE SEQUENCE transfer_log_transfer_log_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
ALTER TABLE controls.transfer_log_transfer_log_oid_seq OWNER TO postgres;
    
ALTER SEQUENCE transfer_log_transfer_log_oid_seq OWNED BY transfer_log.transfer_log_oid;

ALTER TABLE ONLY transfer_log ALTER COLUMN transfer_log_oid SET DEFAULT nextval('transfer_log_transfer_log_oid_seq'::regclass);

--

--ALTER TABLE controls.node OWNER TO postgres;
--
--CREATE SEQUENCE node_node_oid_seq
--    START WITH 1
--    INCREMENT BY 1
--    NO MINVALUE
--    NO MAXVALUE
--    CACHE 1;
--    
--ALTER TABLE controls.node_node_oid_seq OWNER TO postgres;
--    
--ALTER SEQUENCE node_node_oid_seq OWNED BY node.node_oid;
--
--ALTER TABLE ONLY node ALTER COLUMN node_oid SET DEFAULT nextval('node_node_oid_seq'::regclass);