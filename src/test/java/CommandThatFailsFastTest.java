import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class CommandThatFailsFastTest {

    @Test
    public void testSuccess() {
        String result = new CommandThatFailsFast(false).execute();

        assertEquals("success", result);
    }

    @Test(expected = HystrixRuntimeException.class)
    public void testFailure() {
        new CommandThatFailsFast(true).execute();
    }

    @Test
    public void testAsyncExecution() throws ExecutionException, InterruptedException {
        Future<String> future = new CommandThatFailsFast(false).queue();

        String result = future.get();
        assertEquals("success", result);
    }

    @Test(expected = ExecutionException.class)
    public void testAsyncExecutionFailure() throws ExecutionException, InterruptedException {
        Future<String> future = new CommandThatFailsFast(true).queue();

        future.get();
    }

    @Test
    public void testAsyncExecutionStartsAfterGetCall() {
        Future<String> future = new CommandThatFailsFast(true).queue();
    }
}