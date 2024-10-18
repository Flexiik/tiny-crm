#! /bin/bash
/usr/local/bin/aws s3api get-object --bucket mini-crm --key mini-crm-backend-0.0.1-SNAPSHOT.jar mini-crm.jar
sudo kill $(ps aux | grep mini-crm | head -n 1 | cut -d' ' -f3)
java -jar mini-crm.jar &
