package blossom.buddy;

import blossom.Blossom;
import com.github.charlemaznable.httpclient.common.Mapping;
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
