# Window-Cleaner-Simulation
A simulation for an automated window cleaner.

##General Algorithm

  1.	Window cleaner moves down then cleans the windows while moving up
    *	Suction cups alternate between switching on the left/right and middle suction cups

  2.	When reaching the top of the building, the window cleaner move right
    *	The window cleaner moves down slightly before moving right so that the middle suction cup can grip the building

  3.	When the window cleaner has reached the next column of windows
    *	The window cleaner moves up slightly to clean the top of the building before repeating Steps 1 to 3 

  4.	When the window cleaner has finished cleaning all the windows
    *	The window cleaner moves down slightly so that the middle suction cup can grip the building
    *	The window cleaner then moves left until it reaches the beginning of the building
    *	Finally, the window returns to its original position by moving slightly up

##Algorithm for Water & Chemical Tanks

  1.	When the window cleaner is cleaning (meaning it is moving up)
    *	Water and chemicals are sprayed onto the window
    *	Dirty water is collected and pumped back to the dolly's dirty water tank

  2.	When the clean water or chemical tanks reach a certain threshold  (e.g. 30 gallon onboard water tank has 10 gallons of water left)
    *	Start filling up that tank
    *	Continue filling up until the fluid in the tank reaches a higher threshold
      *	e.g., Continue filling up the onboard water tank until it has 20 gallons of water in it

  3.	When the dolly's dirty water tank reaches a certain threshold, start emptying the tank until it has 10 gallons of dirty water in it
