# CTRP_4x_PA
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/94d257b9684a44d99ba806af952e481c)](https://www.codacy.com/app/FNLCR/CTRP_4x_PA?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=CBIIT/CTRP_4x_PA&amp;utm_campaign=Badge_Grade)

Requirements:
-------------

* Ant 1.8 or higher

* Maven 2.2 or higher

* Java 7

Build Commands
-------------

Ant Commands

"ant deploy-notest" will rebuild and redeploy EAR into JBoss quickly without running tests
"ant deploy" will run all tests for the given module (pa, reg-web or accrual) and redeploy EAR in JBoss.
"ant database.install database.update populate-test-db" in pa will DROP your PA database and re-initialize it for Selenium tests (see below).



External Dependencies
--------------------- 