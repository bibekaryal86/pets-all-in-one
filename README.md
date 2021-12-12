# pets-all-in-ne
Maven / Gradle Multi Module Single PETS app

This app is one monolith app which combines most of the functionalities of the following five apps:
* https://github.com/bibekaryal86/pets-database-layer
* https://github.com/bibekaryal86/pets-service-layer
* https://github.com/bibekaryal86/pets-authenticate-layer
* https://github.com/bibekaryal86/pets-gateway-layer
* https://github.com/bibekaryal86/pets-ui-layer

Instead of separate apps for database, business, authenticate, gateway and ui layers, the separate logic are placed in different modules - database, service, ui, and a common module for model/pojos. There is no need for gateway and authenticate layers which are handled within this single app.

THe app does not have Spring Boot starter, so the generated war does not have embedded container. Hence the app needs to be loaded to outside container (Tomcat/Jetty) to run.

To run the app, first create WAR file:
* For WAR file to upload to Google Cloud Project, use `mvn clean package`
* For WAR file to upload to AWS Elastic Beanstalk or docker or locally, use `mvn clean package -P development`. This excludes GCP related dependencies.

When running the app, we need to supply the following environment variables to the container:
* Active Profile:
  * spring.profiles.active (development, gcp, aws)
* Log Config for logfile location:
  * LOG_CONFIG (development, gcp, aws)
* MongoDb Database Details:
  * MONGODB_ACC_NAME (account name of database)
  * MONGODB_USR_NAME (database username)
  * MONGODB_USR_PWD (database password)

The App can be run locally or AWS or GCP. To run in AWS, upload WAR file, set the environment variables, and that's it. The .ebextensions folder in src/main/webapp contains configurations for viewing logs in the AWS UI. To run in GCP App Engine, use the GCP plugin for easy way, the environment variables are set in the appengine-web.xml.

Deployed to:
* AWS: http://petsuimpa.us-east-1.elasticbeanstalk.com/home.pets
* GCP: http://petsuimpa.appspot.com/home.pets

# best to use war file from gradle for local/AWS and keep maven configuration for GCP
