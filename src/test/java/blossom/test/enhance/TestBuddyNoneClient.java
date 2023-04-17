package blossom.test.enhance;

import com.github.charlemaznable.httpclient.annotation.Mapping;
import com.github.charlemaznable.httpclient.ohclient.OhClient;

@OhClient
@Mapping("http://127.0.0.1:21215")
public interface TestBuddyNoneClient {

    String x();
}
