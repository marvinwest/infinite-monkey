@echo off

set CURRENT_DIRECTORY=%CD%

echo Delete old jar if present..
DEL %CURRENT_DIRECTORY%\docker\infinite-monkey-0.0.1-SNAPSHOT.jar

echo Create new jar..
cd %CURRENT_DIRECTORY%\backend 
call mvnw clean package -DskipTests

echo Copy jar to docker folder..
copy %CURRENT_DIRECTORY%\backend\target\infinite-monkey-0.0.1-SNAPSHOT.jar %CURRENT_DIRECTORY%\docker

echo New deployment to Docker..
cd %CURRENT_DIRECTORY%\docker
docker-compose down
docker rmi infinite-monkey:latest
docker-compose up