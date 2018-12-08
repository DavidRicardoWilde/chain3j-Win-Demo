package Demo.ContractDemo.GreetContract;

import org.chain3j.abi.FunctionEncoder;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.Utf8String;
import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.DefaultBlockParameter;
import org.chain3j.protocol.core.RemoteCall;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.request.Transaction;
import org.chain3j.protocol.core.methods.response.*;
import org.chain3j.protocol.websocket.events.LogNotification;
import org.chain3j.protocol.websocket.events.NewHeadsNotification;
import org.chain3j.tx.Contract;
import org.chain3j.tx.exceptions.ContractCallException;
import org.chain3j.tx.gas.ContractGasProvider;
import org.chain3j.tx.gas.DefaultGasProvider;
import rx.Observable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import org.chain3j.protocol.http.HttpService;

public class GreeterContract extends Contract {
    public GreeterContract(
            String  contractBinary,
            String contractAddress, Chain3j chain3j, Credentials credentials,
            ContractGasProvider provider
    ){
        super(contractBinary,contractAddress,chain3j, credentials,provider);
        System.out.println("hi,");
    }
    public void init(){
        //super.init();
        System.out.println("this is greetContractClass");
    }
    //public Future<Greeter> deployMe(
    public static  GreeterContract deployMe(
            Chain3j chain3j, Credentials credentials,
            ContractGasProvider provider, String contractBinary, Utf8String _greeting,
            BigInteger initialValue
    ){
        String encodedConstrutor=
                FunctionEncoder.encodeConstructor(
                        Arrays.<Type>asList(_greeting)
                );
        try{
            //Future<Greeter> future =
            return deploy(GreeterContract.class,chain3j,credentials,provider,
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

    //public RemoteCall

   // public Future<Utf8String> greet() {
        public RemoteCall<Utf8String> greet(){
        //Function function = new Function("greet",
        //Function function = new Function("getGreeting",
        Function function = new Function("greet",
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(
                new TypeReference<Utf8String>() {
                }));
        try
        {
            //return executeCallSingleValueReturn(function);  //,String.class);
            return executeRemoteCallSingleValueReturn(function);
        } /*catch (ContractCallException e1) {
            System.out.println("greet contract exception:" + e1);
            return null;
        } */catch (Exception e) {
            System.out.println("basedemo greet exception:" + e);
            return null;
        }
    }
    public RemoteCall<TransactionReceipt> newGreeting(Utf8String _greeting){
        Function function = new Function( "newGreeting",
                Arrays.<Type>asList(_greeting),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(){}
                ));
        return executeRemoteCallTransaction(function);
    }
}
