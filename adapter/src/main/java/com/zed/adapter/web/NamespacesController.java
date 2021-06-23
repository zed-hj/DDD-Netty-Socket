package com.zed.adapter.web;

import com.zed.application.in.event.NotifyNamespaceCommand;
import com.zed.application.out.NamespaceDTO;
import com.zed.application.service.NamespacesApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 命名空间控制器
 *
 * @author zed
 */
@RestController
@RequestMapping("/test")
public class NamespacesController {

    @Resource
    private NamespacesApplicationService namespacesApplicationService;

    @GetMapping("/namespaces")
    public ResponseEntity<List<NamespaceDTO>> getNamespaces(String name) {
        return ResponseEntity.ok(namespacesApplicationService.getNamespaces(name));
    }

    @PostMapping("/namespaces")
    public ResponseEntity<Boolean> notifyNamespace(@RequestBody NotifyNamespaceCommand command) {
        namespacesApplicationService.notifyNamespace(command);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
