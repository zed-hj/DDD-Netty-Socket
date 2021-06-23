package com.zed.adapter.web;

import com.zed.application.in.event.NotifyClientsCommand;
import com.zed.application.in.event.NotifyNamespaceCommand;
import com.zed.application.service.ClientApplicationService;
import com.zed.application.service.NamespacesApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 命名空间控制器
 *
 * @author zed
 */
@RestController
@RequestMapping("/test")
public class ClientController {

    @Resource
    private ClientApplicationService clientApplicationService;

    @PostMapping("/client")
    public ResponseEntity<Boolean> notifyNamespace(@RequestBody NotifyClientsCommand command) {
        for (int i = 0; i < 1000000; i++) {
            clientApplicationService.notifyClients(command);
        }
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
