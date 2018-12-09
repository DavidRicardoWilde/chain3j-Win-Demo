package Demo.ContractDemo.GreetContract;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import org.chain3j.abi.FunctionEncoder;
import org.chain3j.abi.TypeReference;
import org.chain3j.protocol.admin.Admin;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.tx.exceptions.ContractCallException;
import org.chain3j.tx.gas.DefaultGasProvider;
import org.chain3j.tx.gas.StaticGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.concurrent.Future;


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
import org.chain3j.tx.gas.DefaultGasProvider;
import org.chain3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import org.chain3j.tx.Contract;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.protocol.core.RemoteCall;

import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.Utf8String;

public class MyContract {
    private static final Logger log = LoggerFactory.getLogger(MyContract.class);

    public static void main(String[] args) throws Exception {

        new MyContract().run();
    }

    public class Greeter extends Contract{
        /* private Greeter(
                 String contractAddress,Chain3j chain3j,Credentials credentials,
                 BigInteger gasPrice,BigInteger gasLimit
         ){
             //String constractBinary = "";
             //super(contractAddress,chain3j,credentials,gasPrice,gasLimit);
            // DefaultGasProvider provider = new DefaultGasProvider();
            // super(constractBinary,contractAddress,chain3j, credentials,provider);
         }*/
        private Greeter(
                String  contractBinary,
                String contractAddress,Chain3j chain3j,Credentials credentials,
                ContractGasProvider provider
        ){
            //String constractBinary = "";
            //super(contractAddress,chain3j,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);//gasPrice,gasLimit);
            // DefaultGasProvider provider = new DefaultGasProvider();
            super(contractBinary,contractAddress,chain3j, credentials,provider);
        }

        //public Future<Greeter> deployMe(
        public Greeter deployMe(
                Chain3j chain3j,Credentials credentials,
                ContractGasProvider provider,String contractBinary,Utf8String _greeting,
                BigInteger initialValue
        ){
            String encodedConstrutor=
                    FunctionEncoder.encodeConstructor(
                            Arrays.<Type>asList(_greeting)
                    );
            try{
                //Future<Greeter> future =
                return deploy(Greeter.class,chain3j,credentials,provider,
                        contractBinary,encodedConstrutor,initialValue); //.get();
                //return greeter;
            }catch(ContractCallException e1){
                System.out.println("greet contract exception:"+e1);
                return null;
            }
            catch(Exception e){
                System.out.println("deploy exception:"+e);
                return null;
            }
        }

        //public Future<Utf8String> greet() {
            public RemoteCall<Utf8String> greet(){
            //Function function = new Function("greet",
            //Function function = new Function("getGreeting",
            Function function = new Function("getGreeting",
                    Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(
                    new TypeReference<Utf8String>() {
                    }));
            try
            {
                //return executeCallSingleValueReturn(function);  //,String.class);
                return executeRemoteCallSingleValueReturn(function);
            }catch(ContractCallException e1){
                System.out.println("greet contract exception:"+e1);
                return null;
            }catch (Exception e){
                System.out.println("basedemo greet exception:"+e);
                return null;
            }

        }
    }

    private void run() throws Exception {


        Chain3j chain3j = Chain3j.build(new HttpService(
                "http://127.0.0.1:8545"));  // Use local MOAC server;
//        Admin chain3j = Admin.build(new HttpService("http://127.0.0.1:8545"));

//        System.out.println("Out Connected to MOAC client version: "
//                + chain3j.chain3ClientVersion().send().getChain3ClientVersion());
//
//        System.out.println("block number:"
//                + chain3j.mcBlockNumber().send().getBlockNumber());

//        System.out.println("net peer Count:"
//                + chain3j.netPeerCount().send().getQuantity());

        //Load the wallet info from a keystore file
        Credentials credentials = LoadCredentialsFromKeystoreFile("test123");   //test123");
//        String src = credentials.getAddress();
//        System.out.println("Load address: " + src);


        //BigInteger gasPrice = chain3j.mcGasPrice().send().getGasPrice();
        BigInteger gasLimit = BigInteger.valueOf(80_000);
        //DefaultGasProvider provider = new DefaultGasProvider();
        String contractBinary = "123";
        String contractAddress =
                "0x316C14F71334fB3D873fF0F797825C65277B64D5";
                //"0x316C14F71334fB3D873fF0F797825C65277B64D5"; //"greet" smart cotract on private chain

        //GreeterContract greeter = new GreeterContract(contractBinary,contractAddress,chain3j,credentials,provider);
        //String chainId = chain3j.netVersion().send().getNetVersion();
        StaticGasProvider gasProvider = new StaticGasProvider(BigInteger.valueOf(80_000_000),gasLimit);
        GreeterContract greeterContract = new GreeterContract(contractBinary,contractAddress,chain3j,credentials,gasProvider);
        //Greeter loadContract = Greeter.load();
        //GreeterContract greeter = GreeterContract.deployMe(chain3j, credentials,
        //        provider, contractBinary, new Utf8String("Hello world"),
        //        BigInteger.ZERO );
        System.out.println("new Contract");
        //System.out.println(greeterContract.isValid());
        if( greeterContract==null ){//isValid())){
            System.out.println(("greeter is null"));
        }

        //greeterContract.newGreeting("hi,change it").sendAsync();
        greeterContract.newGreeting(new Utf8String("hi,change it 2018")).send();
        //greeterContract.newGreeting("hi,change it");
        //TransactionReceipt tReceipt = greeterContract.newGreeting("123321").get()
        System.out.println("have using newGreetng");

//        //Future<Utf8String> valueFuture =
        try{
            System.out.println("test123321 "+greeterContract.greet().send());
        }catch (Exception e){
            e.printStackTrace();
        }
//        try{
//            Utf8String test2 = greeterContract.greet().send();
//            if(test2==null){
//                System.out.println("test 2 is null");
//            }else{
//                System.out.println("test123321 "+test2.getValue());
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }



        //Utf8String test2 = greeterContract.greet().send();
        //String valueString = test2.getTypeAsString();
       //System.out.println("test1121 = "+valueString);
//        if(valueFuture == null){
//            System.out.println("future is null");
//        }
        //Utf8String valueUtf8 = valueFuture.get();
        //String valueString = valueUtf8.getTypeAsString();


//        System.out.println("geeter greet:"+
//                greeter.greet().get().getTypeAsString());
        //System.out.println("greeter greet:"+
        //greeter.greet().send());

//        // Build the Raw TX
//        BigInteger sendValue = BigInteger.valueOf(1000000000000L);
//        String des = "0x7312F4B8A4457a36827f185325Fd6B66a3f8BB8B";
//        RawTransaction rawTx  = createTX(srcNonce, des, sendValue);
//
//        // Sign the TX with Credential
//        byte[] signedTX = TransactionEncoder.signTxEIP155(rawTx, 100, credentials);
//        String signedRawTx = Numeric.toHexString(signedTX);
//
//        System.out.println("Signed RawTX: "+signedRawTx);
//
//        // Send the TX to the network and wait for the results
//        System.out.println("MOAC TX send: " + chain3j.mcSendRawTransaction(signedRawTx).send());

    }


    //private chain
//    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
//        return WalletUtils.loadCredentials(
//                //password, "C:/Users/david/AppData/Roaming/MoacNode/devnet/keystore"
//                //       + "/UTC--2018-11-14T16-59-28."
//                //       + "398731300Z--533ef68e791d49154d0979c8851fde5455c345cf");
//                password, "E:/work/MOAC/Moac core/win/vnode/dev/keystore"
//                        +"/UTC--2018-12-01T03-07-20.204507100Z--1bc165d9015229c99b9f984a9104b57da5bf39b0");
//
//    }

    //testnet chain
    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
        return WalletUtils.loadCredentials(
                password, "E:\\work\\MOAC\\Moac core\\win\\vnode\\dev\\keystore"
                        +"/UTC--2018-12-01T03-07-20.204507100Z--1bc165d9015229c99b9f984a9104b57da5bf39b0");

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
