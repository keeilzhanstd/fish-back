### DEV:
`git clone -b dev git@github.com:keeilzhanstd/fish-back.git fish-back-dev`

### QA:
`git clone -b qa git@github.com:keeilzhanstd/fish-back.git fish-back-qa`

### PROD:
`git clone -b prod git@github.com:keeilzhanstd/fish-back.git fish-back-prod`

## Run locally
You need docker installed and running on your machine  

### ARGS:  

* `SERVER_PORT` to specify port on which API will run.

### Commands:  
`docker-compose build --build-arg SERVER_PORT=8080`  
`docker-compose up`
