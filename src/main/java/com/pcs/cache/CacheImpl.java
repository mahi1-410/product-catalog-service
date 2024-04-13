package com.pcs.cache;
import redis.clients.jedis.Jedis;


public class CacheImpl {
	  private Jedis jedis;

	    public CacheImpl() {
	        // Connect to Redis server
	        jedis = new Jedis("localhost", 6379);
	    }

	    public String getProductDataFromCache(String productId) {
	        // Check if product data exists in cache
	        String productData = jedis.get(productId);
	        return productData != null ? productData : "";
	    }

	    public void setProductDataInCache(String productId, String productData) {
	        // Set product data in cache with a TTL (time-to-live) of 1 hour
	        jedis.setex(productId, 3600, productData);
	    }

	    public String getProductDataFromDatabase(String productId) {
	        // Fetch product data from the database
	        return "Dummy product data for product ID: " + productId;
	    }

	    public String getProductData(String productId) {
	        String productData = getProductDataFromCache(productId);
	        if (productData.isEmpty()) {
	            // If not found in cache, fetch from database
	            productData = getProductDataFromDatabase(productId);
	            // Store in cache for future use
	            setProductDataInCache(productId, productData);
	        }
	        return productData;
	    }

	    public static void main(String[] args) {
	    	CacheImpl cache = new CacheImpl();
	        // Sample usage
	        String productId = "123456";
	        String productData = cache.getProductData(productId);
	        System.out.println("Product data: " + productData);
	    }
}
