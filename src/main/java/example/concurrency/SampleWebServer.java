package example.concurrency;

public interface SampleWebServer {

    void start(int port, int requestsNumber);

    void join(long millis) throws InterruptedException;

    String getResult();
}
