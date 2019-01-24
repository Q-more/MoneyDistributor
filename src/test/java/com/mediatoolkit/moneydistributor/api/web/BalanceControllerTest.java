package com.mediatoolkit.moneydistributor.api.web;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * H2 database for testing purpose missing.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BalanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBalanceAsLander() {

    }

    @Test
    public void getBalanceAsBorrower() {
    }

    @Test
    public void getAllBalances() {
    }

    @Test
    public void getGroupBalance() {
    }
}