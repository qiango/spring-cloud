#!/bin/bash
mvn package

dockerImageName=ribbon-image-9011
dockerContainerName=ribbon-contain-9011
dockerContainerPort=9011


docker stop $dockerContainerName
docker rm $dockerContainerName
docker rmi $dockerImageName

docker build -t $dockerImageName .

docker run -e JAVA_OPTS='-Xmx512m' --name $dockerContainerName -it -p ${dockerContainerPort}:9011 -d $dockerImageName