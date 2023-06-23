package com.acme.test01.anthonybuckland.accounts;

import com.acme.test01.anthonybuckland.database.SystemDB;
import org.junit.jupiter.api.BeforeEach;

public class CurrentAccountTest {

    private static SystemDB systemDB;

    @BeforeEach
    public static void init() {
        systemDB = SystemDB.getInstance();
    }



}
