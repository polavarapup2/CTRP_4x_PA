IDE Setup

For Eclipse, install plugins for Maven, PMD, and Checkstyle. Having PMD and Checkstyle plugins will help detect static code analysis problems earlier, before the build does. 

Set up the following projects in your IDE:
- pa
- reg-web
- accrual
- pa-grid-3.4 (recommended, but optional)

Eclipse classpath variables:
- PA_LIB points to code\target\pa\lib.
- ACCRUAL_LIB points to code\target\accrual\lib

To satisfy Checkstyle:

- Disable tabs:
        Select Windows > General > Editor > Text Editor >
            Displayed tab width = 4
            check insert spaces for tab
            check show print margin
            set print margin column as 120
			
- Modify Java Code Formatter to use spaces instead of tabs as well.


Ant Commands

"ant deploy-notest" will rebuild and redeploy EAR into JBoss quickly without running tests
"ant deploy" will run all tests for the given module (pa, reg-web or accrual) and redeploy EAR in JBoss.
"ant database.install database.update populate-test-db" in pa will DROP your PA database and re-initialize it for Selenium tests (see below).


Pre-commit Check

- svn update
- "ant -f build-lite.xml test" in build-pa must pass cleanly before committing. 

Selenium tests are excluded from pre-commit check because they take a very long time to run. 


Functional Tests (Selenium)

The following commands WILL DROP and re-create your PA database!

- Modify build-pa\tier-properties\build-lite-LOCAL.properties: set mock.po=true. PA will use a in-memory PO mock rather than calling PO's Remote EJBs.
- On Windows, ensure psexec.exe is available on your PATH. This EXE is used to start a JBoss process.
- Stop PO & PA JBoss, if running.
- Run "ant test-integration". This will drop, re-create and re-initialize the database, deploy PA into JBoss, start JBoss, run Selenium tests in Firefox, and stop JBoss.

You will surely want to set up your IDE to run Selenium tests as well. Use this command to re-initialize PA database for Selenium tests: "ant database.install database.update populate-test-db". This does not have to be run before each test, but if you're running into odd data issues with tests, most likely the database needs to be refreshed. Set mock.po=true, do "ant deploy-notest", and start JBoss. You can now run Selenium tests from your IDE, such as Eclipse.

JRebel

JRebel greatly reduces deployment overhead for Java EE applications, such as PO & PA and is a must for CTRP developers. Please install and configure. 


