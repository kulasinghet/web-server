# Simple HTTP Server

## Project Description

The **Simple HTTP Server** is a Java-based application that serves as a rudimentary web server capable of handling HTTP requests and delivering web content. This project demonstrates fundamental concepts of network programming, file handling, and HTTP protocol interpretation.

## Project Objectives

1. **HTTP Request Handling:** The core objective of this project is to handle incoming HTTP requests. The server listens on a specific port (2728) and accepts incoming connections. It then parses these requests to determine the HTTP method, requested path, version, host, and headers.

2. **File Content Delivery:** The server is designed to deliver requested files to clients. It examines the requested path and, if found, returns the corresponding file content. If the requested file is not found, it generates a "404 Not Found" response and sends an HTML message indicating that the file is not available.

3. **Content Type Detection:** The server employs Java NIO to detect and set the content type of the delivered files. It uses the file's extension to determine the appropriate MIME type for the response header.

4. **Logging and Monitoring:** The application logs essential information about incoming requests and client interactions. This includes details like client IP, HTTP method, requested path, HTTP version, host, and request headers. The logs are printed to the console for monitoring purposes.

## Key Features

- **HTTP Protocol Handling:** The server supports HTTP/1.1 protocol and can handle various HTTP methods such as GET, POST, etc.

- **Dynamic Path Resolution:** When a client requests the root path ("/"), the server defaults to serving an "index.html" file. It also allows for serving other files located within the "htdocs" directory.

- **Error Handling:** In cases where requested files are not found, the server generates a user-friendly "Not Found" HTML response.

- **Content Type Detection:** The server uses Java NIO to determine the content type of requested files, allowing for proper rendering in web browsers.

- **Scalability:** The server is designed to handle multiple simultaneous client connections using a threaded approach, making it capable of serving multiple clients concurrently.

## Technologies Used

- Java
- Socket Programming
- Java NIO (for content type detection)
- HTTP/1.1 Protocol

## Usage

This HTTP server can be used for educational purposes to understand the basics of web servers, network communication, and HTTP protocol handling. It provides a simple foundation for building more advanced web server applications.
