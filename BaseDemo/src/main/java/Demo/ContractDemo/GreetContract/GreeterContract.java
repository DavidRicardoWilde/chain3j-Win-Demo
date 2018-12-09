package Demo.ContractDemo.GreetContract;

import org.chain3j.abi.FunctionEncoder;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.Utf8String;
import org.chain3j.crypto.Credentials;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.RemoteCall;
import org.chain3j.protocol.core.methods.response.*;
import org.chain3j.protocol.exceptions.TransactionException;
import org.chain3j.tx.Contract;
import org.chain3j.tx.exceptions.ContractCallException;
import org.chain3j.tx.gas.ContractGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class GreeterContract extends Contract {
    public GreeterContract(
            String  contractBinary,
            String contractAddress, Chain3j chain3j, Credentials credentials,
            ContractGasProvider provider
    ){
        super(contractBinary,contractAddress,chain3j, credentials,provider);
    }

//    private GreeterContract(String contractAddress, Chain3j chain3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
//        super(contractAddress, chain3j, credentials, gasPrice, gasLimit);
//    }



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
        //Function function = new Function("greet",bi
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

    //sign
    public RemoteCall<TransactionReceipt> newGreeting(Utf8String _greeting){
        Function function = new Function( "newGreeting",
                Arrays.<Type>asList(_greeting),
                //Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(){})
                Collections.<TypeReference<?>>emptyList()
                );
        return executeRemoteCallTransaction(function);
        //return exe(function);
    }

    //without sign

//    public Future<TransactionReceipt> newGreeting(Utf8String _greeting) throws Exception{
//        Function function = new Function("newGreeting", Arrays.<Type>asList(_greeting), Collections.<TypeReference<?>>emptyList());
//        return (Future<TransactionReceipt>) executeTransaction(function);
//    }



//    public TransactionReceipt newGreeting(String _greeting) throws IOException, TransactionException {
//        Function function = new Function( "newGreeting",
//                Arrays.<Type>asList(new Utf8String(_greeting)),
//                //Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(){})
//                Collections.<TypeReference<?>>emptyList()
//        );
//        //return executeTransaction(function);
//        return executeCallSingleValueReturn(function);
//    }



}
