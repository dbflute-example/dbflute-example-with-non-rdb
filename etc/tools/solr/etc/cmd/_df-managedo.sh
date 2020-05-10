#!/bin/bash

NATIVE_PROPERTIES_PATH=$1

FIRST_ARG=$2
SECOND_ARG=$3
taskReturnCode=0

if [ "$FIRST_ARG" = "0" ];then
  FIRST_ARG=replace-schema
elif [ "$FIRST_ARG" = "97" ];then
  FIRST_ARG=help
fi

if [ "$FIRST_ARG" = "replace-schema" ];then
  echo "/nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"
  echo "...Calling the ReplaceSchema task"
  echo "nnnnnnnnnn/"
  sh $DBFLUTE_HOME/etc/cmd/_df-replace-schema.sh $NATIVE_PROPERTIES_PATH $SECOND_ARG
  taskReturnCode=$?

elif [ "$FIRST_ARG" = "help" ];then
  echo " [DB Change] => after changing database, with replacing your database"
  echo "   0 : replace-schema => drop tables and re-create schema (needs settings)"
  echo ""
  echo " [Utility] => various tasks"
  echo "  97 : help    => show description of tasks"
  taskReturnCode=0

fi

if [ $taskReturnCode -ne 0 ];then
  exit $taskReturnCode;
fi
