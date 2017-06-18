import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandUsingRequestCacheTest {

    @Test
    public void testWithoutCacheHits() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        try {
            assertTrue(new CommandUsingRequestCache(2).execute());
            assertFalse(new CommandUsingRequestCache(1).execute());
            assertTrue(new CommandUsingRequestCache(4).execute());
        } finally {
            context.shutdown();
        }
    }

    @Test
    public void testWithCacheHits() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        CommandUsingRequestCache commandA = new CommandUsingRequestCache(2);
        CommandUsingRequestCache commandB = new CommandUsingRequestCache(2);

        try {
            assertTrue(commandA.execute());
            assertFalse(commandA.isResponseFromCache());

            assertTrue(commandB.execute());
            assertTrue(commandB.isResponseFromCache());
        } finally {
            context.shutdown();
        }

        context = HystrixRequestContext.initializeContext();
        CommandUsingRequestCache commandC = new CommandUsingRequestCache(2);

        try {
            assertTrue(commandC.execute());
            assertFalse(commandC.isResponseFromCache());
        } finally {
            context.shutdown();
        }
    }
}