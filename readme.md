# Snake Game Server

This is a Snake Game Server implemented in Java using Maven. The server handles multiple clients and manages the game state for each connected player.

## Features

- Multiplayer support
- Real-time game state updates
- Simple and intuitive game mechanics

## Requirements

- Java 22 or higher
- Maven 3.6.0 or higher

## Setup

1. Clone the repository:

   ```sh
   git clone https://github.com/thativam/online-snake.git
   cd online-snake
   ```

2. Build the project using Maven:

   ```sh
   mvn clean install
   ```

### Optional: use jib to build a image locally for each module(cd module and then run the mvn command)
```sh
   mvn jib:dockerBuild
```

## Configuration

You can configure the server settings in the `application.properties` file located in the `src/main/resources` directory for each module.



## Overview
![image](https://github.com/user-attachments/assets/318b5d09-b126-43d0-9b47-56f71508de39)

1. + Scalability
2. + RTS Performance
3. - Fault tolerance
4. - Complexity
5. + Centralized
     
## Repository
Each module will have its pom, for commom dependencys and global build.
- master-server/
     - pom.xml
- server/
     - pom.xml
- client/
     - pom.xml
- pom.xml

## Contributing

### TODOS
- General
   - Add dependabot to update dependencies
   - Configure github main branch permission
- Master-Server:
   - Server must listen to client request and then initialize a server for it
   - Control server lifecycle
   - Differ between client and server requests
   - For now, logs stats into a in memory db
   - Use some log library
- Server:
   - Initialize the module with a pom.xml(use master server as a model)
   - Must be only initialize/exclude by the master server
   - For now, logs stats into a in memory db
   - Use some log library
   - Just send/receive client menssages
- Client
   - Initialize the module with a pom.xml(use master server as a model)
   - Must send a request to master server to initialize a server
   - Must connect to the generate server and be able to communicate
   - For now, logs stats into a in memory db
   - Use some log library

Contributions are welcome! Please fork the repository and submit a pull request.
Please for each todo open a branch from Main and then open a PR when its done.
## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
