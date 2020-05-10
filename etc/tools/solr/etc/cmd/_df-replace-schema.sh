#!/bin/sh

for file in `\find ../../../src/main/resources/solr/schema -maxdepth 1 -name '*.xml'`; do
    schema_name=`basename $file`
    schema_name=${schema_name/solr_schema_/}
    schema_name=${schema_name%.*}
    echo "schema $schema_name"
    curl http://localhost:8983/solr/admin/cores?action=unload\&core=${schema_name} > /dev/null 2>&1
    docker exec -u root -it ${DOKER_NAME_PREFIX}solr rm -f -r /opt/solr/server/solr/${schema_name}/
    docker cp ./schema/ ${DOKER_NAME_PREFIX}solr:/opt/solr/server/solr/${schema_name}
    docker cp $file ${DOKER_NAME_PREFIX}solr:/opt/solr/server/solr/${schema_name}/conf/schema.xml
    docker exec -u root -it ${DOKER_NAME_PREFIX}solr chown solr:solr /opt/solr/server/solr/${schema_name}/
    docker exec -u solr -it ${DOKER_NAME_PREFIX}solr mkdir -p /opt/solr/server/solr/${schema_name}/data
    curl http://localhost:8983/solr/admin/cores?action=create\&name=${schema_name}
    curl "http://localhost:8983/solr/${schema_name}/update?commit=true&indent=true&separator=%09&encapsulator=%09&f.MEMBER_INT_LIST.split=true&f.MEMBER_INT_LIST.separator=%2C" --data-binary @data/${schema_name}.tsv -H 'Content-Type: text/csv'
done
