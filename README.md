# Cloth simulation with Position Based Dynamics

This is from a school project in a graphics programming course I took back in 2017. It was all programmed in Java using the Processing library.

It turns out that a natural idea for implementing simulated cloth is to treat it as particles interconnected with springs, but this can have the effect of producing bouncy or rubbery cloth. In this project I implemented an algorithm by Muller and coworkers which takes a different approach to the subject by instead enforcing distance constraints on the particles and running a solver which tries to satisfy the constraints while preserving certain physical quantities which keeps the system behaving physically. For more info, please see the report or follow the blog (which has video).

Blog at
https://dgiproject2017.wordpress.com
