update pa_properties set value = replace(value, 'http://clinicaltrials.gov', 'https://clinicaltrials.gov')
    where name in ('ctgov.api.getByNct', 'ctgov.api.searchByTerm');