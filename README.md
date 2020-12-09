# RRTools
A Griffon application (desktop Java) to keep track of your model railroad cars

This was written with Griffon, Java8, Groovy and JavaFX. You can build it with Gradle (use "Gradlew run" to run using the Gradle wrapper). Or, run it with with ShadowJar as "java -jar <jarname>" As this was built with Java8 and JavaFX, it MUST be run with Java8 as Java 11 does not include JavaFX. This application will use an H2 database on the "D:\" drive which must have the tables previously defined: the JDBC URL is jdbc:h2:file:D:/Projects/RailRoad_Database/Prod/prod;AUTO_SERVER=TRUE;SCHEMA=RR;MODE=DB2 H2 requires an absolute path name (relative path names with give an exception). There is a file in the distribution to create the required tables (Create_H2_DB.sql).
