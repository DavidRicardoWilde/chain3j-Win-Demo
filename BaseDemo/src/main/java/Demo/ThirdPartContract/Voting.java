package Demo.ThirdPartContract;

import org.chain3j.abi.datatypes.generated.Uint256;
import org.chain3j.protocol.Chain3j;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.chain3j.abi.FunctionEncoder;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Bool;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.generated.Bytes32;
import org.chain3j.abi.datatypes.generated.Uint8;
import org.chain3j.crypto.Credentials;
import org.chain3j.protocol.core.RemoteCall;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.tx.Contract;
import org.chain3j.tx.TransactionManager;
import org.chain3j.abi.datatypes.*;

import static org.chain3j.abi.Utils.typeMap;

public class Voting extends Contract{
    private static String BINARY="608060405234801561001057600080fd5b506040516102f23803806102f283398101604052805101805161003a906001906020840190610041565b50506100ab565b82805482825590600052602060002090810192821561007e579160200282015b8281111561007e5782518255602090920191600190910190610061565b5061008a92915061008e565b5090565b6100a891905b8082111561008a5760008155600101610094565b90565b610238806100ba6000396000f30060806040526004361061006c5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632b38cd9681146100715780633477ee2e1461009f57806335154986146100c9578063392e6678146100e3578063b13479081461010f575b600080fd5b34801561007d57600080fd5b50610089600435610127565b6040805160ff9092168252519081900360200190f35b3480156100ab57600080fd5b506100b760043561013c565b60408051918252519081900360200190f35b3480156100d557600080fd5b506100e160043561015b565b005b3480156100ef57600080fd5b506100fb600435610193565b604080519115158252519081900360200190f35b34801561011b57600080fd5b506100896004356101e0565b60006020819052908152604090205460ff1681565b600180548290811061014a57fe5b600091825260209091200154905081565b61016481610193565b151561016f57600080fd5b6000908152602081905260409020805460ff8082166001011660ff19909116179055565b6000805b6001548110156101d55760018054849190839081106101b257fe5b60009182526020909120015414156101cd57600191506101da565b600101610197565b600091505b50919050565b60006101eb82610193565b15156101f657600080fd5b5060009081526020819052604090205460ff16905600a165627a7a723058200439de3968159f3e7e03cd0185c84e0566bfb75e72129e45e1008ce1f2fd05670029";
    public static final String FUNC_VOTES = "votes";

    public static final String FUNC_CANDIDATES = "candidates";

    public static final String FUNC_VOTEFOR = "voteFor";

    public static final String FUNC_VALIDCANDIDATE = "validCandidate";

    public static final String FUNC_GETVOTESFOR = "getVotesFor";

    protected Voting(String contractAddress, Chain3j chain3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit){
        super(BINARY, contractAddress, chain3j, credentials, gasPrice, gasLimit);
    }

    protected Voting(String contractAddress, Chain3j chain3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, chain3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<BigInteger> votes(byte[] param0) {
        final Function function = new Function(FUNC_VOTES,
                Arrays.<Type>asList(new Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<byte[]> candidates(BigInteger param0) {
        final Function function = new Function(FUNC_CANDIDATES,
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> voteFor(byte[] candidate) {
        final Function function = new Function(
                FUNC_VOTEFOR,
                Arrays.<Type>asList(new Bytes32(candidate)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> validCandidate(byte[] candidate) {
        final Function function = new Function(FUNC_VALIDCANDIDATE,
                Arrays.<Type>asList(new Bytes32(candidate)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> getVotesFor(byte[] candidate) {
        final Function function = new Function(FUNC_GETVOTESFOR,
                Arrays.<Type>asList(new Bytes32(candidate)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Voting> deploy(Chain3j chain3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<byte[]> candidateNames) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new DynamicArray<Bytes32>(
                typeMap(candidateNames, Bytes32.class))));
        return deployRemoteCall(Voting.class, chain3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Voting> deploy(Chain3j chain3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<byte[]> candidateNames) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new DynamicArray<Bytes32>(
                typeMap(candidateNames, Bytes32.class))));
        return deployRemoteCall(Voting.class, chain3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Voting load(String contractAddress, Chain3j chain3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Voting(contractAddress, chain3j, credentials, gasPrice, gasLimit);
    }

    public static Voting load(String contractAddress, Chain3j chain3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Voting(contractAddress, chain3j, transactionManager, gasPrice, gasLimit);
    }
}
