#!/bin/sh

touch ~/.m2/settings-security.xml
touch ~/.m2/settings.xml
cd ~/.m2/
echo '<settingsSecurity>
    <master>{mvn-encrypted-password}</master>
</settingsSecurity>' > settings-security.xml

echo '<settings>
    <servers>
        <server>
            <id>server.database</id>
            <username>postgres</username>
            <password>{ENCRYPTED-DB-PASSWORD}</password>
        </server>
    </servers>
</settings>' > settings.xml


echo '1. Generate secret password by:  mvn --encrypt-master-password'
echo '2. Copy result to ~/.m2/settings-security.xml  instead of {mvn-encrypted-password}'
echo '3. Encrypt db password by:  mvn --encrypt-password'
echo '4. Copy result to ~/.m2/settings.xml  instead of {ENCRYPTED-DB-PASSWORD}'