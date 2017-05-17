#!/bin/bash

if [ $# != 3 ] 
  then echo
  echo Usage:
  echo
  echo $0 [PAYARA_HOME] [DOMAIN] [PORT_BASE];
  echo
    echo For example:
    echo $0 /c/dev/payara-4.1.164 application 10400;
    echo This means that the http port of the application will be 10480 and the admin port will be 10448
    echo
  exit;
fi

echo running with:
echo PAYARA_HOME=$1
echo DOMAIN=$2
echo PORT_BASE=$3

PAYARA_HOME=$1
DOMAIN=$2
PORT_BASE=$3
ADMIN_PORT=$(($PORT_BASE + 48))
WEB_PORT=$(($PORT_BASE + 80))
PATH=$PAYARA_HOME/bin:$PATH
SERVERURL=localhost\:$WEB_PORT

asadmin stop-domain $DOMAIN
asadmin delete-domain $DOMAIN
asadmin create-domain --user admin --nopassword --portbase $PORT_BASE $DOMAIN

curl https://jdbc.postgresql.org/download/postgresql-9.4.1212.jar > $PAYARA_HOME/glassfish/domains/$DOMAIN/lib/postgresql-9.4.1212.jar

asadmin -p $ADMIN_PORT start-domain $DOMAIN

#Create example Pool pool as Datasource so transactions are handled correctly
asadmin -p $ADMIN_PORT delete-jdbc-connection-pool --cascade examplePool
asadmin -p $ADMIN_PORT create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.DataSource --property portNumber=5432:password=example:user=example:serverName=localhost:databaseName=example examplePool
asadmin -p $ADMIN_PORT create-jdbc-resource --connectionpoolid examplePool jdbc/DataSource

asadmin -p $ADMIN_PORT create-custom-resource --restype java.lang.String --factoryclass org.glassfish.resources.custom.factory.PrimitivesAndStringFactory --property value='localhost' configuration/serverUrl

asadmin -p $ADMIN_PORT restart-domain $DOMAIN
