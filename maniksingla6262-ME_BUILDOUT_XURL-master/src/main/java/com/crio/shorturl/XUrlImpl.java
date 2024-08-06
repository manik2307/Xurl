package com.crio.shorturl;

import java.util.HashMap;
import java.util.Map;

public class XUrlImpl implements XUrl {

    private Map<String, String> longToShortUrlMap;
    private Map<String, String> shortToLongUrlMap;
    private Map<String, Integer> longUrlHitCountMap;

    public XUrlImpl() {
        this.longToShortUrlMap = new HashMap<>();
        this.shortToLongUrlMap = new HashMap<>();
        this.longUrlHitCountMap = new HashMap<>();
    }

    @Override
    public String registerNewUrl(String longUrl) {
        return registerNewUrl(longUrl, null);
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if (shortUrl != null && shortToLongUrlMap.containsKey(shortUrl)) {
            // Short URL already exists, return null
            return null;
        }

        String finalShortUrl = shortUrl != null ? shortUrl : generateShortUrl();
        longToShortUrlMap.put(longUrl, finalShortUrl);
        shortToLongUrlMap.put(finalShortUrl, longUrl);
        return finalShortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        if (shortToLongUrlMap.containsKey(shortUrl)) {
            String longUrl = shortToLongUrlMap.get(shortUrl);
            // Increment hit count for the corresponding long URL
            longUrlHitCountMap.put(longUrl, longUrlHitCountMap.getOrDefault(longUrl, 0) + 1);
            return longUrl;
        }
        return null;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        return longUrlHitCountMap.getOrDefault(longUrl, 0);
    }

    @Override
    public String delete(String longUrl) {
        String shortUrl = longToShortUrlMap.remove(longUrl);
        shortToLongUrlMap.remove(shortUrl);
        return shortUrl;
    }

    private String generateShortUrl() {
        // In reality, implement a proper shortening algorithm
        // For simplicity, let's just return a random alphanumeric string
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortUrl = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            shortUrl.append(characters.charAt((int) (Math.random() * characters.length())));
        }
        return "http://short.url/" + shortUrl.toString();
    }
}
