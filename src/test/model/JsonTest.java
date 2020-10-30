package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkBook(String name, Book bk) {
        assertEquals(name, bk);
    }
}
