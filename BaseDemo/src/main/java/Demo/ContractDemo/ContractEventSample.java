package Demo.ContractDemo;


import org.chain3j.crypto.Credentials;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.RemoteCall;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.utils.Convert;

import java.math.BigInteger;


public class ContractEventSample {
//    private static String contractAddress = "0x001e1AA3772111f82c0612401B0152CD73619697";
//    private static Chain3j chain3j;

    public static void main(String[] args){
        deploy();
        use();
    }

    private static void deploy(){
        Chain3j chain3j = Chain3j.build(new HttpService("http://127.0.0.1"));
        Credentials credentials = null;
        RemoteCall<TokenERC20> deploy = TokenERC20.deploy(chain3j, credentials,
                Convert.toSha("10", Convert.Unit.GSHA).toBigInteger(),
                BigInteger.valueOf(3000000),
                BigInteger.valueOf(5201314),
                "my token", "mt");
        try {
            TokenERC20 tokenERC20 = deploy.send();
            tokenERC20.isValid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void use(){
        Chain3j chain3j = Chain3j.build(new HttpService("http://127.0.0.1:8545"));
        String contractAddress = null;
        Credentials credentials = null;
        TokenERC20 contract = TokenERC20.load(contractAddress,chain3j,credentials,
                Convert.toSha("10", Convert.Unit.GSHA).toBigInteger(),
                BigInteger.valueOf(100000));
        String myAddress = null;
        String toAddress = null;
        BigInteger amount = BigInteger.ONE;
        try {
            BigInteger balance = contract.balanceOf(myAddress).send();
            TransactionReceipt receipt = contract.transfer(toAddress, amount).send();
            //etc..
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
