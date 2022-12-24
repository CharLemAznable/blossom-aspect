package blossom.test.enhance;

import blossom.spring.BlossomImport;
import com.github.charlemaznable.httpclient.ohclient.OhScan;
import org.n3r.eql.eqler.spring.EqlerScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EqlerScan
@OhScan
@BlossomImport
public class TestBuddyConfig {
}
