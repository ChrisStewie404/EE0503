file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala
### scala.ScalaReflectionException: value in_range_A is not a method

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
        val in_range_A =(i.U < cfg.gemm_matsize.U) & 
                        (RegNext(cross_cnt) < cfg.gemm_matsize.U + i.U) &
    
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
    val start :: load :: lfinish :: cstart :: compute :: cfinish :: write :: wfinish :: Nil = Enum(8)
    val state=RegInit(start)
    switch(state)
    {
        is(start)
        {
            when(io.start)
            {
                cross_cnt:=0.U
                load_index:=0.U
                row_cnt:=0.U
                col_cnt:=0.U
                buf_sel:=cfg.SEL_A
                state:=load
            }
        }
        is(load)
        {
            io.enable:=true.B
            state:=lfinish
        }
        is(lfinish)
        {
            state:=Mux(
                load_index===(cfg.gemm_matsize-1).U & buf_sel===cfg.SEL_B,
                cstart,
                load
            )
            load_index:=Mux(
                load_index===(cfg.gemm_matsize-1).U,
                0.U,
                load_index+1.U
            )
            buf_sel:=MuxCase(buf_sel,Seq(
                (buf_sel===cfg.SEL_A & load_index===(cfg.gemm_matsize-1).U) -> cfg.SEL_B,
                (buf_sel===cfg.SEL_B & load_index===(cfg.gemm_matsize-1).U) -> cfg.SEL_C,
            ))
        }
        is(cstart)
        {
            row_cnt:=0.U
            col_cnt:=0.U
            state:=compute
            for(row <- 0 until cfg.gemm_matsize)
            {
                for(col <- 0 until cfg.gemm_matsize)
                {
                    matA_buf(row)(col) := 0.U
                    matB_buf(row)(col) := 0.S
                    matC_buf(row)(col) := 0.S
                }
            }
        }
        is(compute)
        {
            for(row <- 0 until cfg.gemm_matsize)
            {
                for(col <- 0 until cfg.gemm_matsize)
                {
                    matC_buf(row)(col):=matC_buf(row)(col)+matA_buf(row)(col)*matB_buf(row)(col)
                }
            }
            for(row <- 0 until cfg.gemm_matsize)
            {
                for(col <- 0 until cfg.gemm_matsize)
                {
                    matA_buf(row)(col):=Mux(
                        col.U===0.U,
                        loadA_buf(row),
                        matA_buf(row)(col-1)
                    )
                }
            }
            for(row <- 0 until cfg.gemm_matsize)
            {
                for(col <- 0 until cfg.gemm_matsize)
                {
                    matB_buf(row)(col):=Mux(
                        row.U===0.U,
                        loadB_buf(col),
                        matA_buf(row-1)(col)
                    )
                }
            }   
            state:=cfinish         
        }
        is(cfinish)
        {

        }
    }
 }
```



#### Error stacktrace:

```
scala.reflect.api.Symbols$SymbolApi.asMethod(Symbols.scala:240)
	scala.reflect.api.Symbols$SymbolApi.asMethod$(Symbols.scala:234)
	scala.reflect.internal.Symbols$SymbolContextApiImpl.asMethod(Symbols.scala:99)
	scala.tools.nsc.typechecker.ContextErrors$TyperContextErrors$TyperErrorGen$.MissingArgsForMethodTpeError(ContextErrors.scala:795)
	scala.tools.nsc.typechecker.Typers$Typer.adaptMethodTypeToExpr$1(Typers.scala:984)
	scala.tools.nsc.typechecker.Typers$Typer.adapt(Typers.scala:1305)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6168)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3488)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg0$1(Typers.scala:3596)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$doTypedApply$7(Typers.scala:3615)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$doTypedApply$6(Typers.scala:3594)
	scala.tools.nsc.typechecker.Contexts$Context.savingUndeterminedTypeParams(Contexts.scala:546)
	scala.tools.nsc.typechecker.Typers$Typer.handleOverloaded$1(Typers.scala:3591)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3643)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typed1$28(Typers.scala:5093)
	scala.tools.nsc.typechecker.Typers$Typer.silent(Typers.scala:712)
	scala.tools.nsc.typechecker.Typers$Typer.tryTypedApply$1(Typers.scala:5093)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5181)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedSelectOrSuperCall$1(Typers.scala:6251)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6098)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typed1$41(Typers.scala:5160)
	scala.tools.nsc.typechecker.Typers$Typer.silent(Typers.scala:698)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5162)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3488)
	scala.tools.nsc.typechecker.Typers$Typer.handlePolymorphicCall$1(Typers.scala:3892)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3911)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5183)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3488)
	scala.tools.nsc.typechecker.Typers$Typer.handlePolymorphicCall$1(Typers.scala:3892)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3911)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5183)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3488)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgWithFormal$1(PatternTypers.scala:134)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.$anonfun$typedArgsForFormals$4(PatternTypers.scala:150)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgsForFormals(PatternTypers.scala:150)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgsForFormals$(PatternTypers.scala:131)
	scala.tools.nsc.typechecker.Typers$Typer.typedArgsForFormals(Typers.scala:203)
	scala.tools.nsc.typechecker.Typers$Typer.handleMonomorphicCall$1(Typers.scala:3823)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3874)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typed1$28(Typers.scala:5093)
	scala.tools.nsc.typechecker.Typers$Typer.silent(Typers.scala:698)
	scala.tools.nsc.typechecker.Typers$Typer.tryTypedApply$1(Typers.scala:5093)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5181)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedBlock(Typers.scala:2598)
	scala.tools.nsc.typechecker.Typers$Typer.typedOutsidePatternMode$1(Typers.scala:6071)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6107)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedFunction(Typers.scala:6242)
	scala.tools.nsc.typechecker.Typers$Typer.typedFunction(Typers.scala:3139)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typed1$108(Typers.scala:6035)
	scala.tools.nsc.typechecker.Typers$Typer.typedFunction$1(Typers.scala:497)
	scala.tools.nsc.typechecker.Typers$Typer.typedOutsidePatternMode$1(Typers.scala:6075)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6107)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6153)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3488)
	scala.tools.nsc.typechecker.Typers$Typer.handlePolymorphicCall$1(Typers.scala:3892)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3911)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5183)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5194)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6097)
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
	scala.meta.internal.pc.Compat.$anonfun$runOutline$1(Compat.scala:57)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:576)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:574)
	scala.collection.AbstractIterable.foreach(Iterable.scala:933)
	scala.meta.internal.pc.Compat.runOutline(Compat.scala:49)
	scala.meta.internal.pc.Compat.runOutline(Compat.scala:35)
	scala.meta.internal.pc.Compat.runOutline$(Compat.scala:33)
	scala.meta.internal.pc.MetalsGlobal.runOutline(MetalsGlobal.scala:36)
	scala.meta.internal.pc.ScalaCompilerWrapper.compiler(ScalaCompilerAccess.scala:19)
	scala.meta.internal.pc.ScalaCompilerWrapper.compiler(ScalaCompilerAccess.scala:14)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticTokens$1(ScalaPresentationCompiler.scala:195)
```
#### Short summary: 

scala.ScalaReflectionException: value in_range_A is not a method