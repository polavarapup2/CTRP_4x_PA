--Author   : Reshma Koganti
--Date     : 7/15/2014        
--Jira#    : PO-7263 PA: Increase size of check-in comment box 

ALTER TABLE study_checkout alter COLUMN checkin_comment  TYPE character varying(4000); 