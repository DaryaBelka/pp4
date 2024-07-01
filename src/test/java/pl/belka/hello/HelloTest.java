package pl.belka.hello;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HelloTest {
    @Test
    void equationTestV2(){
        //A  / Arrange
        int a = 2;
        int b = 3;
        //A  / Act
        var result = a+b;
        //A  / Assert
        assert (5 == result);
    }

    @Test
    void itFail() {
        //A / Arrange  / Given
        var a = 2;
        var b = 4;
        //A / Act      / When
        var result = a + b;
        //A / Assert   / Then / Expected
        assert (10 != result);
    }

    @Test
    void itGreetUsername() {
        //Arrange
        String name = "Darya";
        //Act
        String message = String.format("Hello %s", name);
        //Assert
        assertEquals("Hello Darya", message);
    }

    @Test
    void listExpectedToBeEmpty() {
        var list = new ArrayList<>();

        list.add("Jakub");
        list.remove(0);

        assert list.isEmpty();
    }
}