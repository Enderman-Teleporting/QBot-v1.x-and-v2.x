package io.github.et.tools;

import java.io.*;
import java.net.*;

public class Client {
    private static final Socket socket;
    private static OutputStream outputStream;
    private static InputStream inputStream;
    private static PrintWriter writer;
    private static BufferedReader reader;
    static{
        try {
            socket = new Socket("127.0.0.1", 35386);
            outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);
            inputStream = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getReply(String msg) throws IOException {
        writer.println(msg);
        return reader.readLine();
    }
}
