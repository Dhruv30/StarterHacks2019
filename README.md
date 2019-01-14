# SmartR
SmartR is an Android application that is capable of categorizing waste, and notifying the user how it should be disposed of. By using image recognition technology, the user is able to conveniently take a picture of something they wish to throw out and SmartR will identify how to dispose of it.


# Inspiration
According to the general manager of solid waste management for the City of Toronto, it is estimated that each percentage point decrease in contamination could lower recycling costs by $600,000 - $1,000,000, and this correlates to the rates that we pay for waste collection.


# How we built it
Android Studio was our main platform for development. We used Clarifai's Image Recognition API to determine a list of possible objects that the image might represent. We then created an algorithm to help classify which bin the item should be disposed in depending on the objects identified in the picture. We created a responsive and intuitive user interface, to allow the user to conveniently navigate within the application.


# What's next
We expect it to be hard to engage users to use the application in the long term. Therefore we would plan to implement more features that offer incentive for people to use the application. One of these features could include quizzes that reward points, based on items they have disposed of in the past. Also we thought about implementing the Snapchat API so that users can display their score to their friends, and show off that they are being environmentally friendly.


#Navigating the Repository 
The actual implementation is contained within the 'Test3' folder whereas the logo designs are contained within the 'Logos and Designs' folder. For more about our project, refer to https://devpost.com/software/starterhacks2019