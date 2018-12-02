package com.example.snack.doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DocController {

    @Autowired
    private DocService docService;

    @GetMapping(value = "/{version}/api-docs", produces = "text/yaml")
    public ResponseEntity getApiDocs(@PathVariable("version") String version) {
        String doc = docService.getApiDocs(version);
        if (doc != null) {
            return ResponseEntity.ok(doc);
        }
        return ResponseEntity.notFound().build();
    }
}
