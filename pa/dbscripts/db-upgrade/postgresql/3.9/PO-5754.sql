DELETE 
FROM icd9_disease 
WHERE disease_code = '151.5'
  AND identifier NOT IN (SELECT MIN(identifier) FROM icd9_disease GROUP BY disease_code);
