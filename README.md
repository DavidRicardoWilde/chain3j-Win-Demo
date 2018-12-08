# Chain3j - Demo
<p>Sample program of Java demo based on the Chain3j.</p>

## Development Environment
- IDE: Intellij IDEA or Visual Studio Code
- Moac node
- Java Version: 1.8
- chain3j: The latest version

## Demo
 The demo in BaseDemo package shows some example how to use the Chain3j to connect to Moac.

The demo in ContractDemo includes a sol sample file and ContractEentSample.java, TokenERC20.java. Two Java classes demonstrate how to get smart contract and call the smart contract in Moac.

**[Please read the Demo description for more details.](http://www.baidu.com)**

# Geting start
## Moac Chain
Download [the last version](https://github.com/MOACChain/moac-core).
<p>Create a node and a wallet for testing</p>

>Start a node:
```PowerShell
.\moac-windows-4.0-amd64.exe --testnet --datadir ./testnet --rpc --rpcaddr=0.0.0.0 --rpcapi="chain3, mc, admin, net, vnode, personal" --rpccorsdomain=*
```
>Enter the console:
```PowerShell
.\moac-windows-4.0-amd64.exe attach
```
> Create a wakket:
```PowerShell
personal.newAccount("Your password")
*** or ***
personal.newAccount()
```
**[Please refer to the Console document for more details.](http://www.baidu.com)**

## Chain3j
Download [the latest version](https://github.com/MOACChain/chain3j)

**[Chain3j Document](http://www.baidu.com)**