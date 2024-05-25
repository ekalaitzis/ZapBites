package com.example.zapbites.Search;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchController {
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<Map<String, List<?>>> search(@RequestParam String query) {
        Map<String, List<?>> searchResults = searchService.search(query);
        return ResponseEntity.ok(searchResults);
    }


}
