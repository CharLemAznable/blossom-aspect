package blossom.test.enhance;

import com.github.charlemaznable.eql.apollo.Aql;
import org.n3r.eql.eqler.annotations.EqlerConfig;
import org.n3r.eql.eqler.annotations.Sql;

@EqlerConfig(eql = Aql.class)
public interface TestBuddyNoneEqler {

    @Sql("select 'x'")
    String x();
}
