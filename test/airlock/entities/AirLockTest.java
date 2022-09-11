package airlock.entities;

import static org.junit.jupiter.api.Assertions.*;

class AirLockTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void test() {
        AirLock airLock = new AirLock(null, null, null);
        assertEquals(3, 1 + 2);
    }
}