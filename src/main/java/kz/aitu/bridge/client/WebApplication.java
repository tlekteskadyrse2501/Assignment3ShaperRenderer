package kz.aitu.bridge.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import kz.aitu.bridge.abstraction.Circle;
import kz.aitu.bridge.abstraction.Shape;
import kz.aitu.bridge.abstraction.Square;
import kz.aitu.bridge.implementor.RasterRenderer;
import kz.aitu.bridge.implementor.Renderer;
import kz.aitu.bridge.implementor.VectorRenderer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public final class WebApplication {
    private final List<Shape> shapes = new ArrayList<>();

    public void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new StaticHandler());
        server.createContext("/api/state", new StateHandler());
        server.createContext("/api/action", new ActionHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Website mode running on http://localhost:8080/");
        System.out.println("Open the browser and test it out. Press Ctrl+C to exit.");
    }

    private class StaticHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("/".equals(exchange.getRequestURI().getPath())) {
                String html = WebUI.getHtml();
                byte[] response = html.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.length);
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        }
    }

    private class StateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            
            StringBuilder json = new StringBuilder("[\n");
            for (int i = 0; i < shapes.size(); i++) {
                Shape shape = shapes.get(i);
                String type = shape instanceof Circle ? "circle" : "square";
                String rendererType = shape.getRenderer().getType();
                
                double x = 0, y = 0, size = 0;
                if (shape instanceof Circle c) {
                    x = c.getX();
                    y = c.getY();
                    size = c.getRadius();
                } else if (shape instanceof Square s) {
                    x = s.getX();
                    y = s.getY();
                    size = s.getSideLength();
                }
                
                json.append(String.format("  { \"type\": \"%s\", \"x\": %f, \"y\": %f, \"size\": %f, \"renderer\": \"%s\" }", type, x, y, size, rendererType));
                if (i < shapes.size() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }
            json.append("]\n");
            
            byte[] response = json.toString().getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    private class ActionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                Map<String, String> params = parseQuery(query);
                
                String action = params.getOrDefault("action", "");
                try {
                    switch (action) {
                        case "createCircle": {
                            double x = Double.parseDouble(params.get("x"));
                            double y = Double.parseDouble(params.get("y"));
                            double r = Double.parseDouble(params.get("radius"));
                            String rType = params.get("renderer");
                            Renderer rend = "vector".equalsIgnoreCase(rType) ? new VectorRenderer() : new RasterRenderer();
                            shapes.add(new Circle(x, y, r, rend));
                            break;
                        }
                        case "createSquare": {
                            double x = Double.parseDouble(params.get("x"));
                            double y = Double.parseDouble(params.get("y"));
                            double s = Double.parseDouble(params.get("side"));
                            String rType = params.get("renderer");
                            Renderer rend = "vector".equalsIgnoreCase(rType) ? new VectorRenderer() : new RasterRenderer();
                            shapes.add(new Square(x, y, s, rend));
                            break;
                        }
                        case "switch": {
                            int idx = Integer.parseInt(params.get("index"));
                            String rType = params.get("renderer");
                            Renderer rend = "vector".equalsIgnoreCase(rType) ? new VectorRenderer() : new RasterRenderer();
                            if(idx >= 0 && idx < shapes.size()) {
                                shapes.get(idx).setRenderer(rend);
                            }
                            break;
                        }
                        case "move": {
                            int idx = Integer.parseInt(params.get("index"));
                            double x = Double.parseDouble(params.get("x"));
                            double y = Double.parseDouble(params.get("y"));
                            if(idx >= 0 && idx < shapes.size()) {
                                Shape s = shapes.get(idx);
                                if (s instanceof Circle c) {
                                    c.setX(x);
                                    c.setY(y);
                                } else if (s instanceof Square sq) {
                                    sq.setX(x);
                                    sq.setY(y);
                                }
                            }
                            break;
                        }
                        case "clear": {
                            shapes.clear();
                            break;
                        }
                        case "demo": {
                            shapes.clear();
                            shapes.add(new Circle(150, 150, 50, new VectorRenderer()));
                            shapes.add(new Square(400, 150, 100, new RasterRenderer()));
                            break;
                        }
                    }
                    respondText(exchange, 200, "OK");
                } catch(Exception e) {
                    respondText(exchange, 400, "Error: " + e.getMessage());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    private void respondText(HttpExchange exchange, int code, String text) throws IOException {
         byte[] response = text.getBytes(StandardCharsets.UTF_8);
         exchange.sendResponseHeaders(code, response.length);
         OutputStream os = exchange.getResponseBody();
         os.write(response);
         os.close();
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null || query.isEmpty()) return map;
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                map.put(pair[0], pair[1]);
            } else if (pair.length == 1) {
                map.put(pair[0], "");
            }
        }
        return map;
    }
}