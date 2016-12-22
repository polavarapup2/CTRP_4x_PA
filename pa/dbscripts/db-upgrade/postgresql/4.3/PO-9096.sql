insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties), 'twitter.trials.complete.basetext',
    'A new NCI-sponsored {hashtags} study is now accepting patients. {url}');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties), 'twitter.trials.industrial.basetext',
    'A new NCI-sponsored {hashtags} study is now accepting patients. {url}');
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties), 'twitter.trials.url',
    'http://www.cancer.gov/clinicaltrials/{nctid}');  
insert into PA_PROPERTIES values ((select max(identifier) + 1 from pa_properties), 'twitter.trials.scan.schedule','0 0 7 * * ?');   

alter table anatomic_sites add column twitter_hashtags varchar;

update anatomic_sites set twitter_hashtags='anuscancer,analcancer' where code='Anus';
update anatomic_sites set twitter_hashtags='bonecancer' where code='Bones and Joints';
update anatomic_sites set twitter_hashtags='braincancer' where code='Brain and Nervous System';
update anatomic_sites set twitter_hashtags='breastcancer' where code='Breast - Female';
update anatomic_sites set twitter_hashtags='malebreastcancer' where code='Breast - Male';
update anatomic_sites set twitter_hashtags='cervixcancer,cervicalcancer' where code='Cervix';
update anatomic_sites set twitter_hashtags='coloncancer,colorectalcancer' where code='Colon';
update anatomic_sites set twitter_hashtags='uteruscancer,uterinecancer' where code='Corpus Uteri';
update anatomic_sites set twitter_hashtags='esophaguscancer' where code='Esophagus';
update anatomic_sites set twitter_hashtags='eyecancer' where code='Eye and Orbit';
update anatomic_sites set twitter_hashtags='hodgkinslymphoma' where code='Hodgkin''s Lymphoma';
update anatomic_sites set twitter_hashtags='kaposisarcoma' where code='Kaposi''s Sarcoma';
update anatomic_sites set twitter_hashtags='kidneycancer' where code='Kidney';
update anatomic_sites set twitter_hashtags='larynxcancer,laryngealcancer,throatcancer' where code='Larynx';
update anatomic_sites set twitter_hashtags='leukemia,bloodcancer' where code='Leukemia, not otherwise specified';
update anatomic_sites set twitter_hashtags='leukemia,bloodcancer' where code='Leukemia, other';
update anatomic_sites set twitter_hashtags='oralcancer,mouthcancer' where code='Lip, Oral Cavity and Pharynx';
update anatomic_sites set twitter_hashtags='livercancer' where code='Liver';
update anatomic_sites set twitter_hashtags='lungcancer' where code='Lung';
update anatomic_sites set twitter_hashtags='lymphoma,lymphaticcancer' where code='Lymphoid Leukemia';
update anatomic_sites set twitter_hashtags='melanoma,skincancer' where code='Melanoma, Skin';
update anatomic_sites set twitter_hashtags='multiplemyeloma,myeloma' where code='Multiple Myeloma';
update anatomic_sites set twitter_hashtags='mycosisfungoides,tcelllymphoma' where code='Mycosis Fungoides';
update anatomic_sites set twitter_hashtags='monocyticleukemia' where code='Myeloid and Monocytic Leukemia';
update anatomic_sites set twitter_hashtags='nonhodgkinslymphoma,nonhodgkins,nonhodgkin' where code='Non-Hodgkin''s Lymphoma';
update anatomic_sites set twitter_hashtags='gastrointestinalcancer,gicancer' where code='Other Digestive Organ';
update anatomic_sites set twitter_hashtags='adrenalcancer,testicularcancer' where code='Other Endocrine System';
update anatomic_sites set twitter_hashtags='femalecancer' where code='Other Female Genital';
update anatomic_sites set twitter_hashtags='hematologiccancers' where code='Other Hematopoietic';
update anatomic_sites set twitter_hashtags='penilecancer,testicularcancer' where code='Other Male Genital';
update anatomic_sites set twitter_hashtags='trachealcancer' where code='Other Respiratory/Intrathoracic Organs';
update anatomic_sites set twitter_hashtags='basalcellcarcinoma' where code='Other Skin';
update anatomic_sites set twitter_hashtags='renalcancer' where code='Other Urinary';
update anatomic_sites set twitter_hashtags='ovariancancer' where code='Ovary';
update anatomic_sites set twitter_hashtags='pancreaticcancer' where code='Pancreas';
update anatomic_sites set twitter_hashtags='prostatecancer' where code='Prostate';
update anatomic_sites set twitter_hashtags='rectalcancer' where code='Rectum';
update anatomic_sites set twitter_hashtags='smallintestinecancer' where code='Small Intestine';
update anatomic_sites set twitter_hashtags='sarcoma,softtissuesarcoma' where code='Soft Tissue / Sarcoma';
update anatomic_sites set twitter_hashtags='stomachcancer' where code='Stomach';
update anatomic_sites set twitter_hashtags='thyroidcancer' where code='Thyroid';
update anatomic_sites set twitter_hashtags='bladdercancer' where code='Urinary Bladder';