#!/bin/sh

. ./_project.sh
docker exec -i -t ${DOKER_NAME_PREFIX}redis-cluster redis-cli -p 7000 -c
