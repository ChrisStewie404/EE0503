file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala
### scala.reflect.internal.FatalError: 
  ThisType(value $anonfun) for sym which is not a class
     while compiling: file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala
        during phase: globalPhase=<no phase>, enteringPhase=parser
     library version: version 2.13.12
    compiler version: version 2.13.12
  reconstructed args: -deprecation -feature -Wconf:cat=feature:w -Wconf:cat=deprecation:w -Wconf:cat=deprecation:ws -Wconf:cat=feature:ws -Wconf:cat=optimizer:ws -classpath <WORKSPACE>/.bloop<HOME>/bloop-bsp-clients-classes/classes-Metals-6B5QlsqPQPaGwYPmhUlC6g==:<HOME>/.cache/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.4/semanticdb-javac-0.10.4.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/chipsalliance/chisel_2.13/5.1.0/chisel_2.13-5.1.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/scopt/scopt_2.13/3.7.1/scopt_2.13-3.7.1.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/net/jcazevedo/moultingyaml_2.13/0.4.2/moultingyaml_2.13-0.4.2.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native_2.13/4.0.6/json4s-native_2.13-4.0.6.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-text/1.10.0/commons-text-1.10.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/github/alexarchambault/data-class_2.13/0.2.5/data-class_2.13-0.2.5.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/os-lib_2.13/0.8.1/os-lib_2.13-0.8.1.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parallel-collections_2.13/1.0.4/scala-parallel-collections_2.13-1.0.4.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle_2.13/2.0.0/upickle_2.13-2.0.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/nscala-time/nscala-time_2.13/2.22.0/nscala-time_2.13-2.22.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/yaml/snakeyaml/1.26/snakeyaml-1.26.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-core_2.13/4.0.6/json4s-core_2.13-4.0.6.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native-core_2.13/4.0.6/json4s-native-core_2.13-4.0.6.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/geny_2.13/0.7.1/geny_2.13-0.7.1.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/ujson_2.13/2.0.0/ujson_2.13-2.0.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upack_2.13/2.0.0/upack_2.13-2.0.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-implicits_2.13/2.0.0/upickle-implicits_2.13-2.0.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/joda-time/joda-time/2.10.1/joda-time-2.10.1.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/joda/joda-convert/2.2.0/joda-convert-2.2.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-ast_2.13/4.0.6/json4s-ast_2.13-4.0.6.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-scalap_2.13/4.0.6/json4s-scalap_2.13-4.0.6.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/thoughtworks/paranamer/paranamer/2.8/paranamer-2.8.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-core_2.13/2.0.0/upickle-core_2.13-2.0.0.jar -language:reflectiveCalls -Xcheckinit -Xplugin-require:semanticdb -Yrangepos -Ymacro-expand:discard -Ymacro-annotations -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: Select(ApplyImplicitView(method fromIntToLiteral), U)
       tree position: line 72 of file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala
            tree tpe: chisel3.UInt
              symbol: method U in class fromBigIntToLiteral
   symbol definition: def U: chisel3.UInt (a MethodSymbol)
      symbol package: chisel3
       symbol owners: method U -> class fromBigIntToLiteral -> package object chisel3
           call site: <none> in <none>

== Source file context for tree position ==

    69         val in_range_B = (i.U < cfg.gemm_matsize.U) & (RegNext(cross_cnt)- i.U < cfg.gemm_matsize.U)
    70         loadA_buf(i):=MuxCase(loadA_buf(i),Seq(
    71             (_CURSOR_en_load_B & in_range_B) -> io.dataIn.asUInt,
    72             (en_load_B & !in_range_B) -> 0.U
    73         ))
    74     }
    75  }

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.12
Classpath:
<WORKSPACE>/.bloop<HOME>/bloop-bsp-clients-classes/classes-Metals-6B5QlsqPQPaGwYPmhUlC6g== [exists ], <HOME>/.cache/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.4/semanticdb-javac-0.10.4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/chipsalliance/chisel_2.13/5.1.0/chisel_2.13-5.1.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/scopt/scopt_2.13/3.7.1/scopt_2.13-3.7.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/net/jcazevedo/moultingyaml_2.13/0.4.2/moultingyaml_2.13-0.4.2.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native_2.13/4.0.6/json4s-native_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-text/1.10.0/commons-text-1.10.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/github/alexarchambault/data-class_2.13/0.2.5/data-class_2.13-0.2.5.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/os-lib_2.13/0.8.1/os-lib_2.13-0.8.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parallel-collections_2.13/1.0.4/scala-parallel-collections_2.13-1.0.4.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle_2.13/2.0.0/upickle_2.13-2.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/nscala-time/nscala-time_2.13/2.22.0/nscala-time_2.13-2.22.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/yaml/snakeyaml/1.26/snakeyaml-1.26.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-core_2.13/4.0.6/json4s-core_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native-core_2.13/4.0.6/json4s-native-core_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/geny_2.13/0.7.1/geny_2.13-0.7.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/ujson_2.13/2.0.0/ujson_2.13-2.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upack_2.13/2.0.0/upack_2.13-2.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-implicits_2.13/2.0.0/upickle-implicits_2.13-2.0.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/joda-time/joda-time/2.10.1/joda-time-2.10.1.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/joda/joda-convert/2.2.0/joda-convert-2.2.0.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-ast_2.13/4.0.6/json4s-ast_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-scalap_2.13/4.0.6/json4s-scalap_2.13-4.0.6.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/thoughtworks/paranamer/paranamer/2.8/paranamer-2.8.jar [exists ], <HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-core_2.13/2.0.0/upickle-core_2.13-2.0.0.jar [exists ]
Options:
-language:reflectiveCalls -deprecation -feature -Xcheckinit -Ymacro-annotations -Yrangepos -Xplugin-require:semanticdb


action parameters:
offset: 2793
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
            (@@en_load_B & in_range_B) -> io.dataIn.asUInt,
            (en_load_B & !in_range_B) -> 0.U
        ))
    }
 }
```



#### Error stacktrace:

```
scala.reflect.internal.Reporting.abort(Reporting.scala:70)
	scala.reflect.internal.Reporting.abort$(Reporting.scala:66)
	scala.reflect.internal.SymbolTable.abort(SymbolTable.scala:28)
	scala.reflect.internal.Types$ThisType.<init>(Types.scala:1394)
	scala.reflect.internal.Types$UniqueThisType.<init>(Types.scala:1414)
	scala.reflect.internal.Types$ThisType$.apply(Types.scala:1418)
	scala.meta.internal.pc.AutoImportsProvider$$anonfun$autoImports$3.applyOrElse(AutoImportsProvider.scala:75)
	scala.meta.internal.pc.AutoImportsProvider$$anonfun$autoImports$3.applyOrElse(AutoImportsProvider.scala:60)
	scala.collection.immutable.List.collect(List.scala:267)
	scala.meta.internal.pc.AutoImportsProvider.autoImports(AutoImportsProvider.scala:60)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$autoImports$1(ScalaPresentationCompiler.scala:384)
```
#### Short summary: 

scala.reflect.internal.FatalError: 
  ThisType(value $anonfun) for sym which is not a class
     while compiling: file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala
        during phase: globalPhase=<no phase>, enteringPhase=parser
     library version: version 2.13.12
    compiler version: version 2.13.12
  reconstructed args: -deprecation -feature -Wconf:cat=feature:w -Wconf:cat=deprecation:w -Wconf:cat=deprecation:ws -Wconf:cat=feature:ws -Wconf:cat=optimizer:ws -classpath <WORKSPACE>/.bloop<HOME>/bloop-bsp-clients-classes/classes-Metals-6B5QlsqPQPaGwYPmhUlC6g==:<HOME>/.cache/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.4/semanticdb-javac-0.10.4.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/chipsalliance/chisel_2.13/5.1.0/chisel_2.13-5.1.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/scopt/scopt_2.13/3.7.1/scopt_2.13-3.7.1.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/net/jcazevedo/moultingyaml_2.13/0.4.2/moultingyaml_2.13-0.4.2.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native_2.13/4.0.6/json4s-native_2.13-4.0.6.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-text/1.10.0/commons-text-1.10.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/io/github/alexarchambault/data-class_2.13/0.2.5/data-class_2.13-0.2.5.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/os-lib_2.13/0.8.1/os-lib_2.13-0.8.1.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parallel-collections_2.13/1.0.4/scala-parallel-collections_2.13-1.0.4.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle_2.13/2.0.0/upickle_2.13-2.0.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/github/nscala-time/nscala-time_2.13/2.22.0/nscala-time_2.13-2.22.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/yaml/snakeyaml/1.26/snakeyaml-1.26.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-core_2.13/4.0.6/json4s-core_2.13-4.0.6.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-native-core_2.13/4.0.6/json4s-native-core_2.13-4.0.6.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/geny_2.13/0.7.1/geny_2.13-0.7.1.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/ujson_2.13/2.0.0/ujson_2.13-2.0.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upack_2.13/2.0.0/upack_2.13-2.0.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-implicits_2.13/2.0.0/upickle-implicits_2.13-2.0.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/joda-time/joda-time/2.10.1/joda-time-2.10.1.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/joda/joda-convert/2.2.0/joda-convert-2.2.0.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-ast_2.13/4.0.6/json4s-ast_2.13-4.0.6.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/org/json4s/json4s-scalap_2.13/4.0.6/json4s-scalap_2.13-4.0.6.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/thoughtworks/paranamer/paranamer/2.8/paranamer-2.8.jar:<HOME>/.cache/coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/upickle-core_2.13/2.0.0/upickle-core_2.13-2.0.0.jar -language:reflectiveCalls -Xcheckinit -Xplugin-require:semanticdb -Yrangepos -Ymacro-expand:discard -Ymacro-annotations -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: Select(ApplyImplicitView(method fromIntToLiteral), U)
       tree position: line 72 of file://<WORKSPACE>/src/main/scala/Lab4s/SGEMM.scala
            tree tpe: chisel3.UInt
              symbol: method U in class fromBigIntToLiteral
   symbol definition: def U: chisel3.UInt (a MethodSymbol)
      symbol package: chisel3
       symbol owners: method U -> class fromBigIntToLiteral -> package object chisel3
           call site: <none> in <none>

== Source file context for tree position ==

    69         val in_range_B = (i.U < cfg.gemm_matsize.U) & (RegNext(cross_cnt)- i.U < cfg.gemm_matsize.U)
    70         loadA_buf(i):=MuxCase(loadA_buf(i),Seq(
    71             (_CURSOR_en_load_B & in_range_B) -> io.dataIn.asUInt,
    72             (en_load_B & !in_range_B) -> 0.U
    73         ))
    74     }
    75  }