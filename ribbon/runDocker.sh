#!/bin/bash
mvn package

dockerImageName=ribbon-image-9040
dockerContainerName=ribbon-contain-9040
dockerContainerPort=9040


docker stop $dockerContainerName
docker rm $dockerContainerName
docker rmi $dockerImageName

docker build -t $dockerImageName .

docker run -e JAVA_OPTS='-Xmx512m' --name $dockerContainerName -it -p ${dockerContainerPort}:90400 -d $dockerImageName