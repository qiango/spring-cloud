#!/bin/bash
mvn package

dockerImageName=zuul-image
dockerContainerName=zuul-contain
dockerContainerPort=9020


docker stop $dockerContainerName
docker rm $dockerContainerName
docker rmi $dockerImageName

docker build -t $dockerImageName .

docker run -e JAVA_OPTS='-Xmx512m' --name $dockerContainerName -it -p ${dockerContainerPort}:9020 -d $dockerImageName