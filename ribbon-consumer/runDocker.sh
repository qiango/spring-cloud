#!/bin/bash
mvn package

dockerImageName=ribbon-image
dockerContainerName=ribbon-contain
dockerContainerPort=8886


docker stop $dockerContainerName
docker rm $dockerContainerName
docker rmi $dockerImageName

docker build -t $dockerImageName .

docker run -e JAVA_OPTS='-Xmx512m' --name $dockerContainerName -it -p ${dockerContainerPort}:8886 -d $dockerImageName