# ChromaScan

## Project Overview
### Problem Statement and Goals

When creating digital art, choosing the right colours can get difficult and can take away time spent being creative. A user may:
1. Come across a colour from elsewhere but wont know the value of the colour
2. Want to know the most complimenting colour from an existing palette (source)

The goal of this tool is to allow a user to swiftly find a colour to increase efficiency. I would also like the tool to be installed on the desktop so they can swiftly use the tool.

### Scope + Deliverables table

Description | MVP?
----------- | ----
Create a desktop application users can open using keyboard shortcuts | yes
Create an algorithm to find complimenting colours | yes
Integrate GPT to find a colour using a prompt | no
Use github actions to implement CI/CD workflow | no
Integrate GPT to come up with a name for a colour | yes


I will NOT create the desktop application from scratch: I will follow tutorials on how to achieve my goals
- https://www.geeksforgeeks.org/image-processing-in-java-colored-to-red-green-blue-image-conversion/
- https://www.tutorialspoint.com/how-to-get-pixels-rgb-values-of-an-image-using-java-opencv-library 

### Project schedule

| Milestone | Time spent | Due Date | Description |
| -- | -- | -- | -- |
| Milestone 0 | 1 week | 2023-07-25 | - Create backend design and be able to evaluate the colour based on a small source image <br>- Integrate openAI API to come up with a fun name for the colour <break> - create unit tests |
| Milestone 1 | 1 week |  | - Develop algorithm to find the complimenting colour in a source image
| Milestone 3 | 1-2 days |  | - Develop API using SpringBoot to send the image from the frontend to backend |
| Milestone 4 | 1 week |  | - Design and develop frontend using flutter |
| Milestone 5 | 1 week |  | - Attach api to frontend |


## High Level Technical Design
### Technologies Used + high level system design
- Flutter frontend
- SpringBoot API
- Java backend

### Api design
`Colour getColour(Image source): Colour`

`Colour getRandomColour(): Colour`

`Colour getComplimentingColour(Image image)`

`Colour getComplimentingColour(List[Image] sourceImg)`


### Important classes and data structures

```
class Rgb{
	private int red
	private int green
	private int blue
}
class Colour{
	private Rgb rgb
	private String hex
	Private String name
}
Class ColourEvaluator{
	getColour(Image source)
    getRandomColour()
    getComplimentingColour(Image image)
    getComplimentingColour(List[Image] sampleImg)
}
```
### Frontend Mock up

