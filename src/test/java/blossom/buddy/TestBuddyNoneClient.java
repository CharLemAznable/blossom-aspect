package blossom.buddy;

import com.github.charlemaznable.httpclient.common.Mapping;
import com.github.charlemaznable.httpclient.ohclient.OhClient;

@OhClient
@Mapping("http://127.0.0.1:21215")
public interface TestBuddyNoneClient {

    String x();
}
