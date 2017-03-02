package br.com.caelum.vraptor.controller;

import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@ServerEndpoint(value = "/table/socket")
public class TestWebSocket {

    // Store sessions if you want to, for example, broadcast a message to all users
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    @OnOpen
    public void connected(Session session, EndpointConfig config) {
        sessions.add(session);
    }

    @OnClose
    public void closed(Session session, CloseReason reason) {
        sessions.remove(session);
    }

    @OnMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("Got: " + message);   // Print message
        session.getAsyncRemote().sendText(message); // and send it back
    }

    public static void remove(Person p) {
        String json = (new Gson()).toJson(p);
        sessions.forEach(s -> s.getAsyncRemote().sendText(json));
    }
}
