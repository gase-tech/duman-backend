package com.kstech.duman.cache.controller;

import com.kstech.duman.cache.model.ClearCacheDto;
import com.kstech.duman.cache.service.ClearCacheService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CacheClearController.END_POINT)
public class CacheClearController {
    static final String END_POINT = "/api/cache";

    private final ClearCacheService clearCacheService;

    public CacheClearController(ClearCacheService clearCacheService) {
        this.clearCacheService = clearCacheService;
    }

    @PostMapping("/clear")
    public void cacheClear(@RequestBody ClearCacheDto clearCacheDto) {
        clearCacheService.clearCacheByServiceNames(clearCacheDto.getServices());
    }
}
