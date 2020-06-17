#!/bin/bash
mvn package

dockerImageName=feign-image-9031
dockerContainerName=feign-contain-9031
dockerContainerPort=9031


docker stop $dockerContainerName
docker rm $dockerContainerName
docker rmi $dockerImageName

docker build -t $dockerImageName .

docker run -e JAVA_OPTS='-Xmx512m' --name $dockerContainerName -it -p ${dockerContainerPort}:9031 -d $dockerImageName