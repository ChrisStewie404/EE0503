# Matrix Multiplication in Chisel
This repo contains my labs for SJTU `EE0503`. All implementations are under the directory `src` (`src/main` for soruce code, `src/test` for corresponding test code).
## Running the Tests
To run the tests, please first make sure that your current working directory is `EE0503`. Given test class named `GEMMMTEST` of package `EE0503LAB`, run the following command in your terminal to test your implementation
```bash
sbt "testOnly EE0503LAB.GEMMMTEST"
```
If you would like to generate the digital waveform as well, then use
```bash
sbt "testOnly  EE0503LAB.GEMMMTEST -- -DwriteVcd=1"
```
## Overview of Labs
- `Lab2` implements a vector dot product machine with Finite State Machine (FSM).
- `Lab3` implements square matrix multiplication of matrix $A$,$B$, which is read from memory, and write the final result matrix $ C=A*B $ back to the memory.
- `Lab4` implements general matrix multiplication (GEMM) of matrix $A_{m*k}$ and $B_{k*n}$ in two different ways.  
 `Lab4plus` (package `Lab4plus`) implements GEMM using **blocking** technique. The size of each block is specified by the `cfg.matSize`, which is not necessarily a common divisor of $m$,$k$, and $n$, thus, **padding** is integrated to solve corner cases during execution.  
 `Lab4s` (package `Lab4s`) uses a **systolic array** to realize matrix multiplication, which improves the throughput of the module greatly (more specifically, runtime of matrix multiplication is now $O(mk+kn+mn)$, whereas in the previous implementation, it would be $O(mkn)$).