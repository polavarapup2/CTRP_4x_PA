alter table study_site_accrual_status add comments varchar(2000);
alter table study_site_accrual_status add deleted bool NOT NULL DEFAULT false;