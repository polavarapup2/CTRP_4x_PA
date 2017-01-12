--Author   : Reshma Koganti
--Date     : 10/05/2012        
--Jira#    : PO-5761Biomarker - Start capturing caDSR public IDs in CTRP db 
--Comments :  Adding a column to planned marker. Inserting the sync id values in planned marker table.  
ALTER TABLE PLANNED_MARKER ADD COLUMN PM_SYNC_IDENTIFIER BIGINT;

ALTER TABLE PLANNED_MARKER ADD CONSTRAINT FK_PLANNED_MARKER_SYNC_ID
FOREIGN KEY (PM_SYNC_IDENTIFIER) REFERENCES PLANNED_MARKER_SYNC_CADSR (IDENTIFIER)
ON DELETE CASCADE;