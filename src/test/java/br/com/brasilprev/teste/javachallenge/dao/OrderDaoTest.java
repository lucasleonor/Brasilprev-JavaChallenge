package br.com.brasilprev.teste.javachallenge.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderDaoTest {

    @Autowired
    private OrderDao dao;

    @Test
    public void findById() {
        assertThat(dao.findById(-1, -1), is(Optional.empty()));
        assertThat(dao.findById(1, -1), is(Optional.empty()));
        assertThat(dao.findById(-1, 1), is(Optional.empty()));
        assertThat(dao.findById(1, 1), is(not(Optional.empty())));
    }
}