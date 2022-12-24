package blossom.test.enhance;

import blossom.Blossom;
import com.github.charlemaznable.eql.apollo.Aql;
import org.n3r.eql.eqler.annotations.EqlerConfig;
import org.n3r.eql.eqler.annotations.Sql;

@EqlerConfig(eql = Aql.class)
public interface TestBuddyEqler {

    @Blossom
    @Sql("select 'y'")
    String y();

    @SuppressWarnings("UnusedReturnValue")
    @Blossom
    @Sql("select count(*) from z")
    String z();
}
