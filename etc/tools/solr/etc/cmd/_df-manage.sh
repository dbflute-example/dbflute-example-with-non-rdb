#!/bin/bash

NATIVE_PROPERTIES_PATH=$1

FIRST_ARG=$2
SECOND_ARG=$3
taskReturnCode=0

if [ "$FIRST_ARG" = "" ];then
  echo "     |\  |-\ |-- |      |      "
  echo "     | | |-\ |-  | | | -+- /_\ "
  echo "     |/  |-/ |   | |_|  |  \-  "
  echo ""
  echo " <<< DB Change >>> *delete database"
  echo "   0 : replace-schema  => drop tables and create schema"
  echo ""
  echo " <<< Utility >>>"
  echo "   97 : help"
  echo ""
  echo \(input on your console\)
  echo What is your favorite task? \(number\):

  read FIRST_ARG
fi

# you can specify plural tasks by comma string
#  e.g. manage.sh 21,22
IFS=,
for element in $FIRST_ARG
do
  sh $DBFLUTE_HOME/etc/cmd/_df-managedo.sh $NATIVE_PROPERTIES_PATH $element $SECOND_ARG
  taskReturnCode=$?
  if [ $taskReturnCode -ne 0 ];then
    exit $taskReturnCode;
  fi
done
