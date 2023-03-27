# ClashTasker - BIM Model Collision Conflict Management Tool #

ClashTasker is an application designed specifically for MEP engineers to easily manage and resolve collision conflicts in BIM models. The application works by generating a list of collision conflicts within the model, along with information about the department of the engineers involved and the element IDs from Autodesk Revit.

## Features
- Generate list of collision conflicts within the BIM model
- View information about engineer departments and element IDs from Autodesk Revit
- Assign engineers to specific conflicts for resolution
- Monitor progress in real-time
- Ability to automatically load a list of clashes exported from Autodesk Navisworks (coming soon)

## Stack
ClashTasker is built using the following technologies:
- Spring for the backend
- PostgreSQL for the database
- Java 11 for the programming language
- Maven for dependency management
- Apache Tomcat for the application server

## Installation
1. Clone the repository to your local machine:

   `git clone https://github.com/mitrofmep/clash-tasker.git`
2. Make sure you have Java 11, Apache Tomcat and PostgreSQL installed on your machine.
3. Set DB properties to `database.properties`. As example use `database.properties.origin`
4. Open a terminal and navigate to the root directory of the project.
5. Build the project using Maven by running the following command:

   `mvn clean package`
6. After a successful build, locate the `clash-tasker.war` file in the target directory.
7. Copy the `clash-tasker.war` file to the `webapps` directory of your Tomcat installation.
8. Start Tomcat by running the following command:

   `catalina.sh run`
9. Open a web browser and navigate to `http://localhost:8080/main` to access the application.

## Usage
1. Open the application
2. Upload your list of collisions from Autodesk Revit
3. Assign engineers to specific conflicts for resolution
4. Monitor progress in real-time

## Contributions
Contributions are welcome and encouraged! Please feel free to submit pull requests or open issues if you encounter any problems.

## License
This application is licensed under the MIT License.
