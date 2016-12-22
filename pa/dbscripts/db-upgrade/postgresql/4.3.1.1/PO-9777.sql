CREATE TABLE webservice_access_log
(
  identifier serial NOT NULL,
  datetime timestamp without time zone,
  client_ip text,
  client_username text,
  uri text,
  method text,
  headers text,
  payload text,
  response text,
  response_code integer,
  processing_time bigint,
  processing_errors text,
  CONSTRAINT webservice_access_log_pk PRIMARY KEY (identifier)
);
