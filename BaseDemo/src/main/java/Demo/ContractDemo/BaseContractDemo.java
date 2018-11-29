package Demo.ContractDemo;

import Demo.BaseDemo.BaseDemoApplication;
import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.tx.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseContractDemo{
    private static final Logger log = LoggerFactory.getLogger(BaseDemoApplication.class);

    public static void main(String[] args) throws Exception {

        new BaseContractDemo().run();
    }

    private void run() throws Exception{
        Chain3j chain3j = Chain3j.build(new HttpService("http://127.0.0.1:8545"));

        Credentials credentials = LoadCredentialsFromKeystoreFile("test123");
        String src = credentials.getAddress();
        System.out.println("Load address: " + src);

    }

    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
        return WalletUtils.loadCredentials(
                password, "E:/work/MOAC/Moac core/win/vnode/test/keystore"
                        + "433499700Z--8fc6e625b5491beaea8a81e5799a5c975a4381de");

    }
}
