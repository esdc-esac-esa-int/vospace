cd /home/eacsops/eacsComponents/esavo-vospace-querymanager-X.x
nohup /usr/local/jdk1.7.0_67/jre/bin/java -Xmx512m \
-cp conf/:lib/esavo-vospace-querymanager-X.x.jar:lib/absi-common-countryresolver-module-0.1.1.jar:lib/absi-common-entitymodel-api-0.6.jar:lib/absi-common-model-api-0.6.4.jar:lib/absi-common-querybean-module-1.1.jar:lib/absi-common-resultbean-api-0.5.jar:lib/absi-common-resultbean-module-0.5.2.jar:lib/absi-common-retrievalbean-module-0.7.1.jar:lib/absi-common-retrievalresultbean-module-0.2.jar:lib/absi-common-skycoords-module-0.1.1.jar:lib/absi-common-transferobjects-api-0.9.jar:lib/absi-dl-dao-api-3.0.1.jar:lib/absi-dl-querybuilder-module-3.4.jar:lib/absi-dl-querymanager-api-2.3.jar:lib/absi-ifsd-model-api-1.6.jar:lib/activation-1.1.jar:lib/antlr-2.7.7.jar:lib/aopalliance-1.0.jar:lib/c3p0-0.9.1.jar:lib/commons-beanutils-1.8.3.jar:lib/commons-lang-2.5.jar:lib/commons-logging-1.1.1.jar:lib/dom4j-1.6.1.jar:lib/dozer-5.3.2.jar:lib/ecs-1.4.2.jar:lib/ehcache-core-2.4.3.jar:lib/esavo-vospace-dao-0.6.oper.jar:lib/esavo-vospace-entitymodel-0.4.jar:lib/esavo-vospace-exceptions-0.2.jar:lib/esavo-vospace-ifsd-0.4.jar:lib/esavo-vospace-model-0.6.jar:lib/hibernate-annotations-3.5.3-Final.jar:lib/hibernate-c3p0-4.2.16.Final.jar:lib/hibernate-commons-annotations-4.0.2.Final.jar:lib/hibernate-commons-annotations-4.0.1.Final.jar:lib/hibernate-core-4.2.16.Final.jar:lib/hibernate-ehcache-4.2.16.Final.jar:lib/hibernate-jpa-2.0-api-1.0.1.Final.jar:lib/jasypt-1.9.2.jar:lib/jasypt-hibernate4-1.9.2.jar:lib/javassist-3.18.1-GA.jar:lib/jboss-logging-3.1.0.GA.jar:lib/jboss-transaction-api_1.1_spec-1.0.1.Final.jar:lib/log4j-1.2.16.jar:lib/mail-1.4.1.jar:lib/maxmindgeoip-1.2.2.jar:lib/postgresql-9.2-1000.jdbc4.jar:lib/serializer-2.7.1.jar:lib/slf4j-api-1.6.1.jar:lib/slf4j-log4j12-1.6.1.jar:lib/spring-aop-3.2.6.RELEASE.jar:lib/spring-beans-3.2.6.RELEASE.jar:lib/spring-context-3.2.6.RELEASE.jar:lib/spring-core-3.2.6.RELEASE.jar:lib/spring-expression-3.2.6.RELEASE.jar:lib/spring-jdbc-3.2.6.RELEASE.jar:lib/spring-orm-3.2.6.RELEASE.jar:lib/spring-tx-3.2.6.RELEASE.jar:lib/xalan-2.7.1.jar:lib/xercesImpl-2.9.1.jar:lib/xml-apis-1.3.04.jar:lib/xmlParserAPIs-2.6.2.jar  \
esavo.vospace.dl.querymanager.QueryManager &
exit 0