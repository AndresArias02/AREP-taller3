package edu.eci.arep;

import edu.eci.arep.Annotation.RequestParam;
import edu.eci.arep.services.RestService;

import java.io.*;
import java.lang.reflect.*;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine == null) return;

            String[] tokens = requestLine.split(" ");
            String method = tokens[0];
            String URI = tokens[1];

            handleRequest(method, URI, out, dataOut);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleRequest(String method, String URI, PrintWriter out, BufferedOutputStream dataOut) throws IOException, InvocationTargetException, IllegalAccessException {

        System.out.println("Handling request: " + method + " " + URI);
        System.out.println("");

        if (method.equals("GET") && !URI.startsWith("/app") && !URI.startsWith("/Spring")) {
            handleGetRequest(URI, out, dataOut);
        } else {
            try {
                String responseBody = null;
                Integer contentLength = null;

                if (URI.startsWith("/app")) {
                    System.out.println("----------------------- SPARK -----------------------");
                    System.out.println("");
                    responseBody = callService(URI, method);
                    contentLength = responseBody.getBytes().length;
                } else if (URI.startsWith("/Spring")) {
                    System.out.println("----------------------- SPRING -----------------------");
                    System.out.println("");
                    responseBody = callMethod(URI);
                    contentLength = responseBody.getBytes().length;
                }

                out.print("HTTP/1.1 200 OK\r\n");
                out.print("Content-Type: application/json\r\n");
                out.print("Content-Length: " + contentLength + "\r\n");
                out.print("\r\n");
                out.print(responseBody);
                out.flush();
            } catch (Exception e) {
                out.print("HTTP/1.1 500 Internal Server Error\r\n");
                out.print("Content-Type: text/html\r\n");
                out.print("\r\n");
                out.print("<html><body><h1>Internal Server Error</h1></body></html>");
                out.flush();
                e.printStackTrace();
            }
        }
    }

    private void handleGetRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        File file = new File(HttpServer.WEB_ROOT, fileRequested);
        int fileLength = (int) file.length();
        String content = getContentType(fileRequested);

        if (file.exists()) {
            byte[] fileData = readFileData(file, fileLength);
            out.print("HTTP/1.1 200 OK\r\n");
            out.print("Content-type: " + content + "\r\n");
            out.print("Content-length: " + fileLength + "\r\n");
            out.print("\r\n");
            out.flush();
            dataOut.write(fileData, 0, fileLength);
            dataOut.flush();
        } else {
            out.print("HTTP/1.1 404 Not Found\r\n");
            out.print("Content-type: text/html\r\n");
            out.print("\r\n");
            out.flush();
            out.print("<html><body><h1>File Not Found</h1></body></html>");
            out.flush();
        }
    }

    private String callService(String URI, String method) {
        String response = "";
        RestService restService = SparkServer.findHandler(cleanURI(URI), method);
        return restService.response(URI);
    }

    private String callMethod(String URI) throws InvocationTargetException, IllegalAccessException {
        Method springMethod = SpringServer.findMappingMethod(cleanURI(URI));
        Map<String, String> queryParams = extractQueryParams(URI);
        String response;

        if (checkIfHasRequestParam(springMethod)) {
            Object[] parameters = getParameters(springMethod, queryParams);
            response = (String) springMethod.invoke(null, parameters);
        } else {
            response = (String) springMethod.invoke(null);
        }

        return response;
    }

    private Boolean checkIfHasRequestParam(Method m) {
        for (Parameter p : m.getParameters()) {
            if (p.isAnnotationPresent(RequestParam.class)) {
                return true;
            }
        }
        return false;
    }

    private Object[] getParameters(Method method, Map<String, String> queryParams) {
        Parameter[] parameters = method.getParameters();
        Object[] params = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            if (requestParam != null) {
                String paramName = requestParam.value();
                params[i] = queryParams.getOrDefault(paramName, requestParam.defaultValue());
            }
        }
        return params;
    }

    private Map<String, String> extractQueryParams(String URI) {
        Map<String, String> params = new HashMap<>();
        String[] parts = URI.split("\\?");
        if (parts.length > 1) {
            String[] queryParams = parts[1].split("&");
            for (String param : queryParams) {
                String[] pair = param.split("=");
                if (pair.length == 2) {
                    params.put(pair[0], pair[1]);
                }
            }
        }
        return params;
    }

    private String cleanURI(String URI) {
        int queryStartIndex = URI.indexOf('?');
        if (queryStartIndex != -1) {
            return URI.substring(0, queryStartIndex);
        }
        return URI;
    }

    private void printRequestHeader(String requestLine, BufferedReader in) throws IOException {
        System.out.println("Request line: " + requestLine);
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Header: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }
    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html")) return "text/html";
        else if (fileRequested.endsWith(".css")) return "text/css";
        else if (fileRequested.endsWith(".js")) return "application/javascript";
        else if (fileRequested.endsWith(".png")) return "image/png";
        else if (fileRequested.endsWith(".jpg")) return "image/jpeg";
        return "text/plain";
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        byte[] fileData = new byte[fileLength];
        try (FileInputStream fileIn = new FileInputStream(file)) {
            fileIn.read(fileData);
        }
        return fileData;
    }
}
