package com.zed.application.assembler;

import com.zed.application.out.TenantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

/**
 * @author Zed
 */
@Mapper
public interface TenantAssembler {

    TenantAssembler INSTANCE = Mappers.getMapper(TenantAssembler.class);

    @Mappings({
            @Mapping(source = "tenant.id.id", target = "id", qualifiedByName = "uuidToString"),
    })
    TenantDTO domainToDTO(Tenant tenant);

    @Named("uuidToString")
    default public String uuidToString(UUID uuid) {
        return uuid.toString();
    }


}
