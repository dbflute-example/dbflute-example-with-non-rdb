#!/bin/sh

cd `dirname $0`

. ./_project.sh
docker-compose up -d
docker ps
