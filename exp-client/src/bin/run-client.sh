#!/bin/bash
cmd=$0
bin_home=$(dirname ${cmd})
if [[ ${bin_home} == '.' ]]; then
  bin_home=$(pwd)
fi

java -Dspring.profiles.active=prod -jar ${bin_home}/exp-client-0.0.1-SNAPSHOT.jar > /tmp/client.log 2> /tmp/client_err.log < /dev/null &
echo "command ran"