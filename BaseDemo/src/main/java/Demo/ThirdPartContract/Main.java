package Demo.ThirdPartContract;


import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.tx.ClientTransactionManager;

import java.math.BigInteger;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Chain3j chain3j = Chain3j.build(new HttpService("http://127.0.0.1:8545"));
        String contractAddress = "0xd70Fb279ddC088F02a3689D45Cec82fF74C7FBfD";

        List<String> accounts = chain3j.mcAccounts().send().getAccounts();
        ClientTransactionManager ctm1 = new ClientTransactionManager(chain3j,accounts.get(0));
        Voting v1 = Voting.load(contractAddress,chain3j,ctm1, BigInteger.valueOf(1000000),BigInteger.valueOf(1000001));
        v1.voteFor(stringToByte32("Tommy")).send();
        ClientTransactionManager ctm2 = new ClientTransactionManager(chain3j,accounts.get(1));
        Voting v2 = Voting.load(contractAddress,chain3j,ctm2,BigInteger.valueOf(1000000),BigInteger.valueOf(1000001));
        v2.voteFor(stringToByte32("Jerry")).send();

    }

    private static byte[] stringToByte32(String str) {
        byte[] a = new byte[32];
        System.arraycopy(str.getBytes(),0,a,32-str.length(),str.length());
        return a;
    }
}
