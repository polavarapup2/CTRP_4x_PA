To set up development box:

Java
1. Install Java JDK 1.7.0
2. Set PATH and JAVA_HOME environment variables.

Ant
1. Install Ant 1.8.4
2. Set PATH and ANT_HOME environment variables.

Database
1. Install Postgres 9.2. Create a superuser and database to be used by CTRP-PA.
2. Use pg_dump to pull data from existing 8.4 database.
3. Use pg_restore to load data into new 9.2 database.
4. Run the following SQL on the database: update databasechangelog set md5sum = null, filename='/tmp/pa/db-upgrade.xml'

Application Server
1. Install JBoss EAP 6.2.0 GA.
2. Extract the file build-pa/server-config/ctrp-pa-modules.zip to the folder <jboss home>/modules. Add jars listed
   in the two module.xml file. Use https://drive.google.com/file/d/0B2AAJeDeGUVGWjkxbDZQM25xbjA/view?usp=sharing 
   to avoid the need for this second step.
3. Copy build-pa/server-config/standalone.xml to the folder <jboss home>/standalone/configuration.
4. Search for "java:/pa-ds and edit" the datasource with appropriate database name, port, user.
5. Search for the pa and accrual security domains and update database information. 
6. Set JAVA_OPTS environment variable with -Xms1024m -Xmx2048m -XX:MaxPermSize=2048m
7. For remote debugging also set -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n
8. Start server using <server home>/standalone.bat (or standalone.sh) script.
9. Check that server is running properly http://localhost:39480/

Build
1. Rename file build-pa/tier-properties/build-lite-LOCAL.properties.example to build-lite-LOCAL.properties.
2. Edit as appropriate for local configuration.	
3. Build applications with command build-pa/ant -f build-lite.xml dist
4. Check that the folders target/pa/dist and target/accrual/dist each contain an EAR and a tooltip file.
5. Test the applications with the command build-pa/ant -f build-lite.xml test
6. Test reports will be in target/pa/reports/**/* and target/accrual/reports/**/*

Deploy Locally
1. Shutdown JBoss
2. pa/ant deploy-notest
3. accrual/ant deploy-notest
4. Start JBoss

