# XML Formatting Instructions

## Note

- CellSociety simulation assumes correct implementation of SOME formatting of a simulation's XML file, but also CHECKS for some errors;
- Some formatting errors that are checked for will either throw an exception which will open a pop-up box informing the user of the formatting error, OR simply carry on the simulation with some assigned parameters;
- Details about what kinds of errors are checked for or not are included in the last section;
- It is the responsibility of whoever is adding the XML file(s) to ensure it is formatted correctly as best as possible by following the rules below;

## Description

**Title** is simply a text sequence

    <title>Game of Life</title>


**Author** is simply a text sequence
    
    <author>John Horton Conway</author>
    
    
**Color Scheme** is a sequence of integers

- The sequence is defined by what states will map to what color
- Each state of the simulation (an integer) will appear once in the number sequece, as it maps to only one color
- The position that the state values appear in the sequence will dictate what color it maps to
- The color order mapping is defined in the SimulationParameters class, and is currently the following:
- colors = {Color.WHITE, Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};
- For any colors not being used, a -1 will be in place
- It is not necessary for a sequence to always be 7 integers long; it may only go up to the last color used by a state;

Thus, a simulation with 3 states, and the mapping of State 0 = BLACK, State 1 = YELLOW, State 2 = RED will be:

    <colorScheme>-1 0 -1 2 -1 1</colorScheme>
    
    
**Extra Parameters** is a sequence of doubles

- Each simulation may or may not have special parameters it deals with
- These special parameters will be included in a sequence of numbers here
- It is up to the RuleSet class for the specific simulation to define how to interpret these numbers and how these numbers affect the rules of the game
- This sequence may have as many numbers as needed; some simulations may have 3 extra parameters, some 1, and some 0, which in this case would just be a number 0, or anything else, as the RuleSet for the simulation wouldn't interpret anything from the extra parameters anyway

In the Fire simulation the number below may represent probCatch, i.e. the probability that a tree catches on fire during the simulation; whereas in Segregation, it could represent the parameter t, which defines the threshold as to how many alike neighbors are required for a group of blocks to be satisfied and not have to move;

    <extraParams>0.9</extraParams>
    
    
**Shape** will specify the shape used for the elements, also influencing the shape of the grid as well. Values can be "square", "hexagon", "triangle" and potentially "other"

	<shape>square</shape>


**Outline** will dictate whether the cells have an outline as its borders or not; values of "true" or "false"

	<hasOutline>true</hasOutline>
    
    
**Neighbors Considered** will dictate what neighbor arrangement is considered for the simulation. Values can be "cardinal", "diagonal" or "all"

	<neighborsConsidered>all</neighborsConsidered>
    
**Sliders** will inform all the sliders that will be present on the screen for the simulation.

- String of data;
- First element of string is slider TITLE;
- Second element of string is int of slider min value;
- Third element of string is int of slider max value;
- Fourth element of string is boolean informing if value of slider isContinuous (alternatively to being discrete)
- The slider elements are separated by a semicolon ";" (do not include spaces)
- If the simulation uses no slider, simply include "none"


	<sliders>ratio 0 1 true;sharkStarveTime 0 10 false</sliders>
    
    
**Toroidal** will dictate whether or not the grid warps around itself or not; values of "true" or "false"

	<toroidal>false</toroidal>
    
    
**Initial Configuration** determines how the initial configuration will be read and/or created

- There are 3 different ways an initial configuration can be created
- They will be represented by "fixed", "probability", and "random"
- Depending on the identifier provided, this will change how the Grid section (explained below) will be interpreted by SimulationParameters when building the for the simulation

- **fixed** means that the Grid specification will be exact, containing the structure of a 2-Dimensional array with its states positioned accordingly
- **probability** means that the Grid specification will provide the size of the grid, and the probability distribution of each state present in the simulation (examples provided in section below)
- **random** means that the Grid specification will provide the size of the grid, the total number of states, and the total number of elements to be occupied by non-empty (i.e. background) cells (examples provided in section below)

	<initialConfig>fixed</initialConfig>    
    
**Grid** is a visual representation of how the grid will look like

- The grid interpretation will be specified by the Initial Configuration identifier (explained above)
- The 3 ways (fixed, probability and random) of which the grid can be interpreted are explained below:


**fixed** identifier interpretation
- Each number in the visual grid is the current state that the grid will be initialized to in the beginning of the simluation
- Note that including a blank space " " before the first element is required
- A blank space between numbers is required throughout rows;
- Rows should be ended with a semicolon ";"

**probability** identifier interpretation
- Will be a string of 2 integers, and some doubles
- The first two integers will specify grid TOTAL ROWS and grid TOTAL COLUMNS
- The following doubles will specify the concentration of each NON-EMPTY STATE; i.e. 0.2 0.4 would indicate that state 1 fills 20% of the world and state 2 fills 40% of the world; still assuming the convention of state 0 being the empty state;
- Note that there is NOT an error check to see if probabilities add up to more than 1; if they do (meaning the XML file was input incorrectly) a grid will still be returned, except there will likely be no empty cells and will be fully covered.



**random** identifier interpretation
- Will be a string of 4 integers
- The first two integers will specify grid TOTAL ROWS and grid TOTAL COLUMNS
- The next integer will specify the total number of NON-EMPTY STATES (i.e. Game of Life would have 1, since the other state is meant to be the background / empty state)
- The next integer will specify the total number of cells to be occupied by NON-EMPTY states;
- Note that this requires the simulations to follow the conventions such that state 0 is the empty state; as this will generate random entries for states 1-N, assumed to be non-empty states;
- The grid configuration will then be determined randomly based off of those parameters



Below is a "fixed" type example starting configuration for GameOfLife, as a Glider in the top left:
    
    
    <grid> 0 0 0 0 0 0 0 0 0 0;
     	   0 0 1 0 0 0 0 0 0 0;
     	   0 0 0 1 0 0 0 0 0 0;
     	   0 1 1 1 0 0 0 0 0 0;
     	   0 0 0 0 0 0 0 0 0 0;
     	   0 0 0 0 0 0 0 0 0 0;
     	   0 0 0 0 0 0 0 0 0 0;
     	   0 0 0 0 0 0 0 0 0 0;
     	   0 0 0 0 0 0 0 0 0 0;
     	   0 0 0 0 0 0 0 0 0 0;</grid>
</data>


## Error Checking

Below is a list of errors checked for (and what is done about them) vs. errors not checked for (i.e. aspects of XML file expected to be correct)

**Not checked for**
- Color Scheme
- Extra Parameters

**Checked for**
- No simulation type given / empty parameter value in XML: POP UP / EXCEPTION 
- Invalid (negative) state number on grid: POP UP / EXCEPTION
- Invalid or incorrect slider formatting: POP UP / EXCEPTION
- Invalid Initial Configuration specifier: DEFAULT PARAMETER
- Incorrect Random Grid setup: DEFAULT PARAMETERS

**Default parameters added**
- Shape
- Outline
- Neighbors considered

Note: there are no checks for "cell locations given outside bounds" because the different types of grid specification don't specify locations; they are either a drawing of the states or parameters used to create a grid setup;



