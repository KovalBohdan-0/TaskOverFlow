# TaskOverFlow

<p id="readme-top">

### Links

- [Project Homepage]
- [Project Homepage-2]

### Built With

* [![Spring Boot][Spring-boot.io]][SpringBoot-url]
* [![Spring Security][Spring-security.io]][SpringSecurity-url]
* [![String Data JPA][SpringDataJPA.io]][SpringDataJPA-url]
* [![Postgresql][Postgresql.org]][Postgresql-url]
* [![Azure][Azure.com]][Azure-url]
* [![Jwt][Jwt.io]][Jwt-url]
* [![TypeScript][TypeScript.org]][TypeScript-url]
* [![Angular][Angular.io]][Angular-url]
* [![Docker][Docker.com]][Docker-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### About The Project

TaskOverflow is a Kanban-style todo web application designed to help users efficiently manage and organize their tasks.

#### Overview

TaskOverflow leverages the power of Java Spring Boot for the backend and Angular for the frontend to provide a seamless and intuitive task management experience. The application allows users to create, categorize, prioritize, and track tasks through a visual and user-friendly interface.
Features

    Kanban Board: Organize tasks into customizable boards (e.g., To-Do, In Progress, Done) for easy visualization of task status.
    Task Creation: Easily create new tasks, set due dates, assignees, and descriptions to keep track of important details.
    Drag-and-Drop: Intuitive drag-and-drop functionality for effortless task management between different boards.
    User Authentication: Secure user authentication and authorization system to safeguard data and user accounts.
    Responsive Design: Angular-powered frontend ensures a responsive and seamless experience across devices.

#### Preview

![Main Page][main-screenshot]

![Task edit][task-edit-screenshot]

### Installation and Setup
Prerequisites

    Java JDK 17
    Node.js and npm
    Angular CLI
    PostgreSQL

Backend Setup

    Clone the repository.
    Navigate to the backend directory.
    Configure the database settings in application.properties.
    Run mvn spring-boot:run to start the backend server.

Frontend Setup

    Navigate to the frontend directory.
    Run npm install to install dependencies.
    Update API endpoints if necessary in the Angular service files.
    Run ng serve to start the frontend server.

Docker Compose Setup

    Navigate to the root directory.
    Configure the .web.env (create in root) and docker-compose.yml files as necessary.
        APP_DOMAIN=...
        DATASOURCE=...
        GOOGLE_CLIENT_ID=...
        GOOGLE_CLIENT_SECRET=...
        JWT_SECRET=...
        MAILPASS=...
        PGPASSWORD=...
        PGUSERNAME=...
        SENDER_MAIL=...
        REDIRECT_DOMAIN=...

    Run docker-compose up to start the application.
    Access the application at http://localhost:4200 by default.

Access the application at http://localhost:4200 by default.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->

[Project Homepage]: https://taskoverflow.pp.ua/
[Project Homepage-2]: https://task-over-flow.vercel.app/
[main-screenshot]: images/app1.png
[task-edit-screenshot]: images/app2.png
[Spring.io]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
[SpringDataJPA.io]: https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[SpringDataJPA-url]: https://spring.io/projects/spring-data-jpa
[Spring-boot.io]: https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white
[SpringBoot-url]: https://spring.io/projects/spring-boot
[Spring-security.io]: https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white
[SpringSecurity-url]: https://spring.io/projects/spring-security
[Postgresql.org]: https://img.shields.io/badge/Postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white
[Postgresql-url]: https://postgresql.org
[Jwt.io]: https://img.shields.io/badge/Json%20Web%20Tokens-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white
[Jwt-url]: https://jwt.io
[TypeScript.org]: https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=typescript&logoColor=white
[TypeScript-url]: https://www.typescriptlang.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Azure.com]: https://img.shields.io/badge/Azure-0089D6?style=for-the-badge&logo=microsoftazure&logoColor=white
[Azure-url]: https://azure.microsoft.com/en-us/
[Docker.com]: https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/

