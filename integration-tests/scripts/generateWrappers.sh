#!/usr/bin/env bash

targets="
arrays/build/Arrays
contracts/build/HumanStandardToken
fibonacci/build/Fibonacci
greeter/build/Greeter
shipit/build/ShipIt
simplestorage/build/SimpleStorage
"

for target in ${targets}; do

    chain3j solidity generate \
        ../../codegen/src/test/resources/solidity/${target}.bin \
        ../../codegen/src/test/resources/solidity/${target}.abi \
        -o /Users/Conor/code/java/chain3j/integration-tests/src/test/java \
        -p org.chain3j.generated

done