# STALGCM-CaseStudy-Program  
**STALGCM Major Course Output: Case Study**
This program is a simulation of the deterministic pushdown automata (DPDA).


## Guidelines for File input.
To run the program, have your machine be a text file and named input.txt for the program 
to read and accept your machine properly.

**FORMAT of the text File**
```
q0 q1 q2 /* List of states separated by spaces. */
0 1 c /* List of input symbols separated by spaces. */
X Z /* List of stack symbols separated by spaces. */
4 /* Number of transitions. */
q0 0 λ q0 X /* Transitions in the format "q s pop q' push"... */
q0 1 X q1 λ /* ...such that δ(q,s,pop) = (q',push). */
q1 1 X q1 λ
q1 λ Z q2 λ
q0 /* Start state. */
q2 /* List of final states. */
```

The machine assumes that Z is the start stack symbol. As such, please
design any machines to be inputed in this program accordingly.

For input and stack symbols, please ensure that each symbol is only one character long each.

Do note that a machine that creates an infinite loop through
λ-transitions on an input string considers said string to be rejected.

To re-run the machine on another input string, kindly exit and re-execute the program.

Developed by: Don Laude A. Aspecto, Lanz Kendall Y. Lim, and Tyler Justin H. Tan
