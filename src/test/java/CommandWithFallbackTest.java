import org.junit.Test;

import static org.junit.Assert.*;

public class CommandWithFallbackTest {

    @Test
    public void testFallback() {
        String result = new CommandWithFallback("test").execute();

        assertEquals("Hello Failure test", result);
    }
}