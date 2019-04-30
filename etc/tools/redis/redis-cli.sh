#!/bin/sh

. ./_project.sh
docker exec -i -t ${DOKER_NAME_PREFIX}redis redis-cli
