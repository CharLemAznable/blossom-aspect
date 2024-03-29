package blossom.test.enhance;

import blossom.Blossom;
import com.github.charlemaznable.httpclient.annotation.Mapping;
import com.github.charlemaznable.httpclient.ohclient.OhClient;

@OhClient
@Mapping("http://127.0.0.1:21215")
public interface TestBuddyClient {

    @Blossom
    String y();

    @SuppressWarnings("UnusedReturnValue")
    @Blossom
    String z();
}
