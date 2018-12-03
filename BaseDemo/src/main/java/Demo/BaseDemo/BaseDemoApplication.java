package Demo.BaseDemo;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.RawTransaction;
import org.chain3j.crypto.TransactionEncoder;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.DefaultBlockParameter;
import org.chain3j.protocol.core.methods.response.McGetBalance;
import org.chain3j.protocol.core.methods.response.McGetTransactionCount;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.utils.Numeric;

import java.math.BigInteger;

public class BaseDemoApplication {

    private static final Logger log = LoggerFactory.getLogger(BaseDemoApplication.class);

    public static void main(String[] args) throws Exception {

        new BaseDemoApplication().run();
    }

    private void run() throws Exception {

        // We start by creating a new web3j instance to connect to remote nodes on the network.
        // Note: if using chain3j Android, use Chain3jFactory.build(...

        //Chain3j chain3j = Chain3j.build(new HttpService(
        //        "http://gateway.moac.io/testnet"));  // FIXME: Enter your http connection here;
        Chain3j chain3j = Chain3j.build(new HttpService(
                "http://127.0.0.1:8545"));  // Use local MOAC server;

        //log.info("Connected to Moac client version: "
        //        + chain3j.chain3ClientVersion().send().getChain3ClientVersion());

        System.out.println("Out Connected to MOAC client version: "
                + chain3j.chain3ClientVersion().send().getChain3ClientVersion());


        //Load the wallet info from a keystore file
        Credentials credentials = LoadCredentialsFromKeystoreFile("test123");
        String src = credentials.getAddress();
        System.out.println("Load address: " + src);


        // Get the TX count from network and build the TX
        BigInteger srcNonce = chain3j.mcGetTransactionCount(src, DefaultBlockParameter.valueOf("latest")).send().getTransactionCount();
        System.out.println("MOAC testnet account TX count: "
                + srcNonce.toString());
        System.out.println("MOAC testnet account balance: "
                + chain3j.mcGetBalance(src, DefaultBlockParameter.valueOf("latest")).send().getBalance());


        // Build the Raw TX
        BigInteger sendValue = BigInteger.valueOf(1000000000000L);
        String des = "0xE9937C5FA6e7823e7D3611511b4A731814df71fB";
        RawTransaction rawTx  = createTX(srcNonce, des, sendValue);

        // Sign the TX with Credential
        byte[] signedTX = TransactionEncoder.signTxEIP155(rawTx, 100, credentials);
        String signedRawTx = Numeric.toHexString(signedTX);

        System.out.println("Signed RawTX: "+signedRawTx);

        // Send the TX to the network and wait for the results
        System.out.println("MOAC TX send: " + chain3j.mcSendRawTransaction(signedRawTx).send());

    }

    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
        return WalletUtils.loadCredentials(
                password, "C:/Users/david/AppData/Roaming/MoacNode/devnet/keystore"
                        + "/UTC--2018-11-14T16-59-28."
                        + "398731300Z--533ef68e791d49154d0979c8851fde5455c345cf");

    }

    // Send value
    private RawTransaction createTX(BigInteger nonce, String des, BigInteger sendValue) {
        // nonce, gasPrice, gasLimit, des, amount to send in Sha
        return RawTransaction.createMcTransaction(
                nonce,
                BigInteger.valueOf(20000000000L),
                BigInteger.valueOf(21000), des,
                sendValue);
    }

}
