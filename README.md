### DEV:
`git clone -b dev git@github.com:keeilzhanstd/fish-back.git`

### QA:
`git clone -b qa git@github.com:keeilzhanstd/fish-back.git`

### PROD:
`git clone -b prod git@github.com:keeilzhanstd/fish-back.git`

## Run locally

`SERVER_PORT` to specify port on which API will run.

`docker-compose build --build-arg SERVER_PORT=8080`
`docker-compose up`
