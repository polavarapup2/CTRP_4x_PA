When updating to the next release version after branching or tagging make the following changes:          

1. Update (preferably from branch/tag) the pa-ejb-client and pa-commons jars (./pa/ant maven:deploy).
2. Update (preferably from branch/tag) the accrual client for old version (./accrual/ant maven:deploy).
3. Update build-pa/version.properties.
4. Deploy the pa-ejb-client and pa-commons jars (./pa/ant maven:deploy).
5. Deploy the accrual client (./accrual/ant maven:deploy).
6. Update the pa-grid pom with the new pa client snapshot jar (3 places).

