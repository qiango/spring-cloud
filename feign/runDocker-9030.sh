#!/bin/bash
mvn package

dockerImageName=feign-image-9030
dockerContainerName=feign-contain-9030
dockerContainerPort=9030


docker stop $dockerContainerName
docker rm $dockerContainerName
docker rmi $dockerImageName

docker build -t $dockerImageName .

docker run -e JAVA_OPTS='-Xmx512m' --name $dockerContainerName -it -p ${dockerContainerPort}:9030 -d $dockerImageName