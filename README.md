# pets-ui-mpa-all
Maven / Gradle Multi Module Single PETS app

This combines the three apps from the following repositories into one monolith app:
* https://github.com/bibekaryal86/pets-database
* https://github.com/bibekaryal86/pets-service
* https://github.com/bibekaryal86/pets-ui-mpa

Instead of separate apps for database, business and ui layers, the separate logic are placed in different modules - database, service, ui, and a common module for model/pojos

This does not have Spring Boot starter, so the generated war does not have embedded container. Hence the app needs to be loaded to outside container (Tomcat/Jetty) to run.

To run the App: (1) From IDE, use Jetty plugin for Eclipse. (2) Create WAR file (mvn clean package OR mvn clean package -P development) and run from container (Tomcat/Jetty)

When running either way, the following environment variables need to be set - spring.profiles.active (development or production), LOG_CONFIG (development, gcp or aws), MONGODB_ACC_NAME (account name of database), MONGODB_USR_NAME (database username) and MONGODB_USR_PWD (database password). Spring profile is optional in the current state of the app.

The App can be run locally or AWS or GCP. To run in AWS, upload WAR file, set the environment variables, and that's it. The .ebextensions folder in src/main/webapp contains configurations for viewing logs in the AWS UI. To run in GCP App Engine, use the GCP plugin for easy way, the environment variables are set in the appengine-web.xml.

Deployed to:
* AWS: http://petsuimpa.us-east-1.elasticbeanstalk.com/home.pets
* GCP: http://petsuimpa.appspot.com/home.pets

# best to use war file from gradle for local/AWS and keep maven configuration for GCP
