#     Message Store

## How to run docker image
Run the following commands in sequence :
1) Make sure maven is installed.
2) Run the command: mvn clean package . This runs all integration and unit tests too.
3) docker build -t paxos .
4) docker run -p 8080:8080 --name messagestore paxos

## How to build and run application without Docker
1) Make sure maven is installed.
2) Run the command: mvn clean package . This runs all integration and unit tests too.
3) Run the command to start the server at default port 8080: java -jar target/messageStore-0.0.1-SNAPSHOT.jar
4) To change the port number, change the setting in application.properties file and recompile.

## What would the bottleneck(s) be in your implementation as you acquire more users? How you might scale your microservice?
1) More users will lead to more hits to the APIs. Single instance of server will not be sufficient. As the system is stateless, multiple
   instances can be deployed to cater to increasing needs.
2) Since each of the server will also be interacting to database, a single server instance of database becomes bottleneck too. Since the access pattern for the application is simple key-value based (query is always on message hash),
   use of a nosql database like Dynamodb or Cassandra is ideal for the use-case. The data will be sharded to multiple instances based on hash key and can easily scale as per demand.
3) A caching layer like Redis can be used as LRU or LFU cache as optimization for messages that are more frequently used.
4) Client side caching can be enabled for hot items based on the access rate. If the access rate for an item crosses a threshold, client can be hinted to cache the item locally.


## How would you improve your deployment process if you needed to maintain this application long term?
To ease continuous enhancements and deployments for the application, CI/CD pipeline can be enabled. The deployment pipeline would function like the following:
1) There will be an Orchestration service, which would be used to reserve resources for any new application based on the configuration provided to it. The configuration will be present in the Git project itself.
   The Orchestration service will take the git url for the project and necessary credentials to pull the configuration and prepare the infrastructure accordingly. Internally, Orchestration service can make
   use of the Marathon/Mesos as resource negotiator for the underlying cloud containers. Marathon also provides easy way to scale up/down the containers for application.
2) There will be another Deployment service, which will set up the deployment pipeline for the application. Deployment service will be triggered by Orchestration service.
3) Developer can kickoff the deployment by triggering the deployment pipeline for his application through the Deployment service. Deployment service will pull the code from git and use Jenkins to build a
   docker image, run tests and publish the docker image. The deployment service can now forward the docker image to Orchestration service to deploy the image to reserved containers.
4) The deployment pipeline will contain multiple stages - dev, test, prod, etc. Once the dev build is tested by the developer, he can forward it to test stage in the pipeline. Similarly, when QA is done with testing,
   production deployment can be initiated.