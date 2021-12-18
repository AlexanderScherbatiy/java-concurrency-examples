package example.concurrency;

import java.io.IOException;
import java.io.InputStream;

public interface SampleConsumer {

    void consume(InputStream is) throws IOException;
}
