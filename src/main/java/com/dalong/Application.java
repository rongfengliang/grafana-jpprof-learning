package com.dalong;

import jpprof.CPUProfiler;
import spark.Service;

import java.io.IOException;
import java.time.Duration;

public class Application {
    public static void main(String[] args) throws IOException {
        Service service = Service.ignite();
        service.port(9999);
        service.get("/debug/pprof/profile", (req, res) -> {
            String seconds = req.queryParams("seconds");
            Duration duration = Duration.ofSeconds((long) Integer.parseInt(seconds));
            try {
                res.header("Content-Encoding", "gzip");
                res.status(200);
                CPUProfiler.start(duration, res.raw().getOutputStream());
            } catch (Exception var9) {
                res.status(500);
            } finally {
                res.raw().getOutputStream().close();
            }
            return null;
        });
        service.get("/demo", (request, response) -> {
            return "dalongdemo";
        });
    }
}
