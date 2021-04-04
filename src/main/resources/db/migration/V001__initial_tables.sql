create sequence if not exists patient_id_seq;
create table if not exists patient
(
	id                  BIGSERIAL  primary key    not null,
	external_reference  uuid CONSTRAINT unique_external_reference UNIQUE NOT NULL,
	name                text,
	address             text,
	created TIMESTAMP NOT NULL,
	updated TIMESTAMP
);

COMMENT ON COLUMN patient.name IS 'Patient name recorded during registration';
COMMENT ON COLUMN patient.address IS 'Patient address entered during registration';
COMMENT ON COLUMN patient.external_reference IS 'Patient external reference that can be shared externally';
COMMENT ON COLUMN patient.created IS 'Time when the record was created (UTC)';
COMMENT ON COLUMN patient.updated IS 'Time when the record was last updated (UTC)';