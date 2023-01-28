# TodoApp
Overview:
The "To-Do List" app is designed to help users stay organized and on top of their tasks. The main goals of the app are to provide a simple and user-friendly interface for creating, managing, and completing tasks, as well as to offer reminders and notifications to ensure that tasks are not forgotten.
The app will have several key features, including:
•	Ability to create, edit, and delete tasks
•	Set reminders notification for tasks
•	Option to set recurring tasks
The user experience is designed to be simple and intuitive, with a clear navigation menu and easy-to-use input fields for creating and managing tasks. The target audience is anyone who wants to stay organized and on top of their tasks, and the app is designed to meet their needs by providing a comprehensive and user-friendly approach to task management.

From a technical standpoint, the app will be developed for Android platforms, and will require a mobile device with internet access to use. The visual design will be minimalistic and clean, with a focus on easy readability and clear navigation.

To-Do List UI:
1. SplashScreen
2. SignIn
3. SignUp
4. HomeScreen
5. Popup Fragment

 ![gt9](https://user-images.githubusercontent.com/70897446/215229435-3482a54e-cc96-42ef-bfb4-40047a1a8f94.jpg)
 ![image](https://user-images.githubusercontent.com/70897446/215227893-9e082d86-528b-4132-8190-c5695a843914.png)   ![image](https://user-images.githubusercontent.com/70897446/215227982-003fd5ac-6d29-43c1-b2d1-bead33c3a020.png)    ![image](https://user-images.githubusercontent.com/70897446/215228006-38a3a391-fd29-48b5-b541-7d0a236834c2.png)

Navigation Components
Implemented Navigation Component in App, navigation components is a  new Android Jetpack library that makes it easy to navigate between different screens (or destinations) in an app. It provides a consistent and predictable way of navigating between destinations, and it eliminates the need to manually manage the navigation stack.
![image](https://user-images.githubusercontent.com/70897446/215228054-b08401dc-9acf-4ea2-bc79-b13406dd275e.png)

Creating and Updating Task:

![gt](https://user-images.githubusercontent.com/70897446/215228380-5f56bd50-9f40-4870-8ef0-2c58a381314c.jpg)
![gt2](https://user-images.githubusercontent.com/70897446/215228600-2b766b05-1823-45eb-926a-37ad225b13eb.jpg)
![gt3](https://user-images.githubusercontent.com/70897446/215228684-36e048a0-2d96-4224-ae8e-b3f58122089a.jpg)

Firebase Authentication
Our app uses Firebase Authentication to provide a secure and easy-to-use login experience for our users."
"With Firebase Authentication, our app ensures that only authorized users have access to it.
"Firebase Authentication gives our app the ability to authenticate users quickly and securely."

Firebase real-time database
Firebase Realtime Database is a cloud-hosted NoSQL database that allows you to store and sync data in real-time between your app and the Firebase servers.
 In the context of a TODO list app, using Firebase Realtime Database for storing tasks would allow for the following benefits:
Real-time syncing: Since the database is cloud-based, any changes made to the task list in the app will be instantly synced with the Firebase servers, and any other connected devices will receive updates in real-time. This makes it easy to collaborate on tasks with others or keep the task list up-to-date across multiple devices.
Firebase Realtime Database is Scalability and can handle large amounts of data and thousands of concurrent users, making it suitable for large-scale apps with many users.

![gt4](https://user-images.githubusercontent.com/70897446/215228812-ff816081-f130-4916-9365-be45e2133d00.jpg)

Notification:

![gt5](https://user-images.githubusercontent.com/70897446/215228965-8e298239-bc9d-4cbd-8f13-48585c693ca6.jpg)
![gt6](https://user-images.githubusercontent.com/70897446/215228975-e81d71db-6a20-4537-8fd3-508e46b396b8.jpg)

Development Process
In the development process, the Android app began with the creation of a sign-in and sign-up feature using Firebase Authentication. This ensured that only authorized users could access the app's features and data. The Firebase Authentication library was integrated into the app to handle user registration and login securely."
"To store and manage tasks within the app, Firebase and Real-time Database Firebase libraries were integrated into a separate class. This class was responsible for handling all the CRUD (Create, Read, Update, and Delete) operations on tasks and made it easy to keep track of the tasks added by users."
"An adapter was developed to retrieve the data from the Real-time Database and display it in the app's user interface. This adapter was used to populate the task list within the app, making it easy for users to see their tasks and manage them."
"In order to remind users when a task is due, the app was designed to send the time and date of each task to the device's notification system. This allowed users to be alerted when a task is approaching its due date, ensuring that they never miss an important task."

Testing on different devices
Android Version 10, SDK/API Level 29
"The Android app underwent thorough testing on version 10 of the Android operating system. The app was confirmed to be fully functional and stable on this version, ensuring a smooth user experience for those running it on devices with Android 10."

![gt7](https://user-images.githubusercontent.com/70897446/215229202-dc328ea4-4b94-4c7f-881d-6587f2fc5013.jpg)

Android Version 8, SDK/API Level 27
The Android app was successfully run and evaluated on version 8 of the Android operating system. This included testing for compatibility. This ensures that the app is optimized for use on devices running Android 8.


![gt8](https://user-images.githubusercontent.com/70897446/215229249-e1545fc7-1f35-4a6a-a628-894c94bb953b.jpg)


https://user-images.githubusercontent.com/70897446/215238617-a1227381-5a4c-4ddf-86e6-d4a72e503517.mp4

