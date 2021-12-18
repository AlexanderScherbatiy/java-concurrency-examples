package example.concurrency;

import java.io.IOException;
import java.io.OutputStream;

public interface SampleProducer {

    void produce(OutputStream os) throws IOException;
}
