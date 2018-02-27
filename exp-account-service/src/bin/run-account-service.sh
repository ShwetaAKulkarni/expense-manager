#!/bin/bash
cmd=$0
bin_home=$(dirname ${cmd})
if [[ ${bin_home} == '.' ]]; then
  bin_home=$(pwd)
fi
app_home=$(dirname ${bin_home})
echo "Running the command from :: "${app_home}

nohup java -Dspring.profiles.active=prod -jar ${bin_home}/exp-account-service-0.0.1-SNAPSHOT.jar > /tmp/service.log 2> /tmp/server_err.log < /dev/null &
echo "command ran "