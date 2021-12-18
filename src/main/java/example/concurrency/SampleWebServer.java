package example.concurrency;

public interface SampleWebServer {

    void start(int port, int requestsNumber, SampleConsumer consumer);

    void join(long millis) throws InterruptedException;
}
