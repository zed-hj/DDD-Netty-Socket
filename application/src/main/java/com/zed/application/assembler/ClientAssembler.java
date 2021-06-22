package com.zed.application.assembler;

import com.zed.application.out.ClientDTO;
import com.zed.domain.aggregate.Client;
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
public interface ClientAssembler {

    ClientAssembler INSTANCE = Mappers.getMapper(ClientAssembler.class);

    @Mappings({
            @Mapping(source = "client.id.id", target = "id", qualifiedByName = "uuidToString"),
    })
    ClientDTO domainToDTO(Client client);

    @Named("uuidToString")
    default public String uuidToString(UUID uuid) {
        return uuid.toString();
    }


}
