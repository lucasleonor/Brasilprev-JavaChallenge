package br.com.brasilprev.teste.javachallenge.controller;

import br.com.brasilprev.teste.javachallenge.model.Client;
import br.com.brasilprev.teste.javachallenge.service.ClientService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService service;
    private ClientController controller;
    private int id;
    private Client client;

    @BeforeEach
    void setUp() {
        controller = new ClientController(service);
        id = new Random().nextInt();
        client = new Client();
        client.setName(RandomString.make());
    }

    @Test
    void findById() {
        when(service.findById(id)).thenReturn(client);
        assertThat(controller.findById(id), is(client));
        verify(service, times(1)).findById(id);
    }

    @Test
    void registerClient() {
        when(service.registerClient(new Client())).thenReturn(client);
        assertThat(controller.registerClient(new Client()), is(client));
        verify(service, times(1)).registerClient(new Client());
    }

    @Test
    void updateClient() {
        when(service.updateClient(id, new Client())).thenReturn(client);
        assertThat(controller.updateClient(id, new Client()), is(client));
        verify(service, times(1)).updateClient(id, new Client());
    }

    @Test
    void inactivateClient() {
        controller.inactivateClient(id);
        verify(service, times(1)).inactivateClient(id);
    }
}