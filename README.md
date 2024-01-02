# ChromaScan

## Testing

Navigate to the `ChromaScanController.java` file and run it using an IDE. This will initialize the springboot server.

Navigate to the `flutter_app` directory and run `flutter run` in the terminal. This will build and start the flutter app which this application is built on.

## Project Overview
### Problem Statement and Goals

When creating digital art, choosing the right Colors can get difficult and can take away time spent being creative. A user may:
1. Come across a Color from elsewhere but wont know the value of the Color
2. Want to know the most complimenting Color from an existing palette (source)

The goal of this tool is to allow a user to swiftly find a Color to increase efficiency. I would also like the tool to be installed on the desktop so they can swiftly use the tool.

### Scope + Deliverables table

Description | MVP?
----------- | ----
Create a desktop application users can open using keyboard shortcuts | yes
Create an algorithm to find complimenting Colors | yes
Integrate GPT to find a Color using a prompt | no
Use github actions to implement CI/CD workflow | no
Integrate GPT to come up with a name for a Color | yes


I will NOT create the desktop application from scratch: I will follow tutorials on how to achieve my goals
- https://www.geeksforgeeks.org/image-processing-in-java-colored-to-red-green-blue-image-conversion/
- https://www.tutorialspoint.com/how-to-get-pixels-rgb-values-of-an-image-using-java-opencv-library 

### Project schedule

| Milestone | Time spent | Due Date | Description |
| -- | -- | -- | -- |
| Milestone 0 | 1 week | 2023-08-08 | - Create backend design and be able to evaluate the Color based on a small source image <br>- Integrate openAI API to come up with a fun name for the Color <break> - create unit tests |
| Milestone 1 | 1 week | 2023-08-13 | - Develop algorithm to find the complimenting Color in a source image
| Milestone 3 | 1-2 days | 2023-08-25 | - Develop API using SpringBoot to send the image from the frontend to backend |
| Milestone 4 | 1 week | 2023-08-27 | - Design and develop frontend using flutter |
| Milestone 5 | 2 week | 2024-01-01 | - Attach api to frontend |


## High Level Technical Design
### Technologies Used + high level system design
- Flutter frontend
- SpringBoot API
- Java backend

### Api design
`Color getColor(Image source): Color`

`Color getRandomColor(): Color`

`Color getComplimentingColor(Image image)`

`Color getComplimentingColor(List[Image] sourceImg)`


### Important classes and data structures

```
class Rgb{
	private int red
	private int green
	private int blue
}
class Color{
	private Rgb rgb
	private String hex
	Private String name
}
Class ColorEvaluator{
	getColor(Image source)
    getRandomColor()
    getComplimentingColor(Image image)
    getComplimentingColor(List[Image] sampleImg)
}
```
### Frontend Mock up

