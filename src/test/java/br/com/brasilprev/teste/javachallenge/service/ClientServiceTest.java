package br.com.brasilprev.teste.javachallenge.service;

import br.com.brasilprev.teste.javachallenge.dao.ClientDao;
import br.com.brasilprev.teste.javachallenge.model.Client;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientDao dao;
    private ClientService service;
    private int id;
    private Client client;

    @BeforeEach
    void setUp() {
        service = new ClientService(dao);
        id = new Random().nextInt();
        client = new Client();
        client.setName(RandomString.make());
        client.setActive(true);
    }

    @Test
    void findById() {
        when(dao.findById(id)).thenReturn(Optional.of(client));
        assertThat(service.findById(id), is(client));
        verify(dao, times(1)).findById(id);
    }

    @Test
    void findById_notInBase() {
        when(dao.findById(id)).thenReturn(Optional.empty());
        assertThrows(HttpClientErrorException.class, () -> service.findById(id));
    }

    @Test
    void findById_inactive() {
        when(dao.findById(id)).thenReturn(Optional.of(client));
        client.setActive(false);
        assertThrows(HttpClientErrorException.class, () -> service.findById(id));
    }

    @Test
    void registerClient() {
        when(dao.save(new Client())).thenReturn(client);
        assertThat(service.registerClient(new Client()), is(client));
        verify(dao, times(1)).save(new Client());
    }

    @Test
    void updateClient() {
        Client clientWithId = new Client();
        clientWithId.setId(id);
        when(dao.findById(id)).thenReturn(Optional.of(client));
        when(dao.save(clientWithId)).thenReturn(client);
        assertThat(service.updateClient(id, new Client()), is(client));
        verify(dao, times(1)).save(clientWithId);
    }

    @Test
    void inactivateClient() {
        Client client = mock(Client.class);
        when(client.isActive()).thenReturn(true);
        when(dao.findById(id)).thenReturn(Optional.of(client));
        service.inactivateClient(id);
        verify(dao, times(1)).save(client);
        verify(client, times(1)).setActive(false);
    }
}