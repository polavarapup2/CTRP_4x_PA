--insert modified definations with suggestions from Ruth
delete from misc_documents where name in ('TrialCategory.ExternallyPeerReviewed','TrialCategory.Industrial','TrialCategory.Institutional','TrialCategory.National');

INSERT INTO misc_documents (name,application,version,expiration_date,content) VALUES
('TrialCategory.National','Registry','01',null,
'NCI National Clinical Trials Network (NCTN) and other NIH-supported National Trial Networks.<br>');


INSERT INTO misc_documents (name,application,version,expiration_date,content) VALUES 
('TrialCategory.ExternallyPeerReviewed','Registry','01',null,
E'R01s, SPORES, U01s, U10s, P01s, CTEP, or any other clinical research study mechanism supported by the NIH or organizations on this list:<a href=''http://cancercenters.cancer.gov/documents/PeerReviewFundingOrganizations508C.pdf''> Organizations with Peer Review Funding Systems.</a>');


INSERT INTO misc_documents (name,application,version,expiration_date,content) VALUES
('TrialCategory.Institutional','Registry','01',null,
'In-house clinical research studies authored or co-authored by Cancer Center investigators and undergoing scientific peer review solely by the Protocol Review and Monitoring System of the Cancer Center. The Cancer Center investigator has primary responsibility for conceptualizing, designing, and implementing the clinical research study and reporting results.<ul>  <LI> It is acceptable for industry and other entities to provide support (such as drug, device or other funding), but the trial should clearly be the intellectual product of the center investigator.</LI><LI>This category may also include: <ul><li> Institutional studies authored and implemented by investigators at another Center in which your Center is participating.</LI><LI> Multi-Institutional studies authored and implemented by investigators at your Center.(Note: National and externally peer-reviewed studies should be listed with those categories, not as Institutional studies.)</LI></ul></LI></ul>');


INSERT INTO misc_documents (name,application,version,expiration_date,content) VALUES
('TrialCategory.Industrial','Registry','01',null,
'A pharmaceutical company controls the design and implementation of these clinical research studies.');


