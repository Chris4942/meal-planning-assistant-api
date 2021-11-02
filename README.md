# Setup

## IDE

***IntelliJ***. I'm using Version 2021.2.2.
There might be issues with Kotlin if you use an older version of IntelliJ, so please update to the newest version before cloning the project.
To do this:
1. Click on IntelliJ in the menu bar
2. Click on "Check for Updates..."
3. You may have to update multiple times before getting to the current version.
***
After getting IntelliJ updated, add the Kotlin plugin:
- IntelliJ IDEA >> Preferences >> Plugins >> Search for Kotlin and install it

## Clone the repo

1. `git clone` the repo and open the project with IntelliJ.
2. Set the SDK to version 1.8:
   - File >> Project Structure >> Project Settings >> Project

## Build the project
- Click the green hammer at the top right of IntelliJ or use:
  - Build >> Build Project

## Run Main
- open `src/main/kotlin/edu.byu.mealplanningassistant.Main.kt`
- click the green arrow to the left of `fun main()`


# Models

## Meal Request Objects
**To see what the objects look like [here](https://github.com/Chris4942/meal-planning-assistant-api/tree/main/src/main/kotlin/edu/byu/mealplanningassistant/models)**
_Note: these objects will be sent in http requests as JSON objects_

# Setting up MongoDB (MacOS)

[Use this link as a guide](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/)

## How to install
- brew tap mongodb/brew
- brew install mongodb-community@5.0

## How to start
- brew services start mongodb-community@5.0
- mongosh
- [use mealManager](https://www.tutorialspoint.com/mongodb/mongodb_create_database.htm)
- (First time only) [db.createCollection("recipes")](https://docs.mongodb.com/manual/reference/method/db.createCollection/)

## How to stop

- exit
- brew services stop mongodb-community@5.0
