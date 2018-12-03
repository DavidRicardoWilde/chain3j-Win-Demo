package Demo.ThirdPartContract;

import org.chain3j.protocol.Chain3j;
//import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.tx.ClientTransactionManager;
import org.chain3j.tx.Contract;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;
import java.math.BigInteger;
import java.util.ArrayList;

public class ContractHelper {
    private Chain3j chain3j;
    private List<String> accounts;

    public ContractHelper() throws Exception {
        chain3j = Chain3j.build(new HttpService("http://127.0.0.1:8545"));
        accounts = chain3j.mcAccounts().send().getAccounts();
    }

    private byte[] s2b32(String str){
        byte[] a = new byte[32];
        System.arraycopy(str.getBytes(),0,a,32-str.length(),str.length());
        return a;
    }

    public Voting deployContract(int accountIndex) throws Exception {
        String account = accounts.get(accountIndex);
        ClientTransactionManager ctm = new ClientTransactionManager(chain3j,account);
        BigInteger gasPrice = Contract.GAS_PRICE;
        BigInteger gasLimit = Contract.GAS_LIMIT;
        List<byte[]> candidates = new ArrayList<byte[]>();
        candidates.add(s2b32("Tommy"));
        candidates.add(s2b32("Jerry"));
        candidates.add(s2b32("Micky"));
        Voting voting = Voting.deploy(chain3j,ctm,gasPrice,gasLimit,candidates).send();
        System.out.println("address: " + voting.getContractAddress());
        System.out.println("receipt: " + voting.getTransactionReceipt());
        return voting;
    }

    public Voting loadContract(String address,int accountIndex) throws Exception {
        String account = accounts.get(accountIndex);
        ClientTransactionManager ctm = new ClientTransactionManager(chain3j,account);
        BigInteger gasPrice = Contract.GAS_PRICE;
        BigInteger gasLimit = Contract.GAS_LIMIT;
        Voting voting = Voting.load(address,chain3j,ctm,gasPrice,gasLimit);
        System.out.println("contract loaded");
        return voting;
    }

    public void callContractMethod(Voting voting,String candidate) throws Exception {
        TransactionReceipt receipt = voting.voteFor(s2b32(candidate)).send();
        System.out.println("receipt => " + receipt);
        BigInteger votes = voting.getVotesFor(s2b32(candidate)).send();
        System.out.println("votes for " + candidate + ": " + votes);
    }

}
