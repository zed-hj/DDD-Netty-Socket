package com.zed.application.assembler;

import com.zed.application.out.NamespaceDTO;
import com.zed.domain.aggregate.Namespace;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zed
 */
@Component
public class NamespaceAssemblerImpl implements NamespaceAssembler {

    @Override
    public List<NamespaceDTO> domainToDTO(List<Namespace> namespaces) {
        return namespaces.stream().map(el -> {
            NamespaceDTO dto = new NamespaceDTO();
            dto.setName(el.getId().getId());
            dto.setTenants(el.getTenants()
                    .stream()
                    .map(TenantAssembler.INSTANCE::domainToDTO)
                    .collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }

}
