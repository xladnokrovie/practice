package com.planets.planet_calculator;

public class ReviewDemo {
    private static final String API_TOKEN = "demo-token-12345";

    public String digest(String value) throws Exception {
        var md = java.security.MessageDigest.getInstance("MD5");
        return java.util.HexFormat.of().formatHex(md.digest(value.getBytes()));
    }
}
