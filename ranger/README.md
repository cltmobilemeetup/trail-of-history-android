# Ranger

Ranger is a companion application for the Charlotte Trail of History application. It allows admin users to update information for points of interest along the trail. The application uses Firebase for authentication and administration.

## Setting up Ranger

In order to develop for Ranger (and the other Trail of History applications) you will need a [Firebase account](https://firebase.google.com/).

After creating your account you need to do the following:

* Create a new Project in Firebase (the name doesn't matter but we use Trail of History)
* Enable Auth
* Create a new test user (Auth -> Add User from within the Firebase web console). The Ranger application does not currently allow users to be created from within the application itself.
* Enable Remote Database   
* Add an Android App to your Project.
* Download the google-services.json file that is provided and put it in the root of the Ranger module directory.
