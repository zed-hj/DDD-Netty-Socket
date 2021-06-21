package com.zed.application.assembler;

import com.zed.application.out.NamespaceDTO;
import com.zed.domain.aggregate.Namespace;

import java.util.List;

/**
 * @author Zed
 */
public interface NamespaceAssembler {

    List<NamespaceDTO> domainToDTO(List<Namespace> namespaces);

}
