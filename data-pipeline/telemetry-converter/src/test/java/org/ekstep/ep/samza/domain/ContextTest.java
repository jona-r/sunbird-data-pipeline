package org.ekstep.ep.samza.domain;

import org.ekstep.ep.samza.fixtures.EventFixture;
import org.ekstep.ep.samza.reader.Telemetry;
import org.ekstep.ep.samza.reader.TelemetryReaderException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class ContextTest {

    private Telemetry oeStart;
    private Telemetry geStart;
    private Telemetry ceStart;
    private Telemetry cpImpression;

    @Before
    public void setup() throws FileNotFoundException {
        oeStart = new Telemetry(EventFixture.getEvent("OE_START"));
        geStart = new Telemetry(EventFixture.getEvent("GE_START"));
        ceStart = new Telemetry(EventFixture.getEvent("CE_START"));
        cpImpression = new Telemetry(EventFixture.getEvent("CP_IMPRESSION"));
    }

    @Test
    public void getChannel() throws Exception {
        Context context = new Context(oeStart);
        assertEquals(oeStart.<String>mustReadValue("channel"), context.getChannel());
    }

    @Test
    public void getPdata() throws TelemetryReaderException {
        Context context = new Context(oeStart);
        PData pdata = context.getpData();
        assertEquals(oeStart.mustReadValue("pdata.id"), pdata.getId());
        assertEquals(oeStart.mustReadValue("pdata.pid"), pdata.getPid());
        assertEquals(oeStart.mustReadValue("pdata.ver"), pdata.getVer());
    }

    @Test
    public void getEnvForOEEvents() throws TelemetryReaderException {
        Context context = new Context(oeStart);
        assertEquals("ContentPlayer", context.getEnv());
    }

    @Test
    public void getEnvForGEEvents() throws TelemetryReaderException {
        Context context = new Context(geStart);
        assertEquals("Genie", context.getEnv());
    }

    @Test
    public void getEnvForCEEvents() throws TelemetryReaderException {
        Context context = new Context(ceStart);
        assertEquals("ContentEditor", context.getEnv());
    }

    @Test
    public void getEnvForCPEvents() throws TelemetryReaderException {
        Context context = new Context(cpImpression);
        assertEquals(cpImpression.mustReadValue("edata.eks.env"), context.getEnv());
    }
}