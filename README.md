# Tour-Guide-Kauai  
Your personal tour guide to Kauai.  

This is the first version of the **Adventure Guide App**. This concept has limited functionality at the moment.  

## Features (v1)  
Currently available functionality includes:  
- 📍 **Browse Destinations** – view a personalized list of attractions.  
- 🏝 **Category Navigation** – explore recommendations grouped by categories (Hikes, Food, Culture, Beaches, etc.).  
- 📝 **Recommendation Details** – tap into each recommendation for more information, including description and highlights.  
- 📱 **Jetpack Compose UI** – simple, modern interface using Material 3 guidelines.  
- 💾 **Local Data** – sample recommendations are stored and displayed from temporary data models (no backend yet).  

## Roadmap  
Planned future functionality includes:  
- 🌐 Integration with a backend for dynamic content.  
- 🔎 Search and filtering across recommendations.  
- ❤️ Save favorites for quick access.  
- 🗺 Interactive map view with pinned locations.  
- 🎟 Activity planner (day-by-day itinerary builder).  

## Tech Stack  
- **Kotlin** (100% Kotlin codebase)  
- **Jetpack Compose** for UI  
- **Navigation Component** for multi-screen flow  
- **ViewModel + State** for unidirectional data flow  
- **Gradle** build system  

## Project Structure  
- `ui/` – Screens, components, and themes.  
- `model/` – Data classes for recommendations and categories.  
- `navigation/` – App navigation graph and routes.  
- `MainActivity.kt` – App entry point.  

## Getting Started  
1. Clone the repo  
   ```bash
   git clone https://github.com/your-username/Tour-Guide-Kauai.git
   cd Tour-Guide-Kauai
2. Open in Android Studio (latest stable).
3. Build & run on an emulator or Android device (min SDK 26).
