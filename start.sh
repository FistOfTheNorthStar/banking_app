docker-compose down
docker rmi $(docker images -q 'banking_app*')
docker-compose up --build

