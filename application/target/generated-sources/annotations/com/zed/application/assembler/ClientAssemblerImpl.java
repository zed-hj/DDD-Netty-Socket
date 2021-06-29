package com.zed.application.assembler;

import com.zed.application.out.ClientDTO;
import com.zed.domain.aggregate.Client;
import com.zed.domain.aggregate.Client.ClientId;
import java.util.UUID;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-30T02:38:25+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_241 (Oracle Corporation)"
)
public class ClientAssemblerImpl implements ClientAssembler {

    @Override
    public ClientDTO domainToDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();

        UUID id = clientIdId( client );
        if ( id != null ) {
            clientDTO.setId( uuidToString( id ) );
        }

        return clientDTO;
    }

    private UUID clientIdId(Client client) {
        if ( client == null ) {
            return null;
        }
        ClientId id = client.getId();
        if ( id == null ) {
            return null;
        }
        UUID id1 = id.getId();
        if ( id1 == null ) {
            return null;
        }
        return id1;
    }
}
