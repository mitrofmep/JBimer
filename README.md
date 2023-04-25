# JBimer - BIM Model Collision Conflict Management Tool #

**JBimer** designed specifically for BIM-engineers to easily manage and resolve collision conflicts in **BIM** models. The application works with clash reports from **Autodesk Revit** and allows engineers to create tasks, track statistics, send reports to customers, manage documentation, and communicate through a built-in chat. With these features, the application provides a comprehensive solution for MEP engineers to streamline their workflow and improve collaboration.

## Features
1. **Collision Conflict Resolution:** The application provides a simple and effective way for MEP engineers to manage and resolve collision conflicts in BIM models.
2. **HTML Report Upload:** The BIM coordinator can upload HTML reports generated from Autodesk Revit, allowing engineers to easily view and analyze the collision conflicts.
3. **Database Storage:** All collision objects are saved in the database and have an up-to-date status, making it easy to keep track of the progress and ensure that conflicts are resolved in a timely manner.
4. **Task Management:** The BIM coordinator can assign tasks to specific engineers for resolving conflicts, or if not possible, allow engineers to choose which conflicts they will work on.
5. **Real-time Statistics:** The application provides up-to-date statistics on the progress of conflict resolution, making it easy for the BIM coordinator and customers to monitor the project's status.
6. **Automated Reports:** Automatically generated reports can be sent to customers to demonstrate the project's progress and ensure transparency in the process.
7. **Notifications:** Users receive notifications when they are signed up for new conflicts, ensuring that they are aware of their responsibilities and can address the conflicts promptly.
8. **Commenting and Chatting:** Users can comment on conflicts and chat with other members to resolve difficult problems quickly and efficiently.
9. **Image Attachment:** Users can attach images to collision objects, providing a visual aid for resolving the conflicts.

## Stack
- Java 17
- Spring (Core, MVC, Boot, Data JPA, Security)
- Maven
- PostgreSQL
- Hibernate ORM
- Apache Tomcat
- JUnit5

## Installation
1. Clone the repository to your local machine:

   `git clone https://github.com/mitrofmep/JBimer.git`
2. Make sure you have Java 17 and PostgreSQL installed on your machine.
3. Navigate to the `resources` folder in the root directory of the project and run the `.sql` file to create the database tables. Make sure you have the necessary permissions to create the tables.
4. Set the database properties in the `application.properties` file. You can use the `application.properties.origin` file as an example.
5. Navigate to the root directory of the project in the terminal.
6. Build the project using Maven by running the following command:

   `./mvnw clean package`
7. Run the JAR file using the following command:

   `java -jar JBimer.jar`
8. Open a web browser and navigate to http://localhost:8080/main to access the application.

## Usage
1. Open the application
2. Upload your list of collisions from Autodesk Revit
3. Assign engineers to specific conflicts for resolution
4. Monitor progress in real-time

## Contributions
Contributions are welcome and encouraged! Please feel free to submit pull requests or open issues if you encounter any problems.

## License
This application is licensed under the MIT License.
