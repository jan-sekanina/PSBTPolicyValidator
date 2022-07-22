
---
Bitcoin
---

Bitcoin was invented in 2008[2]. It is primary function was to be an online currency, which could be paid with over the internet.
With a boom of it's price people started to treat it like a commodity. And it's creation started a cryptocurrency era which then spawned in great numbers.
Many had different mechanisms, some of them can be considered to be technically improved, some enhanced, some of them are out of technical point of view 
downright worse. To name a few: Etherium, Monero, Dogecoin.

There are many instances of reason that gave an opportunity to it's rise that allowed it to be globally somehow adopted.
I believe that many of these instances are connected to technical solutions that Bitcoin offers. It gives it's 
users a few, whoever stone carved advantages over clasic currencies. Some of them being certain degree
of annonymity or assurance that there will never exist more than 23 000 000 units (which I will further on refer to as bitcoins[1]).
This means there will be no problem with inflation, on the other hand there is a deflation. It allows it's users to pay worldwide without a third person.
Bitcoin is created to be distributed[2] which makes it to a certain degree uncontrolable by anyone, unless he has at his disposal more than half
of all computing power that surges through Bitcoin (this is called 51% attack[2]). For each transaction there is a certain everchanging fee. That is reaped by a miner who
mines the block with the transaction on it. One of cryptographical foundation that Bitcoin is build upon is a Blockchain which is a vital
part in how the currency circulates between wallets and users. It's technical background is secured by cryptography and math.

What it means that miner mined a block? It means that he was lucky and his part of computing power achieved generating a new block out
of hash of the previous block (his parent) and things it found on the internet, computed or guessed. And afterwards was the
It consists of large quantity of blocks of information, which are bound together not unlike a linked list. Each block holds an
information that can be used to assert it's parrent. A very first block was mined by Bitcoin's creator Satoshi Nakamoto[1], it is
called Genesis block. This block holds no such information.
Information on these blocks consists of mostly transactions and movements of bitcoins across the blocks as well as an amount of newly
mined bitcoins, which could be further decoded into new addresses which the successful miner generated and to which he presumably
holds a secret that could be imagined as a ticket to access these 'coins'.

The amount of mined bitcoins is not static. It is continuously decreesed by half of the amount in a process called Halving until
there will be none bitcoin mined. And the blocks will be created purely for transactions and fees they bring to the miner.
Therefore there will be constantly a decreesing amount which will be increasing total amount of bitcoins up to 21 000 000.
(times 100 000 000 satoshi). This all is achieved by clever usage of hash functions. 
The more different miners participate in the mining and the more evenly is computing power distributed among them, the lower is the chance
that someone is able to get lucky first and then create another block many times in a row. Chances are rising exponentially with each block.
(assuming that chance to mine a block is equal to one's mining share on global stage)

Before the transaction is agreed upon by general majority of miners and is approved and permamently written on Blockchain, it needs to be validated.
Transactions are hashed in a Merkle Tree[1], which significantly saves the ammount of previous hashes needed to validate a block.
Inside of bitcoin wallet are no coins, there are tickets to move coins on Blockchain down the chain. Whoever holds a ticket is authorized to pay with
the coins or move them to a another wallet and much more. Transactions are going to be of a great interest to us in this list we will be looking at
mosty Partially Signed Bitcoin Transactions which we will be finalizing under certain cirmcumstances.

1. "Bitcoin: A Peer-to-Peer Electronic Cash System," Satoshi Nakamoto (https://bitcoin.org/bitcoin.pdf). 
2. bitcoinbook, author (https://github.com/bitcoinbook/bitcoinbook/blob/develop/ch01.asciidoc).

To ignore: keywords: transaction, structure, partially signed, transaction creation, transaction finilazing, signing, writing on Blockchain, transaction fee
