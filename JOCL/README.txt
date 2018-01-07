CL Assignment
Written in MAC (Unicode-utf-8)
by Kelvin (Jinze) Chen, Financial Mathematics, Courant Institute
========================
How To Use
————————————————————————
Here is a example to compute the IBM European Call option price, where the stopping criteria is with probability 99% the estimation error is less than 0.05$.

//Firstly, we need to define our option to be computed
	String optionName0 = "IBMEuropeanCall";
	float volatility = (float) (0.01f * Math.sqrt(252));
	float interestRate = 0.0001f * 252;
	float time = 1f;
	float spotPrice = 152.35f;
	float strikePrice0 = 165f;
	EuropeanOption opt = new EuropeanOption(optionName0,volatility, interestRate, time, spotPrice, strikePrice0, "Call");

//Secondly,we construct a SimulationManager with the option, confidence level, error tolerance used in the simulation.
	SimulationManager sm = new SimulationManager( opt,.99, 0.005);

//Finally, we call the simulate() method to get the result.
	System.out.println(sm.simulate());



Design
————————————————————————
To significantly improve the efficiency, I use OpenCL to conduct the Box Muller transformation, generate the final day price of a stock path, compute the payoff at the expiration and discount the value to get the final estimate of the price. In this design, it is a little bit harder to test weather the final result is reliable. So I cut the test into several stages to test the accuracy of each part.

It is super fast to compute the european option price even for such a strict stopping criteria under this design.
