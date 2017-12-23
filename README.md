# Cache
具有二级缓存的自定义SharedPreferences，支持Bitmap    
使用方法：  
1、初始化Cache，需要传入的参数如下：  

    /**
     * Opens the cache in {@code directory}, creating a cache if none exists there.
     *
     * @param directory a writable directory
     * @param appVersion the value must be positive
     * @param valueCount the number of values per cache entry. Must be positive.
     * @param maxDiskSize the maximum number of bytes this diskCache should use to store
     * @param maxMemorySize the maximum number of kilobytes this memoryCache should be to store
     * @throws IOException if reading or writing the cache directory fails
     */
   Cache.open(File directory, int appVersion, int valueCount, long maxDiskSize, int maxMemorySize)；  
  
  2、调用Cache.putXXX(key, value)存入数据；  
  3、调用Cache.getXXX(key, valeu)取出数据；
    
