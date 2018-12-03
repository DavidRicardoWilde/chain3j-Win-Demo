package Demo.ContractDemo.GreetContract;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import org.chain3j.abi.FunctionEncoder;
import org.chain3j.abi.TypeReference;
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

//        public Greeter load(String contractAddress, Chain3j chain3j, Credentials credentials, BigInteger initialValue) {
//            return new Greeter(contractAddress, chain3j, credentials, initialValue);
//        }


    }

    private void run() throws Exception {


        Chain3j chain3j = Chain3j.build(new HttpService(
                "http://127.0.0.1:8545"));  // Use local MOAC server;

        System.out.println("Out Connected to MOAC client version: "
                + chain3j.chain3ClientVersion().send().getChain3ClientVersion());

        System.out.println("block number:"
                + chain3j.mcBlockNumber().send().getBlockNumber());

//        System.out.println("net peer Count:"
//                + chain3j.netPeerCount().send().getQuantity());

        //Load the wallet info from a keystore file
        Credentials credentials = LoadCredentialsFromKeystoreFile("test123");   //test123");
        String src = credentials.getAddress();
        System.out.println("Load address: " + src);


        BigInteger gasPrice = chain3j.mcGasPrice().send().getGasPrice();
        BigInteger gasLimit = BigInteger.valueOf(80_00);
        //DefaultGasProvider provider = new DefaultGasProvider();
        String contractBinary = //"";
                //"6080604052600436106100565763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166341c0e1b5811461005b5780634ac0d66e14610072578063cfae3217146100cb575b600080fd5b34801561006757600080fd5b50610070610155565b005b34801561007e57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100709436949293602493928401919081908401838280828437509497506101929650505050505050565b3480156100d757600080fd5b506100e06101a9565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561011a578181015183820152602001610102565b50505050905090810190601f1680156101475780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60005473ffffffffffffffffffffffffffffffffffffffff163314156101905760005473ffffffffffffffffffffffffffffffffffffffff16ff5b565b80516101a590600190602084019061023f565b5050565b60018054604080516020601f600260001961010087891615020190951694909404938401819004810282018101909252828152606093909290918301828280156102345780601f1061020957610100808354040283529160200191610234565b820191906000526020600020905b81548152906001019060200180831161021757829003601f168201915b505050505090505b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061028057805160ff19168380011785556102ad565b828001600101855582156102ad579182015b828111156102ad578251825591602001919060010190610292565b506102b99291506102bd565b5090565b61023c91905b808211156102b957600081556001016102c35600a165627a7a72305820980227808580d89bc4b1f6a11cc3fc2bb2eb4d38676a001df3f51bf9c5267a230029";
                //"0x60806040526004361061006c5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166341c0e1b581146100715780634ac0d66e146100885780639197042c146100e1578063cfae3217146100e1578063fe50cc72146100e1575b600080fd5b34801561007d57600080fd5b5061008661016b565b005b34801561009457600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100869436949293602493928401919081908401838280828437509497506101a89650505050505050565b3480156100ed57600080fd5b506100f66101bf565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610130578181015183820152602001610118565b50505050905090810190601f16801561015d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60005473ffffffffffffffffffffffffffffffffffffffff163314156101a65760005473ffffffffffffffffffffffffffffffffffffffff16ff5b565b80516101bb906001906020840190610255565b5050565b60018054604080516020601f6002600019610100878916150201909516949094049384018190048102820181019092528281526060939092909183018282801561024a5780601f1061021f5761010080835404028352916020019161024a565b820191906000526020600020905b81548152906001019060200180831161022d57829003601f168201915b505050505090505b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061029657805160ff19168380011785556102c3565b828001600101855582156102c3579182015b828111156102c35782518255916020019190600101906102a8565b506102cf9291506102d3565b5090565b61025291905b808211156102cf57600081556001016102d95600a165627a7a723058209b1e177542926537d36bf9bc46b231ae6724f89dd4b20979bb4a9cf2c9d180d70029";
                "123";
        String contractAddress =
                //"02E0C634789026AA93Ccf43444F801105b96BdD2";
                //"80250864C02f15c8Be972E9bE86612aBe91B052C";
                "0x316C14F71334fB3D873fF0F797825C65277B64D5";
        //GreeterContract greeter = new GreeterContract(contractBinary,contractAddress,chain3j,credentials,provider);
        StaticGasProvider gasProvider = new StaticGasProvider(gasPrice,gasLimit);
        GreeterContract greeterContract = new GreeterContract(contractBinary,contractAddress,chain3j,credentials,gasProvider);
        //Greeter loadContract = Greeter.load();
        //GreeterContract greeter = GreeterContract.deployMe(chain3j, credentials,
        //        provider, contractBinary, new Utf8String("Hello world"),
        //        BigInteger.ZERO );
        System.out.println("new Contract");
        System.out.println(greeterContract.isValid());
        if( greeterContract==null ){//isValid())){
            System.out.println(("greeter is null"));
        }

        greeterContract.newGreeting(new Utf8String("hi,change it"));
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

    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
        return WalletUtils.loadCredentials(
                //password, "C:/Users/david/AppData/Roaming/MoacNode/devnet/keystore"
                //       + "/UTC--2018-11-14T16-59-28."
                //       + "398731300Z--533ef68e791d49154d0979c8851fde5455c345cf");
                password, "E:/work/MOAC/Moac core/win/vnode/dev/keystore"
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
