package com.zed.domain.service;


import com.zed.domain.aggregate.NamespaceAggregate;
import com.zed.domain.repository.NamespaceRepository;

import javax.annotation.Resource;
import java.util.List;

@Resource
public class NamespaceService {

    @Resource
    private NamespaceRepository namespaceRepository;

}
