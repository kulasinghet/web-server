import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main( String[] args ) throws Exception {

        // Create a server socket on port 2728 using serverSocket object in Java
        try (ServerSocket serverSocket = new ServerSocket(2728)) {
            while (true) { // Loop forever
                try (Socket client = serverSocket.accept()) {
                    handleClient(client);
                }
            }
        }
    }

    private static void handleClient(Socket client) throws IOException {

        // Read the request using BufferedReader in Java io package
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

        StringBuilder requestBuilder = new StringBuilder();
        String line;

        while (!(line = bufferedReader.readLine()).isBlank()) {
            requestBuilder.append(line + "\r\n");
        }

        // here this will create the request string, and split that in to method, path, version and host using regex
        String request = requestBuilder.toString();
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String method = requestLine[0];
        String path = requestLine[1];
        String version = requestLine[2];
        String host = requestsLines[1].split(" ")[1];

        // here it will generate headers list using the request string
        List<String> headers = new ArrayList<>();
        for (int h = 2; h < requestsLines.length; h++) {
            String header = requestsLines[h];
            headers.add(header);
        }

        // in this function access log will be created and print to the console
        String accessLog = String.format("Client %s, method %s, path %s, version %s, host %s, headers %s",
                client.toString(), method, path, version, host, headers.toString());
        System.out.println(accessLog);


        Path filePath = getFilePath(path);
        if (Files.exists(filePath)) {
            // file exists, then it will call the sendResponse method to send the response
            String contentType = guessContentType(filePath);
            sendResponse(client, "200 OK", contentType, Files.readAllBytes(filePath));
        } else {
            // if the requested file is not found it will generate the HTML content and parse that in to the client response
            byte[] notFoundContent = "<h1>Requested file is not found</h1>".getBytes();
            sendResponse(client, "404 Not Found", "text/html", notFoundContent);
        }

    }

    private static void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException {
        // This will send the response to the client, information such as status, content type and content itself
        OutputStream clientOutput = client.getOutputStream();
        clientOutput.write(("HTTP/1.1 \r\n" + status).getBytes());
        clientOutput.write(("ContentType: " + contentType + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write(content);
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        client.close();
    }

    private static Path getFilePath(String path) {
        // if the path is /, then return index.html. This is the default page
        if ("/".equals(path)) {
            path = "/index.html";
        }

        // if not then it returns htdoc/path. Path me differ from the root of the project
        return Paths.get("htdocs", path);
    }

    private static String guessContentType(Path filePath) throws IOException {
        // this method returns the content type of the file using Java NIO
        return Files.probeContentType(filePath);
    }

}




//    DEV Community 👩‍💻👨‍💻. 2022. Build your own HTTP server in Java in less than one hour (only GET method). [online] Available at: <https://dev.to/mateuszjarzyna/build-your-own-http-server-in-java-in-less-than-one-hour-only-get-method-2k02> [Accessed 30 September 2022].