# PDEVisualizer
Solves heat distribution differential equation and presents results on a chart

## About
Let's imagine we have a metal rod connected to a power source and we want to find out, how a heat distribution would look like
around it. To calculate it we need following information:
* Heat source efficiency
* Thermal conductivity of the rod
* Maximum possible temperature

These will allow us to create an equation. To resolve it, we use iterative method. It needs additional data:
* Matrix size
* Spaces between points
* Absolute error

First two parameters define number of points on the chart. The bigger the matrix and the smaller spaces between points,
the more time it will take to calculate it. Also setting smaller absolute error will make calculation last longer.

## Install and run
### Prerequisites
No additional libraries needed

Tested on pure Java SE 9

### Run
Simply run App class
