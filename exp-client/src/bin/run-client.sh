#!/bin/bash
cmd=$0
bin_home=$(dirname ${cmd})
if [[ ${bin_home} == '.' ]]; then
  bin_home=$(pwd)
fi
app_home=$(dirname ${bin_home})
echo "Running the command from :: "${app_home}

java -Dspring.profiles.active=prod -jar ${bin_home}/exp-client-0.0.1-SNAPSHOT.jar &