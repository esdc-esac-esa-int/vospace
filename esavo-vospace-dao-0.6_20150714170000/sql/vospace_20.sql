--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: vos; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA vos;


ALTER SCHEMA vos OWNER TO postgres;

SET search_path = vos, pg_catalog;

--
-- Name: date_default(); Type: FUNCTION; Schema: vos; Owner: postgres
--

CREATE FUNCTION date_default() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
        NEW.creation_date := now();
        RETURN NEW;
    END;$$;


ALTER FUNCTION vos.date_default() OWNER TO postgres;

--
-- Name: quota_default(); Type: FUNCTION; Schema: vos; Owner: postgres
--

CREATE FUNCTION quota_default() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
        NEW.quota := 0;
        RETURN NEW;
    END;$$;


ALTER FUNCTION vos.quota_default() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: capability_param; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE capability_param (
    capability_param_oid integer NOT NULL,
    vo_capability_id integer NOT NULL,
    name character varying NOT NULL,
    value character varying NOT NULL
);


ALTER TABLE vos.capability_param OWNER TO postgres;

--
-- Name: capability_param_capability_param_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE capability_param_capability_param_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.capability_param_capability_param_oid_seq OWNER TO postgres;

--
-- Name: capability_param_capability_param_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE capability_param_capability_param_oid_seq OWNED BY capability_param.capability_param_oid;


--
-- Name: container_node; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE container_node (
    node_oid integer NOT NULL
);


ALTER TABLE vos.container_node OWNER TO postgres;

--
-- Name: data_node; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE data_node (
    node_oid integer NOT NULL,
    busy boolean NOT NULL
);


ALTER TABLE vos.data_node OWNER TO postgres;

--
-- Name: groups; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE groups (
    group_oid integer NOT NULL,
    name character varying NOT NULL,
    description character varying NOT NULL,
    manager integer NOT NULL,
    creation_date timestamp without time zone NOT NULL
);


ALTER TABLE vos.groups OWNER TO postgres;

--
-- Name: group_group_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE group_group_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.group_group_oid_seq OWNER TO postgres;

--
-- Name: group_group_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE group_group_oid_seq OWNED BY groups.group_oid;


--
-- Name: group_node_property; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE group_node_property (
    node_property_oid integer NOT NULL
);


ALTER TABLE vos.group_node_property OWNER TO postgres;

--
-- Name: link_node; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE link_node (
    node_oid integer NOT NULL,
    target character varying NOT NULL
);


ALTER TABLE vos.link_node OWNER TO postgres;

--
-- Name: node; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE node (
    node_oid integer NOT NULL,
    name character varying NOT NULL,
    owner_id integer NOT NULL,
    parent_container_id integer
);


ALTER TABLE vos.node OWNER TO postgres;

--
-- Name: node_capability; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE node_capability (
    node_capability_oid integer NOT NULL,
    node_id integer NOT NULL,
    capability_id integer NOT NULL
);


ALTER TABLE vos.node_capability OWNER TO postgres;

--
-- Name: node_capability_node_capability_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE node_capability_node_capability_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.node_capability_node_capability_oid_seq OWNER TO postgres;

--
-- Name: node_capability_node_capability_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE node_capability_node_capability_oid_seq OWNED BY node_capability.node_capability_oid;


--
-- Name: node_node_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE node_node_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.node_node_oid_seq OWNER TO postgres;

--
-- Name: node_node_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE node_node_oid_seq OWNED BY node.node_oid;


--
-- Name: node_property; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE node_property (
    node_property_oid integer NOT NULL,
    node_id integer NOT NULL,
    property_id integer NOT NULL,
    value character varying
);


ALTER TABLE vos.node_property OWNER TO postgres;

--
-- Name: node_property_node_property_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE node_property_node_property_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.node_property_node_property_oid_seq OWNER TO postgres;

--
-- Name: node_property_node_property_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE node_property_node_property_oid_seq OWNED BY node_property.node_property_oid;


--
-- Name: node_view; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE node_view (
    node_view_oid integer NOT NULL,
    node_id integer NOT NULL,
    view_id integer NOT NULL
);


ALTER TABLE vos.node_view OWNER TO postgres;

--
-- Name: node_view_node_view_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE node_view_node_view_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.node_view_node_view_oid_seq OWNER TO postgres;

--
-- Name: node_view_node_view_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE node_view_node_view_oid_seq OWNED BY node_view.node_view_oid;


--
-- Name: protocol_param; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE protocol_param (
    protocol_param_oid integer NOT NULL,
    vo_protocol_id integer NOT NULL,
    name character varying NOT NULL,
    value character varying NOT NULL
);


ALTER TABLE vos.protocol_param OWNER TO postgres;

--
-- Name: protocol_param_protocol_param_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE protocol_param_protocol_param_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.protocol_param_protocol_param_oid_seq OWNER TO postgres;

--
-- Name: protocol_param_protocol_param_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE protocol_param_protocol_param_oid_seq OWNED BY protocol_param.protocol_param_oid;


--
-- Name: share_group; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE share_group (
    share_group_oid integer NOT NULL,
    node_property_id integer NOT NULL,
    group_id integer NOT NULL
);


ALTER TABLE vos.share_group OWNER TO postgres;

--
-- Name: share_group_share_group_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE share_group_share_group_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.share_group_share_group_oid_seq OWNER TO postgres;

--
-- Name: share_group_share_group_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE share_group_share_group_oid_seq OWNED BY share_group.share_group_oid;


--
-- Name: share_user; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE share_user (
    share_user_oid integer NOT NULL,
    node_property_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE vos.share_user OWNER TO postgres;

--
-- Name: share_user_share_user_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE share_user_share_user_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.share_user_share_user_oid_seq OWNER TO postgres;

--
-- Name: share_user_share_user_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE share_user_share_user_oid_seq OWNED BY share_user.share_user_oid;


--
-- Name: structured_data_node; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE structured_data_node (
    node_oid integer NOT NULL
);


ALTER TABLE vos.structured_data_node OWNER TO postgres;

--
-- Name: unstructured_data_node; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE unstructured_data_node (
    node_oid integer NOT NULL
);


ALTER TABLE vos.unstructured_data_node OWNER TO postgres;

--
-- Name: user_group; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE user_group (
    user_group_oid integer NOT NULL,
    user_id integer NOT NULL,
    group_id integer NOT NULL
);


ALTER TABLE vos.user_group OWNER TO postgres;

--
-- Name: user_group_user_group_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE user_group_user_group_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.user_group_user_group_oid_seq OWNER TO postgres;

--
-- Name: user_group_user_group_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE user_group_user_group_oid_seq OWNED BY user_group.user_group_oid;


--
-- Name: user_node_property; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE user_node_property (
    node_property_oid integer NOT NULL
);


ALTER TABLE vos.user_node_property OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    user_oid integer NOT NULL,
    name character varying NOT NULL,
    full_name character varying NOT NULL,
    mail character varying NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    quota bigint
);


ALTER TABLE vos.users OWNER TO postgres;

--
-- Name: user_user_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE user_user_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.user_user_oid_seq OWNER TO postgres;

--
-- Name: user_user_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE user_user_oid_seq OWNED BY users.user_oid;


--
-- Name: view_param; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE view_param (
    view_param_oid integer NOT NULL,
    vo_view_id integer NOT NULL,
    name character varying NOT NULL,
    value character varying NOT NULL
);


ALTER TABLE vos.view_param OWNER TO postgres;

--
-- Name: view_param_view_param_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE view_param_view_param_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.view_param_view_param_oid_seq OWNER TO postgres;

--
-- Name: view_param_view_param_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE view_param_view_param_oid_seq OWNED BY view_param.view_param_oid;


--
-- Name: vo_capability; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE vo_capability (
    vo_capability_oid integer NOT NULL,
    uri character varying NOT NULL,
    end_point character varying NOT NULL
);


ALTER TABLE vos.vo_capability OWNER TO postgres;

--
-- Name: vo_capability_vo_capability_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE vo_capability_vo_capability_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.vo_capability_vo_capability_oid_seq OWNER TO postgres;

--
-- Name: vo_capability_vo_capability_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE vo_capability_vo_capability_oid_seq OWNED BY vo_capability.vo_capability_oid;


--
-- Name: vo_property; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE vo_property (
    vo_property_oid integer NOT NULL,
    uri character varying NOT NULL,
    description character varying NOT NULL,
    readonly boolean NOT NULL,
    property_type character varying NOT NULL
);


ALTER TABLE vos.vo_property OWNER TO postgres;

--
-- Name: vo_property_vo_property_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE vo_property_vo_property_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.vo_property_vo_property_oid_seq OWNER TO postgres;

--
-- Name: vo_property_vo_property_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE vo_property_vo_property_oid_seq OWNED BY vo_property.vo_property_oid;


--
-- Name: vo_protocol; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE vo_protocol (
    vo_protocol_oid integer NOT NULL,
    uri character varying NOT NULL,
    end_point character varying NOT NULL,
    protocol_type character varying NOT NULL
);


ALTER TABLE vos.vo_protocol OWNER TO postgres;

--
-- Name: vo_protocol_vo_protocol_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE vo_protocol_vo_protocol_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.vo_protocol_vo_protocol_oid_seq OWNER TO postgres;

--
-- Name: vo_protocol_vo_protocol_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE vo_protocol_vo_protocol_oid_seq OWNED BY vo_protocol.vo_protocol_oid;


--
-- Name: vo_view; Type: TABLE; Schema: vos; Owner: postgres; Tablespace: 
--

CREATE TABLE vo_view (
    vo_view_oid integer NOT NULL,
    uri character varying NOT NULL,
    original boolean NOT NULL,
    view_type character varying NOT NULL
);


ALTER TABLE vos.vo_view OWNER TO postgres;

--
-- Name: vo_view_vo_view_oid_seq; Type: SEQUENCE; Schema: vos; Owner: postgres
--

CREATE SEQUENCE vo_view_vo_view_oid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vos.vo_view_vo_view_oid_seq OWNER TO postgres;

--
-- Name: vo_view_vo_view_oid_seq; Type: SEQUENCE OWNED BY; Schema: vos; Owner: postgres
--

ALTER SEQUENCE vo_view_vo_view_oid_seq OWNED BY vo_view.vo_view_oid;


--
-- Name: capability_param_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY capability_param ALTER COLUMN capability_param_oid SET DEFAULT nextval('capability_param_capability_param_oid_seq'::regclass);


--
-- Name: group_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY groups ALTER COLUMN group_oid SET DEFAULT nextval('group_group_oid_seq'::regclass);


--
-- Name: node_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node ALTER COLUMN node_oid SET DEFAULT nextval('node_node_oid_seq'::regclass);


--
-- Name: node_capability_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node_capability ALTER COLUMN node_capability_oid SET DEFAULT nextval('node_capability_node_capability_oid_seq'::regclass);


--
-- Name: node_property_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node_property ALTER COLUMN node_property_oid SET DEFAULT nextval('node_property_node_property_oid_seq'::regclass);


--
-- Name: node_view_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node_view ALTER COLUMN node_view_oid SET DEFAULT nextval('node_view_node_view_oid_seq'::regclass);


--
-- Name: protocol_param_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY protocol_param ALTER COLUMN protocol_param_oid SET DEFAULT nextval('protocol_param_protocol_param_oid_seq'::regclass);


--
-- Name: share_group_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY share_group ALTER COLUMN share_group_oid SET DEFAULT nextval('share_group_share_group_oid_seq'::regclass);


--
-- Name: share_user_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY share_user ALTER COLUMN share_user_oid SET DEFAULT nextval('share_user_share_user_oid_seq'::regclass);


--
-- Name: user_group_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY user_group ALTER COLUMN user_group_oid SET DEFAULT nextval('user_group_user_group_oid_seq'::regclass);


--
-- Name: user_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN user_oid SET DEFAULT nextval('user_user_oid_seq'::regclass);


--
-- Name: view_param_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY view_param ALTER COLUMN view_param_oid SET DEFAULT nextval('view_param_view_param_oid_seq'::regclass);


--
-- Name: vo_capability_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY vo_capability ALTER COLUMN vo_capability_oid SET DEFAULT nextval('vo_capability_vo_capability_oid_seq'::regclass);


--
-- Name: vo_property_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY vo_property ALTER COLUMN vo_property_oid SET DEFAULT nextval('vo_property_vo_property_oid_seq'::regclass);


--
-- Name: vo_protocol_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY vo_protocol ALTER COLUMN vo_protocol_oid SET DEFAULT nextval('vo_protocol_vo_protocol_oid_seq'::regclass);


--
-- Name: vo_view_oid; Type: DEFAULT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY vo_view ALTER COLUMN vo_view_oid SET DEFAULT nextval('vo_view_vo_view_oid_seq'::regclass);


--
-- Name: capability_param_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY capability_param
    ADD CONSTRAINT capability_param_id PRIMARY KEY (capability_param_oid);


--
-- Name: container_node_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY container_node
    ADD CONSTRAINT container_node_id PRIMARY KEY (node_oid);


--
-- Name: data_node_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY data_node
    ADD CONSTRAINT data_node_id PRIMARY KEY (node_oid);


--
-- Name: group_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT group_id PRIMARY KEY (group_oid);


--
-- Name: group_node_property_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY group_node_property
    ADD CONSTRAINT group_node_property_id PRIMARY KEY (node_property_oid);


--
-- Name: link_node_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY link_node
    ADD CONSTRAINT link_node_id PRIMARY KEY (node_oid);


--
-- Name: node_capability_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY node_capability
    ADD CONSTRAINT node_capability_id PRIMARY KEY (node_capability_oid);


--
-- Name: node_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY node
    ADD CONSTRAINT node_id PRIMARY KEY (node_oid);


--
-- Name: node_property_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY node_property
    ADD CONSTRAINT node_property_id PRIMARY KEY (node_property_oid);


--
-- Name: node_view_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY node_view
    ADD CONSTRAINT node_view_id PRIMARY KEY (node_view_oid);


--
-- Name: protocol_param_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY protocol_param
    ADD CONSTRAINT protocol_param_id PRIMARY KEY (protocol_param_oid);


--
-- Name: share_group_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY share_group
    ADD CONSTRAINT share_group_id PRIMARY KEY (share_group_oid);


--
-- Name: share_user_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY share_user
    ADD CONSTRAINT share_user_id PRIMARY KEY (share_user_oid);


--
-- Name: structured_data_node_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY structured_data_node
    ADD CONSTRAINT structured_data_node_id PRIMARY KEY (node_oid);


--
-- Name: uk_group; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT uk_group UNIQUE (name, manager);


--
-- Name: uk_node_capability; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY node_capability
    ADD CONSTRAINT uk_node_capability UNIQUE (node_id, capability_id);


--
-- Name: uk_node_property; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

-- Allow to update property, otherwise it raises duplicated key
-- ALTER TABLE ONLY node_property
--    ADD CONSTRAINT uk_node_property UNIQUE (node_id, property_id);


--
-- Name: uk_node_view; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY node_view
    ADD CONSTRAINT uk_node_view UNIQUE (node_id, view_id);


--
-- Name: uk_share_group; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY share_group
    ADD CONSTRAINT uk_share_group UNIQUE (node_property_id, group_id);


--
-- Name: uk_share_user; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY share_user
    ADD CONSTRAINT uk_share_user UNIQUE (node_property_id, user_id);


--
-- Name: uk_user; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_user UNIQUE (name, full_name, mail);


--
-- Name: uk_user_group; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_group
    ADD CONSTRAINT uk_user_group UNIQUE (user_id, group_id);


--
-- Name: unstructured_data_node_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY unstructured_data_node
    ADD CONSTRAINT unstructured_data_node_id PRIMARY KEY (node_oid);


--
-- Name: user_group_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_group
    ADD CONSTRAINT user_group_id PRIMARY KEY (user_group_oid);


--
-- Name: user_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT user_id PRIMARY KEY (user_oid);


--
-- Name: user_node_property_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_node_property
    ADD CONSTRAINT user_node_property_id PRIMARY KEY (node_property_oid);


--
-- Name: users_name_key; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_name_key UNIQUE (name);


--
-- Name: view_param_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY view_param
    ADD CONSTRAINT view_param_id PRIMARY KEY (view_param_oid);


--
-- Name: vo_capability_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vo_capability
    ADD CONSTRAINT vo_capability_id PRIMARY KEY (vo_capability_oid);


--
-- Name: vo_capability_uri_key; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vo_capability
    ADD CONSTRAINT vo_capability_uri_key UNIQUE (uri);


--
-- Name: vo_property_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vo_property
    ADD CONSTRAINT vo_property_id PRIMARY KEY (vo_property_oid);


--
-- Name: vo_protocol_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vo_protocol
    ADD CONSTRAINT vo_protocol_id PRIMARY KEY (vo_protocol_oid);


--
-- Name: vo_view_id; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vo_view
    ADD CONSTRAINT vo_view_id PRIMARY KEY (vo_view_oid);


--
-- Name: vo_view_uri_key; Type: CONSTRAINT; Schema: vos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vo_view
    ADD CONSTRAINT vo_view_uri_key UNIQUE (uri);


--
-- Name: group_default; Type: TRIGGER; Schema: vos; Owner: postgres
--

CREATE TRIGGER group_default BEFORE INSERT ON groups FOR EACH ROW EXECUTE PROCEDURE date_default();


--
-- Name: quota_default; Type: TRIGGER; Schema: vos; Owner: postgres
--

CREATE TRIGGER quota_default BEFORE INSERT ON users FOR EACH ROW EXECUTE PROCEDURE quota_default();


--
-- Name: user_default; Type: TRIGGER; Schema: vos; Owner: postgres
--

CREATE TRIGGER user_default BEFORE INSERT ON users FOR EACH ROW EXECUTE PROCEDURE date_default();


--
-- Name: capability_param_capability_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY capability_param
    ADD CONSTRAINT capability_param_capability_fk FOREIGN KEY (vo_capability_id) REFERENCES vo_capability(vo_capability_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: container_node_id_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY container_node
    ADD CONSTRAINT container_node_id_fk FOREIGN KEY (node_oid) REFERENCES data_node(node_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: data_node_id_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY data_node
    ADD CONSTRAINT data_node_id_fk FOREIGN KEY (node_oid) REFERENCES node(node_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: group_node_property_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY group_node_property
    ADD CONSTRAINT group_node_property_fk FOREIGN KEY (node_property_oid) REFERENCES node_property(node_property_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: link_node_id_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY link_node
    ADD CONSTRAINT link_node_id_fk FOREIGN KEY (node_oid) REFERENCES node(node_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: manager_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT manager_fk FOREIGN KEY (manager) REFERENCES users(user_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: node_capability_capability_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node_capability
    ADD CONSTRAINT node_capability_capability_fk FOREIGN KEY (capability_id) REFERENCES vo_capability(vo_capability_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: node_capability_node_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node_capability
    ADD CONSTRAINT node_capability_node_fk FOREIGN KEY (node_id) REFERENCES node(node_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: node_owner_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node
    ADD CONSTRAINT node_owner_fk FOREIGN KEY (owner_id) REFERENCES users(user_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: node_parent_container_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node
    ADD CONSTRAINT node_parent_container_fk FOREIGN KEY (parent_container_id) REFERENCES container_node(node_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: node_property_node_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node_property
    ADD CONSTRAINT node_property_node_fk FOREIGN KEY (node_id) REFERENCES node(node_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: node_property_property_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node_property
    ADD CONSTRAINT node_property_property_fk FOREIGN KEY (property_id) REFERENCES vo_property(vo_property_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: node_view_node_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node_view
    ADD CONSTRAINT node_view_node_fk FOREIGN KEY (node_id) REFERENCES data_node(node_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: node_view_view_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY node_view
    ADD CONSTRAINT node_view_view_fk FOREIGN KEY (view_id) REFERENCES vo_view(vo_view_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: protocol_param_protocol_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY protocol_param
    ADD CONSTRAINT protocol_param_protocol_fk FOREIGN KEY (vo_protocol_id) REFERENCES vo_protocol(vo_protocol_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: share_group_group_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY share_group
    ADD CONSTRAINT share_group_group_fk FOREIGN KEY (group_id) REFERENCES groups(group_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: share_group_node_property_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY share_group
    ADD CONSTRAINT share_group_node_property_fk FOREIGN KEY (node_property_id) REFERENCES node_property(node_property_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: share_user_node_property_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY share_user
    ADD CONSTRAINT share_user_node_property_fk FOREIGN KEY (node_property_id) REFERENCES node_property(node_property_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: share_user_user_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY share_user
    ADD CONSTRAINT share_user_user_fk FOREIGN KEY (user_id) REFERENCES users(user_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: structured_data_node_id_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY structured_data_node
    ADD CONSTRAINT structured_data_node_id_fk FOREIGN KEY (node_oid) REFERENCES data_node(node_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: unstructured_data_node_id_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY unstructured_data_node
    ADD CONSTRAINT unstructured_data_node_id_fk FOREIGN KEY (node_oid) REFERENCES data_node(node_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: user_group_group_id_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY user_group
    ADD CONSTRAINT user_group_group_id_fk FOREIGN KEY (group_id) REFERENCES groups(group_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: user_group_user_id_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY user_group
    ADD CONSTRAINT user_group_user_id_fk FOREIGN KEY (user_id) REFERENCES users(user_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: user_node_property_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY user_node_property
    ADD CONSTRAINT user_node_property_fk FOREIGN KEY (node_property_oid) REFERENCES node_property(node_property_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: view_param_view_fk; Type: FK CONSTRAINT; Schema: vos; Owner: postgres
--

ALTER TABLE ONLY view_param
    ADD CONSTRAINT view_param_view_fk FOREIGN KEY (vo_view_id) REFERENCES vo_view(vo_view_oid) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

