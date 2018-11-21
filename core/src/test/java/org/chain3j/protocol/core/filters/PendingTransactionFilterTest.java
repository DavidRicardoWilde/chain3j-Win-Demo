package org.chain3j.protocol.core.filters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import org.chain3j.protocol.core.methods.response.McLog;

@RunWith(MockitoJUnitRunner.class)
public class PendingTransactionFilterTest extends FilterTester {

    @Test
    public void testPendingTransactionFilter() throws Exception {
        McLog ethLog = objectMapper.readValue(
                //CHECKSTYLE:OFF
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":[\"0x31c2342b1e0b8ffda1507fbffddf213c4b3c1e819ff6a84b943faabb0ebf2403\",\"0xccc0d2e07c1febcaca0c3341c4e1268204b06fefa4bb0c8c0d693d8e581c82da\"]}",
                //CHECKSTYLE:ON
                McLog.class);

        runTest(ethLog, chain3j.mcPendingTransactionHashObservable());
    }
}
