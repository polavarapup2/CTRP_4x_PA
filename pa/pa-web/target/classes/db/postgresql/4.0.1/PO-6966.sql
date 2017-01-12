--Author   : Reshma Koganti
--Date     : 3/12/2014        
--Jira#    : PO-6966 Treat updates to Keywords as a scientific change in PA 
 
update pa_properties set value=E'# If mapping not found, the field will be assumed from Admin section. Or say Admin explicitly here.\nstudyProtocol.scientificDescription=Scientific\neligibilityCriteria=Scientific\nstudyProtocol.publicTitle=Scientific\nstudyProtocol.publicDescription=Scientific\narms=Scientific\nstudyProtocol.keywordText=Scientific' where 
name='ctgov.sync.fields_of_interest.key_to_sect_mapping';