package main;

public class TransactionsImported {
    //transaction 1 - 10 are PSBTv0
    public static String validTransaction1 = "70736274ff0100750200000001268171371edff285e937adeea4b37b78000c0566cbb3ad64641713ca42171bf60000000000feffffff02d3dff505000000001976a914d0c59903c5bac2868760e90fd521a4665aa7652088ac00e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787b32e1300000100fda5010100000000010289a3c71eab4d20e0371bbba4cc698fa295c9463afa2e397f8533ccb62f9567e50100000017160014be18d152a9b012039daf3da7de4f53349eecb985ffffffff86f8aa43a71dff1448893a530a7237ef6b4608bbb2dd2d0171e63aec6a4890b40100000017160014fe3e9ef1a745e974d902c4355943abcb34bd5353ffffffff0200c2eb0b000000001976a91485cff1097fd9e008bb34af709c62197b38978a4888ac72fef84e2c00000017a914339725ba21efd62ac753a9bcd067d6c7a6a39d05870247304402202712be22e0270f394f568311dc7ca9a68970b8025fdd3b240229f07f8a5f3a240220018b38d7dcd314e734c9276bd6fb40f673325bc4baa144c800d2f2f02db2765c012103d2e15674941bad4a996372cb87e1856d3652606d98562fe39c5e9e7e413f210502483045022100d12b852d85dcd961d2f5f4ab660654df6eedcc794c0c33ce5cc309ffb5fce58d022067338a8e0e1725c197fb1a88af59f51e44e4255b20167c8684031c05d1f2592a01210223b72beef0965d10be0778efecd61fcac6f79a4ea169393380734464f84f2ab300000000000000";
    public static String validTransaction2 = "70736274ff0100a00200000002ab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40000000000feffffffab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40100000000feffffff02603bea0b000000001976a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac8e240000000000001976a9146f4620b553fa095e721b9ee0efe9fa039cca459788ac000000000001076a47304402204759661797c01b036b25928948686218347d89864b719e1f7fcf57d1e511658702205309eabf56aa4d8891ffd111fdf1336f3a29da866d7f8486d75546ceedaf93190121035cdc61fc7ba971c0b501a646a2a83b102cb43881217ca682dc86e2d73fa882920001012000e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787010416001485d13537f2e265405a34dbafa9e3dda01fb82308000000";
    public static String validTransaction3 = "70736274ff0100750200000001268171371edff285e937adeea4b37b78000c0566cbb3ad64641713ca42171bf60000000000feffffff02d3dff505000000001976a914d0c59903c5bac2868760e90fd521a4665aa7652088ac00e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787b32e1300000100fda5010100000000010289a3c71eab4d20e0371bbba4cc698fa295c9463afa2e397f8533ccb62f9567e50100000017160014be18d152a9b012039daf3da7de4f53349eecb985ffffffff86f8aa43a71dff1448893a530a7237ef6b4608bbb2dd2d0171e63aec6a4890b40100000017160014fe3e9ef1a745e974d902c4355943abcb34bd5353ffffffff0200c2eb0b000000001976a91485cff1097fd9e008bb34af709c62197b38978a4888ac72fef84e2c00000017a914339725ba21efd62ac753a9bcd067d6c7a6a39d05870247304402202712be22e0270f394f568311dc7ca9a68970b8025fdd3b240229f07f8a5f3a240220018b38d7dcd314e734c9276bd6fb40f673325bc4baa144c800d2f2f02db2765c012103d2e15674941bad4a996372cb87e1856d3652606d98562fe39c5e9e7e413f210502483045022100d12b852d85dcd961d2f5f4ab660654df6eedcc794c0c33ce5cc309ffb5fce58d022067338a8e0e1725c197fb1a88af59f51e44e4255b20167c8684031c05d1f2592a01210223b72beef0965d10be0778efecd61fcac6f79a4ea169393380734464f84f2ab30000000001030401000000000000";
    public static String validTransaction4 = "70736274ff0100a00200000002ab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40000000000feffffffab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40100000000feffffff02603bea0b000000001976a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac8e240000000000001976a9146f4620b553fa095e721b9ee0efe9fa039cca459788ac00000000000100df0200000001268171371edff285e937adeea4b37b78000c0566cbb3ad64641713ca42171bf6000000006a473044022070b2245123e6bf474d60c5b50c043d4c691a5d2435f09a34a7662a9dc251790a022001329ca9dacf280bdf30740ec0390422422c81cb45839457aeb76fc12edd95b3012102657d118d3357b8e0f4c2cd46db7b39f6d9c38d9a70abcb9b2de5dc8dbfe4ce31feffffff02d3dff505000000001976a914d0c59903c5bac2868760e90fd521a4665aa7652088ac00e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787b32e13000001012000e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787010416001485d13537f2e265405a34dbafa9e3dda01fb8230800220202ead596687ca806043edc3de116cdf29d5e9257c196cd055cf698c8d02bf24e9910b4a6ba670000008000000080020000800022020394f62be9df19952c5587768aeb7698061ad2c4a25c894f47d8c162b4d7213d0510b4a6ba6700000080010000800200008000";
    public static String validTransaction5 = "70736274ff0100550200000001279a2323a5dfb51fc45f220fa58b0fc13e1e3342792a85d7e36cd6333b5cbc390000000000ffffffff01a05aea0b000000001976a914ffe9c0061097cc3b636f2cb0460fa4fc427d2b4588ac0000000000010120955eea0b0000000017a9146345200f68d189e1adc0df1c4d16ea8f14c0dbeb87220203b1341ccba7683b6af4f1238cd6e97e7167d569fac47f1e48d47541844355bd4646304302200424b58effaaa694e1559ea5c93bbfd4a89064224055cdf070b6771469442d07021f5c8eb0fea6516d60b8acb33ad64ede60e8785bfb3aa94b99bdf86151db9a9a010104220020771fd18ad459666dd49f3d564e3dbc42f4c84774e360ada16816a8ed488d5681010547522103b1341ccba7683b6af4f1238cd6e97e7167d569fac47f1e48d47541844355bd462103de55d1e1dac805e3f8a58c1fbf9b94c02f3dbaafe127fefca4995f26f82083bd52ae220603b1341ccba7683b6af4f1238cd6e97e7167d569fac47f1e48d47541844355bd4610b4a6ba67000000800000008004000080220603de55d1e1dac805e3f8a58c1fbf9b94c02f3dbaafe127fefca4995f26f82083bd10b4a6ba670000008000000080050000800000";
    public static String validTransaction6 = "70736274ff01005202000000019dfc6628c26c5899fe1bd3dc338665bfd55d7ada10f6220973df2d386dec12760100000000ffffffff01f03dcd1d000000001600147b3a00bfdc14d27795c2b74901d09da6ef133579000000004f01043587cf02da3fd0088000000097048b1ad0445b1ec8275517727c87b4e4ebc18a203ffa0f94c01566bd38e9000351b743887ee1d40dc32a6043724f2d6459b3b5a4d73daec8fbae0472f3bc43e20cd90c6a4fae000080000000804f01043587cf02da3fd00880000001b90452427139cd78c2cff2444be353cd58605e3e513285e528b407fae3f6173503d30a5e97c8adbc557dac2ad9a7e39c1722ebac69e668b6f2667cc1d671c83cab0cd90c6a4fae000080010000800001012b0065cd1d000000002200202c5486126c4978079a814e13715d65f36459e4d6ccaded266d0508645bafa6320105475221029da12cdb5b235692b91536afefe5c91c3ab9473d8e43b533836ab456299c88712103372b34234ed7cf9c1fea5d05d441557927be9542b162eb02e1ab2ce80224c00b52ae2206029da12cdb5b235692b91536afefe5c91c3ab9473d8e43b533836ab456299c887110d90c6a4fae0000800000008000000000220603372b34234ed7cf9c1fea5d05d441557927be9542b162eb02e1ab2ce80224c00b10d90c6a4fae0000800100008000000000002202039eff1f547a1d5f92dfa2ba7af6ac971a4bd03ba4a734b03156a256b8ad3a1ef910ede45cc500000080000000800100008000"; // sparrow refuses to open this transaction
    public static String validTransaction7 = "70736274ff01003f0200000001ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0000000000ffffffff010000000000000000036a010000000000000af00102030405060708090f0102030405060708090a0b0c0d0e0f0000";
    public static String validTransaction8 = "70736274ff01009d0100000002710ea76ab45c5cb6438e607e59cc037626981805ae9e0dfd9089012abb0be5350100000000ffffffff190994d6a8b3c8c82ccbcfb2fba4106aa06639b872a8d447465c0d42588d6d670000000000ffffffff0200e1f505000000001976a914b6bc2c0ee5655a843d79afedd0ccc3f7dd64340988ac605af405000000001600141188ef8e4ce0449eaac8fb141cbf5a1176e6a088000000004f010488b21e039e530cac800000003dbc8a5c9769f031b17e77fea1518603221a18fd18f2b9a54c6c8c1ac75cbc3502f230584b155d1c7f1cd45120a653c48d650b431b67c5b2c13f27d7142037c1691027569c503100008000000080000000800001011f00e1f5050000000016001433b982f91b28f160c920b4ab95e58ce50dda3a4a220203309680f33c7de38ea6a47cd4ecd66f1f5a49747c6ffb8808ed09039243e3ad5c47304402202d704ced830c56a909344bd742b6852dccd103e963bae92d38e75254d2bb424502202d86c437195df46c0ceda084f2a291c3da2d64070f76bf9b90b195e7ef28f77201220603309680f33c7de38ea6a47cd4ecd66f1f5a49747c6ffb8808ed09039243e3ad5c1827569c5031000080000000800000008000000000010000000001011f00e1f50500000000160014388fb944307eb77ef45197d0b0b245e079f011de220202c777161f73d0b7c72b9ee7bde650293d13f095bc7656ad1f525da5fd2e10b11047304402204cb1fb5f869c942e0e26100576125439179ae88dca8a9dc3ba08f7953988faa60220521f49ca791c27d70e273c9b14616985909361e25be274ea200d7e08827e514d01220602c777161f73d0b7c72b9ee7bde650293d13f095bc7656ad1f525da5fd2e10b1101827569c5031000080000000800000008000000000000000000000220202d20ca502ee289686d21815bd43a80637b0698e1fbcdbe4caed445f6c1a0a90ef1827569c50310000800000008000000080000000000400000000";
    public static String validTransaction9 = "70736274ff01000a0000000000000000000000"; // sparrow refuses to open this transaction
    public static String validTransaction10 = "70736274ff01004c020000000002d3dff505000000001976a914d0c59903c5bac2868760e90fd521a4665aa7652088ac00e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787b32e1300000000"; // sparrow refuses to open this transaction

    //transaction 11+ are PSBTv2
    // taken from: https://github.com/bitcoin/bips/blob/master/bip-0370.mediawiki
    public static String validTransaction11 = "70736274ff01020402000000010401010105010201fb040200000000010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f0400000000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction12 = "70736274ff01020402000000010401010105010201fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f040000000000220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction13 = "70736274ff01020402000000010401010105010201fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f0400000000011004feffffff00220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction14 = "70736274ff0102040200000001030400000000010401010105010201fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f0400000000011004feffffff0111048c8dc4620112041027000000220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction15 = "70736274ff0102040200000001040101010501020106010101fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f040000000000220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction16 = "70736274ff0102040200000001040101010501020106010201fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f040000000000220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction17 = "70736274ff0102040200000001040101010501020106010401fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f040000000000220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction18 = "70736274ff0102040200000001040101010501020106010801fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f040000000000220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction19 = "70736274ff0102040200000001040101010501020106010301fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f040000000000220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction20 = "70736274ff0102040200000001040101010501020106010501fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f040000000000220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction21 = "70736274ff0102040200000001040101010501020106010601fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f040000000000220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    public static String validTransaction22 = "70736274ff0102040200000001040101010501020106010601fb0402000000000100520200000001c1aa256e214b96a1822f93de42bff3b5f3ff8d0519306e3515d7515a5e805b120000000000ffffffff0118c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e0000000001011f18c69a3b00000000160014b0a3af144208412693ca7d166852b52db0aef06e010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f040000000000220202d601f84846a6755f776be00e3d9de8fb10acc935fb83c45fb0162d4cad5ab79218f69d873e540000800100008000000080000000002a0000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c00220202e36fbff53dd534070cf8fd396614680f357a9b85db7340bf1cfa745d2ad7b34018f69d873e54000080010000800000008001000000640000000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300";
    // transaction were copied how they were, they seem kinda same to me tho
}
