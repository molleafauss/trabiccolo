package net.molleafauss.cf.trabiccolo.configuration;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Creates beans for the processor "module"
 */
@Configuration
public class GraphiteReporterConfiguration {

    @Bean
    public GraphiteReporter configureGraphiteReporter(MetricRegistry registry,
                                                      @Value("${processor.graphite.host:localhost}") String graphiteHost,
                                                      @Value("${processor.graphite.port:2003}") int graphitePort) {
        final Graphite graphite = new Graphite(new InetSocketAddress(graphiteHost, 2003));
        final GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);
        reporter.start(1, TimeUnit.MINUTES);
        return reporter;
    }
}
