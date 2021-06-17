package com.zed.adapter.web;

import com.zed.application.service.NamespacesApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 命名空间控制器
 *
 * @author zed
 */
@RestController
public class NamespacesController {

    @Resource
    private NamespacesApplicationService namespacesApplicationService;

    @GetMapping("/namespaces")
    public ResponseEntity<List<String>> getNamespaces(String name) {
        namespacesApplicationService.getNamespaces(name);
        return null;
    }

}
