# STALGCM-CaseStudy-Program  
**STALGCM Major Course Output: Case Study**
This program is a simulation of the deterministic pushdown automata (DPDA).


# Guidelines for File input.
To run the program, have your machine be a text file and named input.txt for the program 
to read and accept your machine properly.
**FORMAT of the text File**
q0 q1 q2 /* List of states separated by spaces. */
0 1 c /* List of inputs separated by spaces. */
X Z /* List of stack symbols separated by spaces. */
4 /* Number of transitions. */
q0 0 λ q0 X /* Transitions in the format q s pop q' push... */
q0 1 X q1 λ /* ...such that δ(q,s,pop) = (q',push). */
q1 1 X q1 λ
q1 λ Z q2 λ
q0 /* Start state. */
q2 /* List of final states. */

Do note that a machine that creates an infinite loop through
λ-transitions on an input string is considered to be rejected.

Developed by:
Don Laude A. Aspecto
Lanz Kendall Y. Lim
Tyler Justin H. Tan
