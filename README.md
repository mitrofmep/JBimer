# JBimer - BIM Model Collision Conflict Management Tool #

**JBimer** designed specifically for BIM-engineers to easily manage and resolve collision conflicts in **BIM** models. The application works with clash reports from **Autodesk Revit** and allows engineers to create tasks, track statistics, send reports to customers, manage documentation, and communicate through a built-in chat. With these features, the application provides a comprehensive solution for MEP engineers to streamline their workflow and improve collaboration.

## Features
- [x] **Collision Conflict Resolution:** The application provides a simple and effective way for MEP engineers to manage and resolve collision conflicts in BIM models.
- [x] **HTML Report Upload:** The BIM coordinator can upload HTML reports generated from Autodesk Revit, allowing engineers to easily view and analyze the collision conflicts.
- [x] **Database Storage:** All collision objects are saved in the database and have an up-to-date status, making it easy to keep track of the progress and ensure that conflicts are resolved in a timely manner.
- [x] **Task Management:** The BIM coordinator can assign tasks to specific engineers for resolving conflicts, or if not possible, allow engineers to choose which conflicts they will work on.
- [ ] **Real-time Statistics:** The application provides up-to-date statistics on the progress of conflict resolution, making it easy for the BIM coordinator and customers to monitor the project's status.
- [ ] **Automated Reports:** Automatically generated reports can be sent to customers to demonstrate the project's progress and ensure transparency in the process.
- [ ] **Notifications:** Users receive notifications when they are signed up for new conflicts, ensuring that they are aware of their responsibilities and can address the conflicts promptly.
- [x] **Commenting and Chatting:** Users can comment on conflicts and chat with other members to resolve difficult problems quickly and efficiently.
- [ ] **Image Attachment:** Users can attach images to collision objects, providing a visual aid for resolving the conflicts.

## Stack
- Java 17
- Spring (Core, MVC, Boot, Data JPA, Security)
- Maven
- PostgreSQL
- Hibernate ORM
- JUnit5

## Installation

1. Clone the repository to your local machine:

`git clone git@github.com:mitrofmep/JBimer.git`

2. Make sure Docker is installed and running on your system. If you are using Windows, ensure that Docker Desktop is running.

3. Navigate to the project directory:

`cd <project_directory>`

4. Start the application using Docker Compose:

`docker-compose up`

This command will build and run the Spring Boot application along with its required dependencies in separate Docker containers. Wait for the process to complete, and you will see the application logs indicating a successful startup.

You can now access the application by opening your web browser and visiting the appropriate URL, depending on the configuration specified in the application. By default is:

`localhost:8080`

## Usage
1. Open the application
2. Upload your list of collisions from Autodesk Revit
3. Assign engineers to specific conflicts for resolution
4. Monitor progress in real-time

## Contributions
Contributions are welcome and encouraged! Please feel free to submit pull requests or open issues if you encounter any problems.

## License
This application is licensed under the MIT License.
