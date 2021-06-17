package com.zed.application.service;

import com.zed.domain.repository.NamespaceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Application是业务流程的封装，不处理业务逻辑
 * <p>
 * 准备数据：包括从外部服务或持久化源取出相对应的Entity、VO以及外部服务返回的DTO。
 * 执行操作：包括新对象的创建、赋值，以及调用领域对象的方法对其进行操作。需要注意的是这个时候通常都是纯内存操作，非持久化。
 * 持久化：将操作结果持久化，或操作外部系统产生相应的影响，包括发消息等异步操作。
 *
 * @author zed
 */
@Service
public class NamespacesApplicationService {

    @Resource
    private NamespaceRepository namespaceRepository;

    public void getNamespaces(String name) {
    }

}
