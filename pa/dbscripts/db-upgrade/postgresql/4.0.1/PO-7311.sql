--Author   : Dirk Wlter
--Date     : 05/02/2014        
--Jira#    : PO-7311 - Add dropdowns for organization to My Accounts.
--Comments : Adds common organizations table and populate it. Since the table is populated by name rather than ID there are no garantees of correctness and it might need tweaking, but ID numbers change between enviorments.
DROP TABLE IF EXISTS account_common_organizations;

CREATE TABLE account_common_organizations
(
  organization_id integer NOT NULL,
  CONSTRAINT pk_account_common_orgs PRIMARY KEY (organization_id ),
  CONSTRAINT accout_common_organizations_organization_id_fkey FOREIGN KEY (organization_id)
      REFERENCES organization (identifier) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO account_common_organizations
SELECT 
  organization.identifier
FROM 
  organization
WHERE (organization.status_code LIKE 'ACTIVE' or organization.status_code LIKE 'PENDING') and organization.name in (
'Johns Hopkins University',
'University of Wisconsin Hospital and Clinics',
'University of Michigan University Hospital',
'University of Virginia',
'Wayne State University-Karmanos Cancer Institute',
'Ohio State University Medical Center',
'Case Western Reserve University',
'City of Hope',
'University of California at Los Angeles (UCLA )',
'Vanderbilt-Ingram Cancer Center',
'Dana-Farber Cancer Institute',
'University of Southern California',
'Fox Chase Cancer Center',
'Northwestern University',
'Dartmouth Hitchcock Medical Center',
'Memorial Sloan-Kettering Cancer Center',
'University of Colorado',
'Abramson Cancer Center of The University of Pennsylvania',
'Huntsman Cancer Institute/University of Utah',
'St. Jude Children''s Research Hospital',
'University of California At San Diego',
'University of North Carolina',
'Duke University Medical Center',
'Thomas Jefferson University Hospital',
'University of Minnesota Medical Center-Fairview',
'University of Alabama at Birmingham',
'University of California Medical Center At Irvine-Orange Campus',
'M D Anderson Cancer Center',
'University of Rochester',
'Mayo Clinic',
'University of Arizona Health Sciences Center',
'New York University Langone Medical Center',
'University of Hawaii',
'University of Chicago',
'UCSF-Mount Zion',
'University of Nebraska Medical Center',
'University of Pittsburgh',
'University of Texas Health Science Center at San Antonio',
'Oregon Health and Science University',
'H. Lee Moffitt Cancer Center and Research Institute',
'Lombardi Comprehensive Cancer Center at Georgetown University',
'Fred Hutchinson Cancer Research Center UWCC');
