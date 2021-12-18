package example.concurrency;

public interface SampleWebClient {

    void send(String host, int port, int requestNumbers);

    void join(long millis) throws InterruptedException;
}
