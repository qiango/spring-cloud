#!/bin/bash
mvn package

dockerImageName=ribbon-image-9010
dockerContainerName=ribbon-contain-9010
dockerContainerPort=9010


docker stop $dockerContainerName
docker rm $dockerContainerName
docker rmi $dockerImageName

docker build -t $dockerImageName .

docker run -e JAVA_OPTS='-Xmx512m' --name $dockerContainerName -it -p ${dockerContainerPort}:9010 -d $dockerImageName