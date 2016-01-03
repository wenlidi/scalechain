package io.scalechain.blockchain.script

import io.scalechain.blockchain.block._
import io.scalechain.util.HexUtil
import HexUtil._
import io.scalechain.util.HexUtil
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.{Suite, BeforeAndAfterEach, FlatSpec}

case class MergedScript(transaction:Transaction, inputIndex:Int, unlockingScript:UnlockingScript, lockingScript:LockingScript)

/** Test signature validation operations in Crypto.scala
  *
  */
class SignatureCheckingSpec extends FlatSpec with BeforeAndAfterEach with SignatureTestTrait {

  this: Suite =>

  override def beforeEach() {
    // set-up code
    //

    super.beforeEach()
  }

  override def afterEach() {
    super.afterEach()
    // tear-down code
    //
  }

  val transactionInputs =
    Table(
      // column names
      ("subject", "mergedScript"),

      // Summarize the locking/unlocking script as a subject.
      // The MergedScript creating code was copied from the output of DumpChain merge-scripts,
      // which reads all transactions from blkNNNNN.dat file written by the reference implementation.
      (
        "p2pk",
        MergedScript(transaction=Transaction(version=1, inputs=Array(NormalTransactionInput(outputTransactionHash=TransactionHash(bytes("0df43938fa84d440cec1bf0f89644eb93bb5887ba432b0f026c356e16fc20889")), outputIndex=0, unlockingScript=UnlockingScript(bytes("483045022100dc870fd001c3192dbc14a0ddd98cf23aa760ff9923d103c9c2feaf8f2d00bed60220499897a167726d84c064aceb6f9384641368df536886dcbb8106124dfdc8ccde01")) /* ops:ScriptOpList(ops:OpPush(72,ScriptBytes(bytes("3045022100dc870fd001c3192dbc14a0ddd98cf23aa760ff9923d103c9c2feaf8f2d00bed60220499897a167726d84c064aceb6f9384641368df536886dcbb8106124dfdc8ccde01")))), hashType:Some(1) */, sequenceNumber= -1),NormalTransactionInput(outputTransactionHash=TransactionHash(bytes("0852edbdb21769c69f4113c0bf8fd9d0d6b8ddfdd3fd03b39aca63130a945f11")), outputIndex=0, unlockingScript=UnlockingScript(bytes("493046022100ca1610c6d372e37467671bebe77b722fac72044aef22cc32788d399726aafaa7022100af4677e2f214ecec85b207b99bee9417697a66ded3aa775a99df90157689c5f501")) /* ops:ScriptOpList(ops:OpPush(73,ScriptBytes(bytes("3046022100ca1610c6d372e37467671bebe77b722fac72044aef22cc32788d399726aafaa7022100af4677e2f214ecec85b207b99bee9417697a66ded3aa775a99df90157689c5f501")))), hashType:Some(1) */, sequenceNumber= -1),NormalTransactionInput(outputTransactionHash=TransactionHash(bytes("f250813314115d7b50f889cd64a727b23f1306f0afd5097724b4c1016b90e5cd")), outputIndex=0, unlockingScript=UnlockingScript(bytes("483045022017ddfc6b146311a5510bf3a00008e25427254e54094646e5549a08b3cba5e836022100c64a8a4a94370a74ec3d9dcddd8d31df37934a9237e64071c10adccacbcb3eea01")) /* ops:ScriptOpList(ops:OpPush(72,ScriptBytes(bytes("3045022017ddfc6b146311a5510bf3a00008e25427254e54094646e5549a08b3cba5e836022100c64a8a4a94370a74ec3d9dcddd8d31df37934a9237e64071c10adccacbcb3eea01")))), hashType:Some(1) */, sequenceNumber= -1)), outputs=Array(TransactionOutput(value=15000000000L, lockingScript=LockingScript(bytes("4104e4072d4a04889ebfdb48165f7cb0ad5fd586690986c5b2297462955f7d0650b93ab666382292438acc94e07570197dcbaed0f4652b17b800f8e25ece51e243bbac")) /* ops:ScriptOpList(ops:OpPush(65,ScriptBytes(bytes("04e4072d4a04889ebfdb48165f7cb0ad5fd586690986c5b2297462955f7d0650b93ab666382292438acc94e07570197dcbaed0f4652b17b800f8e25ece51e243bb"))),OpCheckSig(Script(bytes("4104e4072d4a04889ebfdb48165f7cb0ad5fd586690986c5b2297462955f7d0650b93ab666382292438acc94e07570197dcbaed0f4652b17b800f8e25ece51e243bbac")))) */ )), lockTime=0 /* hash:bytes("b3a1c28ac943aad288c5d2ad7a30277262d7169c633e8e3d546f27e96b181530") */), inputIndex=1, unlockingScript=UnlockingScript(bytes("493046022100ca1610c6d372e37467671bebe77b722fac72044aef22cc32788d399726aafaa7022100af4677e2f214ecec85b207b99bee9417697a66ded3aa775a99df90157689c5f501")) /* ops:ScriptOpList(ops:OpPush(73,ScriptBytes(bytes("3046022100ca1610c6d372e37467671bebe77b722fac72044aef22cc32788d399726aafaa7022100af4677e2f214ecec85b207b99bee9417697a66ded3aa775a99df90157689c5f501")))), hashType:Some(1) */, lockingScript=LockingScript(bytes("4104772c3ad9afd20d65a7b05c56e8ebd002abed58c49ec5874c6e6eef4a292f5df13998fc05b6b8dc63447e028dd4bbc1c05b872f8527d2b1057e47831028223c3fac")) /* ops:ScriptOpList(ops:OpPush(65,ScriptBytes(bytes("04772c3ad9afd20d65a7b05c56e8ebd002abed58c49ec5874c6e6eef4a292f5df13998fc05b6b8dc63447e028dd4bbc1c05b872f8527d2b1057e47831028223c3f"))),OpCheckSig(Script(bytes("4104772c3ad9afd20d65a7b05c56e8ebd002abed58c49ec5874c6e6eef4a292f5df13998fc05b6b8dc63447e028dd4bbc1c05b872f8527d2b1057e47831028223c3fac")))) */ )
      ),
      (
        "p2pkh uncompressed public key",
        MergedScript(transaction=Transaction(version=1, inputs=Array(NormalTransactionInput(outputTransactionHash=TransactionHash(bytes("1c20641062f0b0aa88c90b3e965832534fa180fb0b644063bd05aab7cef69e78")), outputIndex=1, unlockingScript=UnlockingScript(bytes("483045022100e226e68f61719cc8104c496b0f6e7ea842e2475272d046b3a78e62d00986570f02201643c7bf45ab44ad4dc1c151451b9808c82666c3410b7934f28e6ded0104d57501410409cd755221d250896fcadb560e21e4ee72a3431dfcf16e348dae6ed731d9fb9e5d3de97622c3616031723fe7b930adcdf43e2a08ca60475d865b3f9e83bfc0bb")) /* ops:ScriptOpList(ops:OpPush(72,ScriptBytes(bytes("3045022100e226e68f61719cc8104c496b0f6e7ea842e2475272d046b3a78e62d00986570f02201643c7bf45ab44ad4dc1c151451b9808c82666c3410b7934f28e6ded0104d57501"))),OpPush(65,ScriptBytes(bytes("0409cd755221d250896fcadb560e21e4ee72a3431dfcf16e348dae6ed731d9fb9e5d3de97622c3616031723fe7b930adcdf43e2a08ca60475d865b3f9e83bfc0bb")))), hashType:Some(1) */, sequenceNumber= -1)), outputs=Array(TransactionOutput(value=2100000L, lockingScript=LockingScript(bytes("76a91478ba39266a577f43cf47c07b306636f534beb3ee88ac")) /* ops:ScriptOpList(ops:OpDup(),OpHash160(),OpPush(20,ScriptBytes(bytes("78ba39266a577f43cf47c07b306636f534beb3ee"))),OpEqualVerify(),OpCheckSig(Script(bytes("76a91478ba39266a577f43cf47c07b306636f534beb3ee88ac")))) */ ),TransactionOutput(value=123240000L, lockingScript=LockingScript(bytes("76a9143f59f840d8c9232a6a8794a9c0357060820149a088ac")) /* ops:ScriptOpList(ops:OpDup(),OpHash160(),OpPush(20,ScriptBytes(bytes("3f59f840d8c9232a6a8794a9c0357060820149a0"))),OpEqualVerify(),OpCheckSig(Script(bytes("76a9143f59f840d8c9232a6a8794a9c0357060820149a088ac")))) */ )), lockTime=0 /* hash:bytes("96f012794eb49f0310068e1f22ee6c53c51213dc1c4d329c4339cf750c330f0a") */), inputIndex=0, unlockingScript=UnlockingScript(bytes("483045022100e226e68f61719cc8104c496b0f6e7ea842e2475272d046b3a78e62d00986570f02201643c7bf45ab44ad4dc1c151451b9808c82666c3410b7934f28e6ded0104d57501410409cd755221d250896fcadb560e21e4ee72a3431dfcf16e348dae6ed731d9fb9e5d3de97622c3616031723fe7b930adcdf43e2a08ca60475d865b3f9e83bfc0bb")) /* ops:ScriptOpList(ops:OpPush(72,ScriptBytes(bytes("3045022100e226e68f61719cc8104c496b0f6e7ea842e2475272d046b3a78e62d00986570f02201643c7bf45ab44ad4dc1c151451b9808c82666c3410b7934f28e6ded0104d57501"))),OpPush(65,ScriptBytes(bytes("0409cd755221d250896fcadb560e21e4ee72a3431dfcf16e348dae6ed731d9fb9e5d3de97622c3616031723fe7b930adcdf43e2a08ca60475d865b3f9e83bfc0bb")))), hashType:Some(1) */, lockingScript=LockingScript(bytes("76a9143f59f840d8c9232a6a8794a9c0357060820149a088ac")) /* ops:ScriptOpList(ops:OpDup(),OpHash160(),OpPush(20,ScriptBytes(bytes("3f59f840d8c9232a6a8794a9c0357060820149a0"))),OpEqualVerify(),OpCheckSig(Script(bytes("76a9143f59f840d8c9232a6a8794a9c0357060820149a088ac")))) */ )
      ),
      (
        "p2pkh with compressed public key",
        // Caution : Do not edit this line. This line is copied from the output of DumpChain program.
        MergedScript(transaction=Transaction(version=1, inputs=Array(NormalTransactionInput(outputTransactionHash=TransactionHash(bytes("7b5afb2a1721edee10e4f7af58b6ee509d1ea43308f6f30cf31dfacb67ebf712")), outputIndex=2, unlockingScript=UnlockingScript(bytes("4830450221008f70e4947b1dc666cb6b8296ebb7a9cca3d7eb55740213c7c19673dd9b1b66f40220634bc7e806fbca468aeade5da23160770188582b218174ed91c0bb619771cbc50121031c24239a829a89d7e12a0a5b1456ce60168c2c7dd29b63ea6a2aa8ef64665050")) /* ops:ScriptOpList(ops:OpPush(72,ScriptBytes(bytes("30450221008f70e4947b1dc666cb6b8296ebb7a9cca3d7eb55740213c7c19673dd9b1b66f40220634bc7e806fbca468aeade5da23160770188582b218174ed91c0bb619771cbc501"))),OpPush(33,ScriptBytes(bytes("031c24239a829a89d7e12a0a5b1456ce60168c2c7dd29b63ea6a2aa8ef64665050")))), hashType:Some(1) */, sequenceNumber= -1)), outputs=Array(TransactionOutput(value=100000000, lockingScript=LockingScript(bytes("76a91426b8b5d8bc8548c5176d1d8e9046320dc35d8ff588ac")) /* ops:ScriptOpList(ops:OpDup(),OpHash160(),OpPush(20,ScriptBytes(bytes("26b8b5d8bc8548c5176d1d8e9046320dc35d8ff5"))),OpEqualVerify(),OpCheckSig(Script(bytes("76a91426b8b5d8bc8548c5176d1d8e9046320dc35d8ff588ac")))) */ ),TransactionOutput(value=4504811438L, lockingScript=LockingScript(bytes("76a914425d9c60c16e8364c2125f2560c8d3e847c0827988ac")) /* ops:ScriptOpList(ops:OpDup(),OpHash160(),OpPush(20,ScriptBytes(bytes("425d9c60c16e8364c2125f2560c8d3e847c08279"))),OpEqualVerify(),OpCheckSig(Script(bytes("76a914425d9c60c16e8364c2125f2560c8d3e847c0827988ac")))) */ ),TransactionOutput(value=500000000, lockingScript=LockingScript(bytes("76a914e55ce4ca624664c18f66165780eeb36ee68fbb7888ac")) /* ops:ScriptOpList(ops:OpDup(),OpHash160(),OpPush(20,ScriptBytes(bytes("e55ce4ca624664c18f66165780eeb36ee68fbb78"))),OpEqualVerify(),OpCheckSig(Script(bytes("76a914e55ce4ca624664c18f66165780eeb36ee68fbb7888ac")))) */ )), lockTime=0 /* hash:bytes("a14b51fa2fbcb686e520c463dcf5f19283af7bb8c08cd685c2a09700d027167c") */), inputIndex=0, unlockingScript=UnlockingScript(bytes("4830450221008f70e4947b1dc666cb6b8296ebb7a9cca3d7eb55740213c7c19673dd9b1b66f40220634bc7e806fbca468aeade5da23160770188582b218174ed91c0bb619771cbc50121031c24239a829a89d7e12a0a5b1456ce60168c2c7dd29b63ea6a2aa8ef64665050")) /* ops:ScriptOpList(ops:OpPush(72,ScriptBytes(bytes("30450221008f70e4947b1dc666cb6b8296ebb7a9cca3d7eb55740213c7c19673dd9b1b66f40220634bc7e806fbca468aeade5da23160770188582b218174ed91c0bb619771cbc501"))),OpPush(33,ScriptBytes(bytes("031c24239a829a89d7e12a0a5b1456ce60168c2c7dd29b63ea6a2aa8ef64665050")))), hashType:Some(1) */, lockingScript=LockingScript(bytes("76a914425d9c60c16e8364c2125f2560c8d3e847c0827988ac")) /* ops:ScriptOpList(ops:OpDup(),OpHash160(),OpPush(20,ScriptBytes(bytes("425d9c60c16e8364c2125f2560c8d3e847c08279"))),OpEqualVerify(),OpCheckSig(Script(bytes("76a914425d9c60c16e8364c2125f2560c8d3e847c0827988ac")))) */ )
      ),
      (
        "checkmultisig without p2sh : 2 of 2",
        // Caution : Do not edit this line. This line is copied from the output of DumpChain program.
        MergedScript(transaction=Transaction(version=1, inputs=Array(NormalTransactionInput(outputTransactionHash=TransactionHash(bytes("96e3de38a57646f20dedf9850648c3ac69e7a4f307563401994ca12f8dd42e59")), outputIndex=0, unlockingScript=UnlockingScript(bytes("00483045022100c07ac842dde7cd326ea3c72d33df6e8d2a7af685755343fb270a187d6b6b260c022039fcff7bd36b3abea9273383141af01b02a502ec362fe93cdd28cfdf9606dd1c01483045022100831f1b5f7842fb130ccaff89855bcf402351fd4b103fc9cbbf07b6576035bcb90220294ee3f8c91dbeec7888fccf315170a61755f36290576ada9b45b748591d098201")) /* ops:ScriptOpList(ops:Op0(),OpPush(72,ScriptBytes(bytes("3045022100c07ac842dde7cd326ea3c72d33df6e8d2a7af685755343fb270a187d6b6b260c022039fcff7bd36b3abea9273383141af01b02a502ec362fe93cdd28cfdf9606dd1c01"))),OpPush(72,ScriptBytes(bytes("3045022100831f1b5f7842fb130ccaff89855bcf402351fd4b103fc9cbbf07b6576035bcb90220294ee3f8c91dbeec7888fccf315170a61755f36290576ada9b45b748591d098201")))), hashType:None */, sequenceNumber= -1),NormalTransactionInput(outputTransactionHash=TransactionHash(bytes("8faa026b1e1455f9da6711b2822270be305e7a00c07a704181d4ac8cd1145db8")), outputIndex=0, unlockingScript=UnlockingScript(bytes("00483045022100fd28e743c3ff43b4e235e82db688295ae026761f2086312fe0071c4eb6019377022033af7bd246b110e5f2ee7b58184d5fd1ba9f4fc5a47501fc99a2160085ba7360014730440220367b68dd7c163d16a041cd5106230e0289cb68d74840089a857cf14aac8574b702204abbc8602b94b308145569e053e25a5091b2032000961cf5325b5bc6e12131db01")) /* ops:ScriptOpList(ops:Op0(),OpPush(72,ScriptBytes(bytes("3045022100fd28e743c3ff43b4e235e82db688295ae026761f2086312fe0071c4eb6019377022033af7bd246b110e5f2ee7b58184d5fd1ba9f4fc5a47501fc99a2160085ba736001"))),OpPush(71,ScriptBytes(bytes("30440220367b68dd7c163d16a041cd5106230e0289cb68d74840089a857cf14aac8574b702204abbc8602b94b308145569e053e25a5091b2032000961cf5325b5bc6e12131db01")))), hashType:None */, sequenceNumber= -1)), outputs=Array(TransactionOutput(value=7800L, lockingScript=LockingScript(bytes("522103490c610d5e29a62a905cc3c97225ac1ca56e8677bef2b46e71adc045583b3dc62103e3e869a441cd5646e14d6aecd050545409eb20f069d5a73a3a110cdf11e192f652ae")) /* ops:ScriptOpList(ops:OpNum(2),OpPush(33,ScriptBytes(bytes("03490c610d5e29a62a905cc3c97225ac1ca56e8677bef2b46e71adc045583b3dc6"))),OpPush(33,ScriptBytes(bytes("03e3e869a441cd5646e14d6aecd050545409eb20f069d5a73a3a110cdf11e192f6"))),OpNum(2),OpCheckMultiSig(Script(bytes("522103490c610d5e29a62a905cc3c97225ac1ca56e8677bef2b46e71adc045583b3dc62103e3e869a441cd5646e14d6aecd050545409eb20f069d5a73a3a110cdf11e192f652ae")))) */ ),TransactionOutput(value=7800L, lockingScript=LockingScript(bytes("512103dd1515a32cb25a77370541c81923e1d0813c98eec4b86fa35c1644b97152ab1a2103f76963cd546578960ae2312cc10d41a0251cc02f8277eb645864c3dfbb3f0af7210229cc3dda58b33c1c83a69112a6c960e78f723f792ca03f7f1e1ce021b640573253ae")) /* ops:ScriptOpList(ops:Op1(),OpPush(33,ScriptBytes(bytes("03dd1515a32cb25a77370541c81923e1d0813c98eec4b86fa35c1644b97152ab1a"))),OpPush(33,ScriptBytes(bytes("03f76963cd546578960ae2312cc10d41a0251cc02f8277eb645864c3dfbb3f0af7"))),OpPush(33,ScriptBytes(bytes("0229cc3dda58b33c1c83a69112a6c960e78f723f792ca03f7f1e1ce021b6405732"))),OpNum(3),OpCheckMultiSig(Script(bytes("512103dd1515a32cb25a77370541c81923e1d0813c98eec4b86fa35c1644b97152ab1a2103f76963cd546578960ae2312cc10d41a0251cc02f8277eb645864c3dfbb3f0af7210229cc3dda58b33c1c83a69112a6c960e78f723f792ca03f7f1e1ce021b640573253ae")))) */ ),TransactionOutput(value=10000L, lockingScript=LockingScript(bytes("52210229cc3dda58b33c1c83a69112a6c960e78f723f792ca03f7f1e1ce021b64057322103490c610d5e29a62a905cc3c97225ac1ca56e8677bef2b46e71adc045583b3dc652ae")) /* ops:ScriptOpList(ops:OpNum(2),OpPush(33,ScriptBytes(bytes("0229cc3dda58b33c1c83a69112a6c960e78f723f792ca03f7f1e1ce021b6405732"))),OpPush(33,ScriptBytes(bytes("03490c610d5e29a62a905cc3c97225ac1ca56e8677bef2b46e71adc045583b3dc6"))),OpNum(2),OpCheckMultiSig(Script(bytes("52210229cc3dda58b33c1c83a69112a6c960e78f723f792ca03f7f1e1ce021b64057322103490c610d5e29a62a905cc3c97225ac1ca56e8677bef2b46e71adc045583b3dc652ae")))) */ )), lockTime=0 /* hash:bytes("232b49dbecf6e453a7ee5f93d49a9adc4f07ae1c689894bfaaedbc54933d6751") */), inputIndex=0, unlockingScript=UnlockingScript(bytes("00483045022100c07ac842dde7cd326ea3c72d33df6e8d2a7af685755343fb270a187d6b6b260c022039fcff7bd36b3abea9273383141af01b02a502ec362fe93cdd28cfdf9606dd1c01483045022100831f1b5f7842fb130ccaff89855bcf402351fd4b103fc9cbbf07b6576035bcb90220294ee3f8c91dbeec7888fccf315170a61755f36290576ada9b45b748591d098201")) /* ops:ScriptOpList(ops:Op0(),OpPush(72,ScriptBytes(bytes("3045022100c07ac842dde7cd326ea3c72d33df6e8d2a7af685755343fb270a187d6b6b260c022039fcff7bd36b3abea9273383141af01b02a502ec362fe93cdd28cfdf9606dd1c01"))),OpPush(72,ScriptBytes(bytes("3045022100831f1b5f7842fb130ccaff89855bcf402351fd4b103fc9cbbf07b6576035bcb90220294ee3f8c91dbeec7888fccf315170a61755f36290576ada9b45b748591d098201")))), hashType:None */, lockingScript=LockingScript(bytes("52210229cc3dda58b33c1c83a69112a6c960e78f723f792ca03f7f1e1ce021b64057322103490c610d5e29a62a905cc3c97225ac1ca56e8677bef2b46e71adc045583b3dc652ae")) /* ops:ScriptOpList(ops:OpNum(2),OpPush(33,ScriptBytes(bytes("0229cc3dda58b33c1c83a69112a6c960e78f723f792ca03f7f1e1ce021b6405732"))),OpPush(33,ScriptBytes(bytes("03490c610d5e29a62a905cc3c97225ac1ca56e8677bef2b46e71adc045583b3dc6"))),OpNum(2),OpCheckMultiSig(Script(bytes("52210229cc3dda58b33c1c83a69112a6c960e78f723f792ca03f7f1e1ce021b64057322103490c610d5e29a62a905cc3c97225ac1ca56e8677bef2b46e71adc045583b3dc652ae")))) */ )
      ),
      (
        "p2sh",
        MergedScript(transaction=Transaction(version=1, inputs=Array(NormalTransactionInput(outputTransactionHash=TransactionHash(bytes("7694ba8aaaadd37f8560f1492e7f1de1f70e1178101bab1de70a435e157f0c79")), outputIndex=0, unlockingScript=UnlockingScript(bytes("0047304402207dc87ea88c8a10bde69dbc91f80c827c7b8a3d18122409e358f1e51b54322e9a022042fe00abb6466dc5e4dbd7f982d2ad9f904e6dc69c6f7eb947223e936a00473e01483045022100f2e9163f61e50cf5984b94f3a388b2c13cf33e442bdf217a944a1b482319af1c022060c48f1e696ad084da336c20618dcc12c1cf14cdf0ef3bd1b0c86e026afe78da014c69522102a2a60f3f6ec13028e58e8fb9ccc53aab9391ea542f35a38b5560138b14bd20a62102b6dad19484515162f51ddc5446bc2a3f622f68dd111282b038b9af107be62ad821037e38809356db2eb6b40b0f14666641f00a041996137f7355a8e1745bac3a4cb453ae")) /* ops:ScriptOpList(operations=Array(Op0(),OpPush(71,ScriptBytes(bytes("304402207dc87ea88c8a10bde69dbc91f80c827c7b8a3d18122409e358f1e51b54322e9a022042fe00abb6466dc5e4dbd7f982d2ad9f904e6dc69c6f7eb947223e936a00473e01"))),OpPush(72,ScriptBytes(bytes("3045022100f2e9163f61e50cf5984b94f3a388b2c13cf33e442bdf217a944a1b482319af1c022060c48f1e696ad084da336c20618dcc12c1cf14cdf0ef3bd1b0c86e026afe78da01"))),OpPushData(1,ScriptBytes(bytes("522102a2a60f3f6ec13028e58e8fb9ccc53aab9391ea542f35a38b5560138b14bd20a62102b6dad19484515162f51ddc5446bc2a3f622f68dd111282b038b9af107be62ad821037e38809356db2eb6b40b0f14666641f00a041996137f7355a8e1745bac3a4cb453ae"))))), hashType:None */, sequenceNumber= -1)), outputs=Array(TransactionOutput(value=16337356L, lockingScript=LockingScript(bytes("a91491463c750cff258f7ef5b0aa3dcaea3519ca48ae87")) /* ops:ScriptOpList(operations=Array(OpHash160(),OpPush(20,ScriptBytes(bytes("91463c750cff258f7ef5b0aa3dcaea3519ca48ae"))),OpEqual())) */ ),TransactionOutput(value=880757L, lockingScript=LockingScript(bytes("76a9140e4274d14491be5d5b0ab094b20c52d8abcd795f88ac")) /* ops:ScriptOpList(operations=Array(OpDup(),OpHash160(),OpPush(20,ScriptBytes(bytes("0e4274d14491be5d5b0ab094b20c52d8abcd795f"))),OpEqualVerify(),OpCheckSig(Script(bytes("76a9140e4274d14491be5d5b0ab094b20c52d8abcd795f88ac"))))) */ )), lockTime=0 /* hash:bytes("26e9d2ce8cdfa1e35addaee69065aaadbdd476d40701ea232c2855e8e1115799") */), inputIndex=0, unlockingScript=UnlockingScript(bytes("0047304402207dc87ea88c8a10bde69dbc91f80c827c7b8a3d18122409e358f1e51b54322e9a022042fe00abb6466dc5e4dbd7f982d2ad9f904e6dc69c6f7eb947223e936a00473e01483045022100f2e9163f61e50cf5984b94f3a388b2c13cf33e442bdf217a944a1b482319af1c022060c48f1e696ad084da336c20618dcc12c1cf14cdf0ef3bd1b0c86e026afe78da014c69522102a2a60f3f6ec13028e58e8fb9ccc53aab9391ea542f35a38b5560138b14bd20a62102b6dad19484515162f51ddc5446bc2a3f622f68dd111282b038b9af107be62ad821037e38809356db2eb6b40b0f14666641f00a041996137f7355a8e1745bac3a4cb453ae")) /* ops:ScriptOpList(operations=Array(Op0(),OpPush(71,ScriptBytes(bytes("304402207dc87ea88c8a10bde69dbc91f80c827c7b8a3d18122409e358f1e51b54322e9a022042fe00abb6466dc5e4dbd7f982d2ad9f904e6dc69c6f7eb947223e936a00473e01"))),OpPush(72,ScriptBytes(bytes("3045022100f2e9163f61e50cf5984b94f3a388b2c13cf33e442bdf217a944a1b482319af1c022060c48f1e696ad084da336c20618dcc12c1cf14cdf0ef3bd1b0c86e026afe78da01"))),OpPushData(1,ScriptBytes(bytes("522102a2a60f3f6ec13028e58e8fb9ccc53aab9391ea542f35a38b5560138b14bd20a62102b6dad19484515162f51ddc5446bc2a3f622f68dd111282b038b9af107be62ad821037e38809356db2eb6b40b0f14666641f00a041996137f7355a8e1745bac3a4cb453ae"))))), hashType:None */, lockingScript=LockingScript(bytes("a9140ba8c243f2e21f963cb3c04338b3e0c6918a996c87")) /* ops:ScriptOpList(operations=Array(OpHash160(),OpPush(20,ScriptBytes(bytes("0ba8c243f2e21f963cb3c04338b3e0c6918a996c"))),OpEqual())) */ )
      )
    )

  "scripts" should "be leave true value on top of the stack" in {
    forAll(transactionInputs) { ( subject : String, mergedScript : MergedScript ) =>
      verifyTransactionInput(subject, mergedScript.transaction, mergedScript.inputIndex, mergedScript.lockingScript);
    }
  }


}
