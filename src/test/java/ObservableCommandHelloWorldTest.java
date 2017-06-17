import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ObservableCommandHelloWorldTest {

    @Test
    public void testObservableBlocking() throws Exception {
        Observable<String> observable = new ObservableCommandHelloWorld("World").observe();

        assertEquals("Hello World!", observable.toBlocking().single());
    }

    @Test
    public void testObservable() throws Exception {
        Observable<String> observable = new ObservableCommandHelloWorld("Test").observe();

        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        observable.subscribe(testSubscriber);

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Hello Test!");

        testSubscriber.assertReceivedOnNext(expectedResult);
    }
}