--Author   : Gopalakrishnan Unnikrishnan
--Date     : 8/14/2014        
--Jira#    : PO-7857 Further normalize INTERVENTION table to split intervention types

ALTER TABLE intervention ALTER COLUMN type_code DROP NOT NULL;