#!/bin/sh

app_name=cm-assists
docker_registry=docker.craftsmen.org.cn
user=admin
password=jr2015
remote_docker=ms.ddkm18.com:61028

# image exist
image=`docker images | grep $docker_registry/$app_name | awk 'NR==1{print $3}'`
if [ -n "$image" ]; then
     echo "Image $image exist, remove it now."
     docker rmi $image
fi

# build docker image
docker build -t $docker_registry/$app_name .

# push image to registry
docker login -u $user -p $password $docker_registry
docker push $docker_registry/$app_name


### undeploy
container=`docker -H $remote_docker ps | grep $app_name | awk 'NR==1{print $1}'`
# container is running
if [ -n "$container" ]; then
     echo "Container $container is running, stop it now."
     docker -H $remote_docker stop $container
fi

# container exist
container=`docker -H $remote_docker ps -a | grep $app_name | awk 'NR==1{print $1}'`
if [ -n "$container" ]; then
     echo "Container $container is exist, remove it now."
     docker -H $remote_docker rm $container
fi

# image exist
image=`docker -H $remote_docker images | grep $docker_registry/$app_name | awk 'NR==1{print $3}'`
if [ -n "$image" ]; then
     echo "Image $image exist, remove it now."
     docker -H $remote_docker rmi $image
fi

# run app in remote docker
docker -H $remote_docker run --name $app_name -e TZ="Asia/Shanghai" --add-host reg.ddkm18.com:10.26.43.75 --link mongo --link mysql -p 8081:8081 -d --restart=always $docker_registry/$app_name