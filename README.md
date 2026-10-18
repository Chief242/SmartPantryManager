# **Smart Pantry Manager**

### **Overview**

Smart Pantry Manager is an Android application developed using Java and SQLite. It helps users manage ingredients stored in their pantry and discover recipes based on the ingredients they have available.

The application checks ingredient availability and required quantities before suggesting recipes, helping users identify meals they can prepare using their existing ingredients.

### **Features**

### **Pantry Management**

* Add pantry ingredients with their names, quantities, and units.
* View stored ingredients in a list.
* Edit existing ingredients. 
* Delete ingredients with confirmation.
* Validate ingredient quantities before saving. 

### **Recipe Suggestions**

* Display recipes when all required ingredients are available in sufficient quantities.
* Avoid suggesting recipes when required ingredients are missing or quantities are insufficient. 
* Support basic ingredient-name and measurement-unit normalization. 

### **Recipe Details**

* View recipe ingredients and required quantities. 
* Read preparation instructions for selected recipes.

### **Local Database**

* Use SQLite to store pantry ingredients and recipe information.
* Retain stored pantry data between normal application sessions. 

### **Settings and Navigation**

* Navigate between the home screen, pantry, suggested recipes, recipe details, and settings.
* Save the expiring-soon alert preference.

### **Technologies Used**

* **Programming language**: Java
* **Development environment**: Android Studio
* **User Interface**: XML layouts and Android UI components
* **Database**: SQLite
* **Navigation**: Android Activities and Intents
* **Ingredient display**: ListView and custom adapters
* **Version control**: Git and GitHub

### **Application Screens**

The application includes the following screens:

1. **Home Screen**: Provides access to the main application features.
2. **My Pantry**: Display and manages pantry ingredients.
3. **Suggested Recipes**: Displays recipes that meet the ingredient requirements.
4. **Recipe Details**: Displays the selected recipe's ingredients and preparation instructions.
5. **Settings**: Provides access to the application's settings.

### **Database Design**

The application uses a local SQLite database containing tables for:

* **Pantry ingredients**: Stores ingredient names, quantities, and measurement units.
* **Recipes**: Stores recipe names and preparation instructions.
* **Recipe ingredients**: Stores the ingredients and quantities required for each recipe. 

The recipe-matching logic compares pantry contents with recipe requirements to determine which recipes can be prepared.

# **Getting Started**

### **Prerequisites**

* Android Studio
* A compatible Android emulator or Android device
* The Android SDK and Gradle dependencies required by the project

### **Running the Application**

1. Clone or download this repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and resolve the project dependencies. 
4. Start an Android emulator or connect a compatible device.
5. Select the app run configuration.
6. Click Run to install and launch the application.

### **Testing**

The following application behaviors have been tested:
* Adding, editing, viewing, and deleting pantry ingredients.
* Rejecting empty and zero ingredient quantities.
* Displaying matching recipes when all required ingredients are available.
* Hiding recipe suggestions when a required ingredient is missing.
* Displaying recipe ingredients and preparation instructions.
* Navigating between the application's screens.
* Saving and retaining the settings preference.
* Retaining pantry data during normal application use.

### **Future Improvements**

Potential improvements include:

* Further enhancing the user interface and visual consistency.
* Expanding automated testing.
* Adding additional recipes and improving ingredient-management options.
* Implementing and testing actual expiry alerts if required.

## **Author**

**GitHub: Chief242**

**Repository: Smart Pantry Manager**