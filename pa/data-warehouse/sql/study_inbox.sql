DROP TABLE IF EXISTS stg_dw_study_inbox;

CREATE TABLE stg_dw_study_inbox
(
  id integer NOT NULL,
  nci_id character varying(255) NOT NULL,
  comments character varying(5000),
  open_date timestamp without time zone NOT NULL,
  close_date timestamp without time zone,
  type_code character varying(16) NOT NULL,
  admin boolean,
  scientific boolean,
  admin_close_date timestamp without time zone,
  scientific_close_date timestamp without time zone,
  PRIMARY KEY (id)
);
--Create history table
CREATE TABLE IF NOT EXISTS HIST_DW_study_inbox
(
 RUN_ID TIMESTAMP,
  id integer NOT NULL,
  nci_id character varying(255) NOT NULL,
  comments character varying(5000),
  open_date timestamp without time zone NOT NULL,
  close_date timestamp without time zone,
  type_code character varying(16) NOT NULL,
  admin boolean,
  scientific boolean,
  admin_close_date timestamp without time zone,
  scientific_close_date timestamp without time zone
);