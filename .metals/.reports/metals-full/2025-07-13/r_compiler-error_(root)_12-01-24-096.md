file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala
### java.lang.IndexOutOfBoundsException: -1 is out of bounds (min 0, max 2)

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.12
Classpath:
<WORKSPACE>/.bloop<HOME>/bloop-bsp-clients-classes/classes-Metals-6B5QlsqPQPaGwYPmhUlC6g== [exists ], <HOME>/.cache/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.4/semanticdb-javac-0.10.4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/chipsalliance/chisel_2.13/5.1.0/chisel_2.13-5.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/scopt/scopt_2.13/3.7.1/scopt_2.13-3.7.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/net/jcazevedo/moultingyaml_2.13/0.4.2/moultingyaml_2.13-0.4.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native_2.13/4.0.6/json4s-native_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-text/1.10.0/commons-text-1.10.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/github/alexarchambault/data-class_2.13/0.2.5/data-class_2.13-0.2.5.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/os-lib_2.13/0.8.1/os-lib_2.13-0.8.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parallel-collections_2.13/1.0.4/scala-parallel-collections_2.13-1.0.4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle_2.13/2.0.0/upickle_2.13-2.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/nscala-time/nscala-time_2.13/2.22.0/nscala-time_2.13-2.22.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/yaml/snakeyaml/1.26/snakeyaml-1.26.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-core_2.13/4.0.6/json4s-core_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native-core_2.13/4.0.6/json4s-native-core_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/geny_2.13/0.7.1/geny_2.13-0.7.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/ujson_2.13/2.0.0/ujson_2.13-2.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upack_2.13/2.0.0/upack_2.13-2.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-implicits_2.13/2.0.0/upickle-implicits_2.13-2.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/joda-time/joda-time/2.10.1/joda-time-2.10.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/joda/joda-convert/2.2.0/joda-convert-2.2.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-ast_2.13/4.0.6/json4s-ast_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-scalap_2.13/4.0.6/json4s-scalap_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/thoughtworks/paranamer/paranamer/2.8/paranamer-2.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-core_2.13/2.0.0/upickle-core_2.13-2.0.0.jar [exists ]
Options:
-language:reflectiveCalls -deprecation -feature -Xcheckinit -Ymacro-annotations -Yrangepos -Xplugin-require:semanticdb


action parameters:
uri: file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala
text:
```scala
package Lab4s

import chisel3._
import chisel3.util._
import scala.annotation.compileTimeOnly
import os.read.inputStream
import Adder.Uint32_Adder

class SGMatMulModule(cfg:MatMulConfig) extends Module 
{

     val io = IO(new Bundle {
        // 访问memeory的IOs
        val addr = Output(UInt(log2Ceil(cfg.memory_size).W))
        val writeEn = Output(Bool())
        val enable = Output(Bool())
        val dataIn = Input(SInt(cfg.memory_width.W))
        val dataOut = Output(SInt(cfg.memory_width.W))

        // 开始执行信号
        val start=Input(Bool())

        // 是否执行完成信号 busy是true.B表示正在执行
        val busy=Output(Bool())

        val reg_writeEn = Input(Bool())
        val reg_sel = Input(UInt(2.W))
        val reg_data = Input(UInt(cfg.vecDWidth.W))
    })
    val loadA_buf=RegInit(0.U(cfg.gemm_matsize.W))
    val loadB_buf=RegInit(0.S(cfg.gemm_matsize.W))

    val matA_buf = Seq.fill(cfg.gemm_matsize,cfg.gemm_matsize)(Reg(UInt(cfg.vecDWidth.W)))
    val matB_buf = Seq.fill(cfg.gemm_matsize,cfg.gemm_matsize)(Reg(SInt(cfg.vecDWidth.W)))
    val matC_buf = Seq.fill(cfg.gemm_matsize,cfg.gemm_matsize)(Reg(SInt(cfg.resDWidth.W)))

    val reg_m = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))
    val reg_k = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))
    val reg_n = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))    

    val buf_sel = RegInit((0.U(log2Ceil(3).W)))
    val cross_cnt = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))
    val load_index = RegInit(0.U(log2Ceil(cfg.gemm_matsize).W))
    val row_cnt = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W))) 
    val col_cnt = RegInit((0.U(log2Ceil(cfg.gemm_matsize).W))) 

    io.busy := true.B
    io.writeEn := false.B
    io.enable := false.B
    io.dataOut := 0.S

    io.addr:=MuxCase(0.U,Seq(
        (buf_sel===cfg.SEL_A) -> (cfg.matA_baseAddr.U+(load_index*cfg.gemm_matsize.U)+cross_cnt-load_index),
        (buf_sel===cfg.SEL_B) -> (cfg.matB_baseAddr.U+((cross_cnt-load_index)*cfg.gemm_matsize.U+load_index)),
        (buf_sel===cfg.SEL_C) -> (cfg.matC_baseAddr.U+row_cnt*cfg.gemm_matsize.U+col_cnt)
    ))
    for(i<- 0 until cfg.gemm_matsize)
    {
        val en_load_A = RegNext(io.enable) & i.U===RegNext(load_index) & RegNext(buf_sel)===cfg.SEL_A
        val in_range_A = (i.U < cfg.gemm_matsize.U) & (RegNext(cross_cnt)- i.U < cfg.gemm_matsize.U)
        loadA_buf(i):=MuxCase(loadA_buf(i),Seq(
            (en_load_A & in_range_A) -> io.dataIn.asUInt,
            (en_load_A & !in_range_A) -> 0.U
        ))
    }
    for(i<- 0 until cfg.gemm_matsize)
    {
        val en_load_B = RegNext(io.enable) & i.U===RegNext(load_index) & RegNext(buf_sel)===cfg.SEL_B
        val in_range_B = (i.U < cfg.gemm_matsize.U) & (RegNext(cross_cnt)- i.U < cfg.gemm_matsize.U)
        loadA_buf(i):=MuxCase(loadA_buf(i),Seq(
            (en_load_B & in_range_B) -> io.dataIn.asUInt,
            (en_load_B & !in_range_B) -> 0.U
        ))
    }
    val start :: load :: lfinish :: compute :: cfinish :: write :: wfinish :: Nil = Enum(7)
    val state=RegInit(start)
    switch(state)
    {
        is(start)
        {
            when(io.start)
            {
                state:=loa
            }
        }
    }
 }
```



#### Error stacktrace:

```
scala.collection.mutable.ArrayBuffer.apply(ArrayBuffer.scala:106)
	scala.reflect.internal.Types$Type.findMemberInternal$1(Types.scala:1030)
	scala.reflect.internal.Types$Type.findMember(Types.scala:1035)
	scala.reflect.internal.Types$Type.memberBasedOnName(Types.scala:661)
	scala.reflect.internal.Types$Type.member(Types.scala:625)
	scala.tools.nsc.typechecker.Contexts$SymbolLookup.apply(Contexts.scala:1435)
	scala.tools.nsc.typechecker.Contexts$Context.lookupSymbol(Contexts.scala:1286)
	scala.tools.nsc.typechecker.Typers$Typer.typedIdent$2(Typers.scala:5572)
	scala.tools.nsc.typechecker.Typers$Typer.typedIdentOrWildcard$1(Typers.scala:5631)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6095)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedSelectOrSuperCall$1(Typers.scala:6251)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6098)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.computeType(Typers.scala:6242)
	scala.tools.nsc.typechecker.Namers$Namer.assignTypeToTree(Namers.scala:1137)
	scala.tools.nsc.typechecker.Namers$Namer.inferredValTpt$1(Namers.scala:1775)
	scala.tools.nsc.typechecker.Namers$Namer.valDefSig(Namers.scala:1788)
	scala.tools.nsc.typechecker.Namers$Namer.memberSig(Namers.scala:1976)
	scala.tools.nsc.typechecker.Namers$Namer.typeSig(Namers.scala:1926)
	scala.tools.nsc.typechecker.Namers$Namer$ValTypeCompleter.completeImpl(Namers.scala:944)
	scala.tools.nsc.typechecker.Namers$LockingTypeCompleter.complete(Namers.scala:2123)
	scala.tools.nsc.typechecker.Namers$LockingTypeCompleter.complete$(Namers.scala:2121)
	scala.tools.nsc.typechecker.Namers$TypeCompleterBase.complete(Namers.scala:2116)
	scala.reflect.internal.Symbols$Symbol.completeInfo(Symbols.scala:1565)
	scala.reflect.internal.Symbols$Symbol.info(Symbols.scala:1537)
	scala.reflect.internal.Symbols$Symbol.initialize(Symbols.scala:1726)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5734)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6231)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$8(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedTemplate(Typers.scala:2089)
	scala.tools.nsc.typechecker.Typers$Typer.typedClassDef(Typers.scala:1927)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6060)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6231)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$8(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3470)
	scala.tools.nsc.typechecker.Typers$Typer.typedPackageDef$1(Typers.scala:5743)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6063)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Analyzer$typerFactory$TyperPhase.apply(Analyzer.scala:124)
	scala.tools.nsc.Global$GlobalPhase.applyPhase(Global.scala:480)
	scala.tools.nsc.interactive.Global$TyperRun.applyPhase(Global.scala:1370)
	scala.tools.nsc.interactive.Global$TyperRun.typeCheck(Global.scala:1363)
	scala.tools.nsc.interactive.Global.typeCheck(Global.scala:680)
	scala.meta.internal.pc.WithCompilationUnit.<init>(WithCompilationUnit.scala:22)
	scala.meta.internal.pc.SimpleCollector.<init>(PcCollector.scala:340)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.<init>(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector$lzycompute$1(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:73)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticTokens$1(ScalaPresentationCompiler.scala:196)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: -1 is out of bounds (min 0, max 2)