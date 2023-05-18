# Running

#### DEV:
```console
git clone -b dev git@github.com:keeilzhanstd/fish-back.git fish-back-dev
cd fish-back-dev 
```

Default server port: 8000  

#### QA:
```console
git clone -b qa git@github.com:keeilzhanstd/fish-back.git fish-back-qa 
cd fish-back-qa
```
Default server port: 8080

#### PROD:
```console
git clone -b prod git@github.com:keeilzhanstd/fish-back.git fish-back-prod
cd fish-back-prod
```
Default server port: 5000  

## Run locally
You need docker installed and running on your machine  

### ARGS:  

* `SERVER_PORT` to specify port on which API will run.  

```console
docker-compose build --build-arg SERVER_PORT=8080
docker-compose up
# note run in the directory with dockerfile
```


### Commands:  
run with default server port  
```console
docker-compose build 
docker-compose up
# note run in the directory with dockerfile
```
