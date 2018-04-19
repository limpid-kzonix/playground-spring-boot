#!/bin/sh
echo 'Updating backend ...';
rm -rf /var/prod/omnie-commerce-parent
cd /var/prod/
sudo git clone -b master --single-branch http://git.omniecom.com/omniesoft/omnie-commerce-parent.git
cd /var/prod/omnie-commerce-parent
mvn clean package install
cd /var/prod/ 
docker-compose build
docker-compose stop
docker-compose up -d
echo 'Updating backend DONE';
