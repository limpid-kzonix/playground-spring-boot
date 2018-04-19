#!/bin/sh
echo 'Updating frontend ...';
rm -rf /var/prod/angular4-omnie-web
cd /var/prod/
sudo git clone -b release --single-branch http://git.omniecom.com/omniesoft/angular4-omnie-web.git
echo 'Updating frontend DONE';