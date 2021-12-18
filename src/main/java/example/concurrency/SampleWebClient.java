package example.concurrency;

public interface SampleWebClient {

    void send(String host, int port, int requestNumbers, SampleProducer producer);

    void join(long millis) throws InterruptedException;
}
