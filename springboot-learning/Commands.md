Spring boot commands
-----------------

mvn spring-boot:run 

mvn spring-boot:run -Dspring-boot.run.profiles=dev

mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev

mvn clean spring-boot:run -Dspring-boot.run.profiles=dev

docker build -t booting .



Docker in local mac using colima and buildx
---------------------------------------------

brew install docker colima
colima start or  colima start --cpu 4 --memory 8
docker context use colima (one time)
docker info
docker build -t booting .
colima stop


exec $SHELL (first time or else open a new terminal)
docker buildx version
docker buildx create --name colima-builder --use
docker buildx build --platform=linux/arm64 --load -t booting .
docker run -p 8080:8080 -d booting
docker ps
docker stop <id> 
