package com.kstech.duman.cache.service;

import java.util.List;

public interface ClearCacheService {
    void clearCacheByServiceNames(List<String> serviceNames);
}
