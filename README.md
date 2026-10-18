# **Smart Pantry Manager**

### **Overview**

Smart Pantry Manager is an Android application developed using Java and SQLite. It helps users manage pantry ingredients and discover recipes they can prepare using the ingredients available to them.

The application uses a strict recipe-matching system that checks whether all required ingredients are available in sufficient quantities before suggesting a recipe.

### **Features**

### **1. Pantry Management**

* Add ingredients with their names, quantities, and measurement units.
* View stored ingredients in a list.
* Edit existing ingredients. 
* Delete ingredients with confirmation.
* Validate ingredient quantities before saving. 

### **2. Recipe Suggestions**

* Suggest recipes only when all required ingredients are available in sufficient quantities.
* Exclude recipes when ingredients are missing or quantities are insufficient.
* Support basic ingredient-name and measurement-unit normalization. 

### **3. Recipe Details**

* View the ingredients required for a selected recipe. 
* View the required quantities and measurement units.
* Read preparation instructions.

### **4. Local Database**

* Use SQLite to store pantry ingredients and recipe information.
* Retain pantry data between application sessions.
* Store recipes and their associated ingredient requirements locally.

### **5. Settings and Navigation**

* Navigate between the Home, My Pantry, Suggested Recipes, Recipe Details, and Settings screens.
* Save the expiring-soon alert preference.

### **Technologies Used**


| Technology | Purpose |
|---|---|
| Java | Application programming logic |
| Android Studio | Development environment |
| XML | User interface layouts |
| SQLite | Local database and data persistence |
| Android Activities and Intents | Navigation between screens |
| ListView and Custom Adapters | Displaying pantry ingredients |
| Git and GitHub | Version control and project hosting |

### **Application Screens**

The application contains the following screens:

1. **Home Screen**: Provides access to the application's main features.
2. **My Pantry**: Displays and manages stored ingredients.
3. **Add/Edit Ingredient**: Allows users to enter or update ingredient information. 
4. **Suggested Recipes**: Displays recipes that meet all ingredient requirements.
5. **Recipe Details**: Displays the selected recipe's ingredients and preparation instructions.
6. **Settings**: Provides access to the application's settings.

### **Database Design**

The application uses a local SQLite database with three main tables:

* **Pantry ingredients**: Stores ingredient names, quantities, and measurement units.
* **Recipes**: Stores recipe names and preparation instructions.
* **Recipe ingredients**: Stores the ingredients and quantities required by each recipe. 

The recipe-matching logic compares the ingredients and quantities stored in the pantry against the requirements of each recipe. A recipe is suggested only when all its requirements are satisfied.

# **Getting Started**

### **Prerequisites**

* Android Studio
* Android SDK and the required Gradle dependencies
* A compatible Android emulator or Android device

### **Running the Application**

1. Clone or download this repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and resolve the project dependencies. 
4. Start an Android emulator or connect a compatible Android device.
5. Select the app run configuration.
6. Click **Run** to build, install and launch the application.

### **Testing**

The following application behaviors have been tested:

* Adding, viewing, editing, and deleting pantry ingredients.
* Rejecting empty and zero ingredient quantities.
* Displaying recipes when all required ingredients are available.
* Hiding recipe suggestions when a required ingredient is missing.
* Displaying recipe ingredients and preparation instructions.
* Navigating between the application's screens using the home screen and toolbar menu.
* Saving and retaining the settings preference.
* Retaining pantry data during normal application use.

Additional regression testing and final verification will be performed before submission.

### **Screenshots**

Screenshots of the application's Home, My Pantry, Suggested Recipes, Recipe Details, and Settings screens will be added here after the final user-interface improvements.

### **Future Improvements**

Potential improvements include:

* Further enhancing the user interface and visual consistency.
* Expanding automated testing.
* Improving ingredient-management options.
* Implementing and verifying actual expiry notifications if required.

## **Author**

**GitHub: Chief242**

**Repository: Smart Pantry Manager**